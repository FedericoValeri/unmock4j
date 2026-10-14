#!/usr/bin/env bash
#
# check-gen.sh - copies the UnMock4j generated sources into the cloned target project
# and checks that they compile. Bash port of check-gen.ps1 for Linux and macOS.
#
# Reads the unit test package and class name from UNIT.txt, finds the module of the cloned
# repository that contains the original test, copies <gen-dir>/<package path>/*.java into that
# module's src/test/java/<package path>/ and runs "mvn clean test-compile -pl <module> -am".
# Copied paths are recorded in <repo>/.unmock-copied.txt so that --clean can remove them.
#
# Compatible with the bash 3.2 that ships with macOS (no mapfile, no associative arrays).

set -eo pipefail

usage() {
    cat <<'EOF'
Usage: check-gen.sh --repo <dir> [--unit-test <UNIT.txt>] [--gen-dir <dir>]
                    [--module <relative path>] [--dry-run] [--clean]

Examples:
  ./scripts/check-gen.sh --repo ~/repos/shenyu
  ./scripts/check-gen.sh --repo ~/repos/shenyu --gen-dir r3 \
      --unit-test src/main/resources/prompts/readyToUse/human/shenyu-LdapConfigurationTest/UNIT.txt --dry-run
  ./scripts/check-gen.sh --repo ~/repos/shenyu --clean
EOF
    exit "${1:-0}"
}

die() { echo "error: $*" >&2; exit 1; }

REPO=""; UNIT_TEST=""; GEN_DIR=""; MODULE=""; DRY_RUN=0; CLEAN=0
while [ $# -gt 0 ]; do
    case "$1" in
        --repo)      REPO="${2:?--repo needs a value}"; shift 2 ;;
        --unit-test) UNIT_TEST="${2:?--unit-test needs a value}"; shift 2 ;;
        --gen-dir)   GEN_DIR="${2:?--gen-dir needs a value}"; shift 2 ;;
        --module)    MODULE="${2:?--module needs a value}"; shift 2 ;;
        --dry-run)   DRY_RUN=1; shift ;;
        --clean)     CLEAN=1; shift ;;
        -h|--help)   usage 0 ;;
        *)           echo "Unknown option: $1" >&2; usage 1 ;;
    esac
done
[ -n "$REPO" ] || { echo "--repo is required" >&2; usage 1; }

ROOT="$(cd "$(dirname "$0")/.." && pwd -P)"
[ -n "$UNIT_TEST" ] || UNIT_TEST="$ROOT/src/main/resources/prompts/human/UNIT.txt"
[ -n "$GEN_DIR" ] || GEN_DIR="$ROOT/gen"
case "$GEN_DIR" in /*) ;; *) GEN_DIR="$PWD/$GEN_DIR" ;; esac

[ -d "$REPO" ] || die "Repository not found: $REPO"
REPO="$(cd "$REPO" && pwd -P)"
MANIFEST="$REPO/.unmock-copied.txt"

# --- Clean mode: remove files copied by a previous run -------------------------------------
if [ "$CLEAN" -eq 1 ]; then
    if [ ! -f "$MANIFEST" ]; then echo "Nothing to clean: $MANIFEST not found."; exit 0; fi
    while IFS= read -r f || [ -n "$f" ]; do
        if [ -n "$f" ] && [ -e "$f" ]; then rm -f -- "$f"; echo "removed $f"; fi
    done < "$MANIFEST"
    rm -f -- "$MANIFEST"
    exit 0
fi

# --- 1. Package and test class (equivalent of the FullClassNamesExtractor regex) -----------
[ -f "$UNIT_TEST" ] || die "Unit test file not found: $UNIT_TEST"
source_text="$(cat "$UNIT_TEST")"
pkg_re='package[[:space:]]+([[:alnum:]_.]+)[[:space:]]*;'
cls_re='(^|[^[:alnum:]_])(interface|class)[[:space:]]+([[:alnum:]_]+)'
[[ $source_text =~ $pkg_re ]] || die "Cannot find package/class declaration in $UNIT_TEST"
PACKAGE="${BASH_REMATCH[1]}"
rest="${source_text#*"${BASH_REMATCH[0]}"}"
[[ $rest =~ $cls_re ]] || die "Cannot find package/class declaration in $UNIT_TEST"
TEST_CLASS="${BASH_REMATCH[3]}"
PACKAGE_PATH="${PACKAGE//./\/}"
echo "Unit test : $PACKAGE.$TEST_CLASS"

# --- 2. Generated sources for that package -------------------------------------------------
GEN_PACKAGE_DIR="$GEN_DIR/$PACKAGE_PATH"
sources=()
if [ -d "$GEN_PACKAGE_DIR" ]; then
    while IFS= read -r f; do sources+=("$f"); done < <(
        find "$GEN_PACKAGE_DIR" -maxdepth 1 -type f -name '*.java' | sort)
fi
[ ${#sources[@]} -gt 0 ] || die "No generated .java files in $GEN_PACKAGE_DIR"

# --- 3. Module that contains the original test ---------------------------------------------
# Skip build output, node_modules and .git while searching
prune=( -type d '(' -name target -o -name node_modules -o -name .git ')' -prune -o )
module_of() { sed 's#/src/test/java/.*##'; }

if [ -n "$MODULE" ]; then
    [ -d "$REPO/$MODULE" ] || die "Module not found: $REPO/$MODULE"
    MODULE_DIR="$(cd "$REPO/$MODULE" && pwd -P)"
else
    candidates=()
    while IFS= read -r d; do candidates+=("$d"); done < <(
        find "$REPO" "${prune[@]}" -type f -name "$TEST_CLASS.java" \
             -path "*/src/test/java/$PACKAGE_PATH/$TEST_CLASS.java" -print |
        module_of | awk '!seen[$0]++')

    if [ ${#candidates[@]} -eq 0 ]; then
        # Fallback: any module whose test sources already contain the package (longest path first)
        while IFS= read -r d; do candidates+=("$d"); done < <(
            find "$REPO" "${prune[@]}" -type d -path "*/src/test/java/$PACKAGE_PATH" -print |
            module_of | awk '{ print length($0) "\t" $0 }' | sort -rn | cut -f2- | awk '!seen[$0]++')
    fi

    if [ ${#candidates[@]} -eq 0 ]; then
        die "No module in $REPO contains src/test/java/$PACKAGE_PATH. Use --module <relative path>."
    fi
    if [ ${#candidates[@]} -gt 1 ]; then
        echo "Several modules match:"
        for c in "${candidates[@]}"; do r="${c#"$REPO"}"; echo "  ${r#/}"; done
        die "Ambiguous module. Rerun with --module <relative path>."
    fi
    MODULE_DIR="${candidates[0]}"
fi

MODULE_REL="${MODULE_DIR#"$REPO"}"
MODULE_REL="${MODULE_REL#/}"
TARGET_DIR="$MODULE_DIR/src/test/java/$PACKAGE_PATH"
echo "Module    : ${MODULE_REL:-(root)}"
echo "Target    : $TARGET_DIR"

# --- 4. Copy -------------------------------------------------------------------------------
copied=()
for s in "${sources[@]}"; do
    dest="$TARGET_DIR/$(basename "$s")"
    echo "copy $s -> $dest"
    if [ "$DRY_RUN" -eq 0 ]; then
        mkdir -p "$TARGET_DIR"
        cp -f "$s" "$dest"
    fi
    copied+=("$dest")
done
if [ "$DRY_RUN" -eq 0 ]; then
    tmp_manifest="$(mktemp)"
    { if [ -f "$MANIFEST" ]; then cat "$MANIFEST"; fi; printf '%s\n' "${copied[@]}"; } |
        awk 'NF && !seen[$0]++' > "$tmp_manifest"
    cat "$tmp_manifest" > "$MANIFEST"
    rm -f "$tmp_manifest"
fi

# --- 5. Build ------------------------------------------------------------------------------
mvn_args=(-B clean test-compile)
if [ -n "$MODULE_REL" ]; then mvn_args+=(-pl "$MODULE_REL" -am); fi
mvn_args+=(
    -DskipTests -Dcheckstyle.skip=true -Drat.skip=true
    -Dspotless.check.skip=true -Dspotless.apply.skip=true
    -Dmaven.javadoc.skip=true -Djacoco.skip=true
)
echo
printf 'cd %q; mvn' "$REPO"; printf ' %q' "${mvn_args[@]}"; echo
if [ "$DRY_RUN" -eq 1 ]; then exit 0; fi

command -v mvn >/dev/null 2>&1 || die "mvn not found on PATH"
log="$(mktemp)"
trap 'rm -f "$log"' EXIT

set +e
(cd "$REPO" && mvn "${mvn_args[@]}") 2>&1 | tee "$log"
exit_code=${PIPESTATUS[0]}
set -e

# --- 6. Report -----------------------------------------------------------------------------
echo
echo "mvn exit code: $exit_code"
name_args=()
for s in "${sources[@]}"; do name_args+=(-e "$(basename "$s")"); done
errors="$(grep -F '[ERROR]' "$log" | grep -F "${name_args[@]}" || true)"
if [ -n "$errors" ]; then
    echo "Compile errors in generated files:"
    printf '%s\n' "$errors" | sed 's/^/  /'
elif [ "$exit_code" -eq 0 ]; then
    echo "Generated files compile."
fi
exit "$exit_code"