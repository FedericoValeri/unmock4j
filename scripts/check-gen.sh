#!/usr/bin/env bash
#
# check-gen.sh - copies the UnMock4j generated sources into the cloned target project
# and checks that they compile. Bash port of check-gen.ps1 for Linux and macOS.
#
# Reads the unit test package and class name from UNIT.txt, finds the module of the cloned
# repository that contains the original test, copies <gen-dir>/<package path>/*.java into that
# module's src/test/java/<package path>/ and runs "mvn clean test-compile -pl <module> -am".
# Copied paths are recorded in <repo>/.unmock-copied.txt so that --clean can remove them.
# The build runs on the lowest installed JDK that satisfies the Java version declared in the
# repository's root pom.xml. Installed JDKs are discovered by maven-toolchains-plugin 3.2.0
# plus ~/.m2/toolchains.xml; --java-home overrides the choice.
#
# Compatible with the bash 3.2 that ships with macOS (no mapfile, no associative arrays).

set -eo pipefail

usage() {
    cat <<'EOF'
Usage: check-gen.sh --repo <dir> [--unit-test <UNIT.txt>] [--gen-dir <dir>]
                    [--module <relative path>] [--java-home <dir>] [--dry-run] [--clean]

Examples:
  ./scripts/check-gen.sh --repo ~/repos/shenyu
  ./scripts/check-gen.sh --repo ~/repos/shenyu --gen-dir r3 \
      --unit-test src/main/resources/prompts/readyToUse/human/shenyu-LdapConfigurationTest/UNIT.txt --dry-run
  ./scripts/check-gen.sh --repo ~/repos/shenyu --clean
EOF
    exit "${1:-0}"
}

die() { echo "error: $*" >&2; exit 1; }

REPO=""; UNIT_TEST=""; GEN_DIR=""; MODULE=""; JAVA_HOME_ARG=""; DRY_RUN=0; CLEAN=0
while [ $# -gt 0 ]; do
    case "$1" in
        --repo)      REPO="${2:?--repo needs a value}"; shift 2 ;;
        --unit-test) UNIT_TEST="${2:?--unit-test needs a value}"; shift 2 ;;
        --gen-dir)   GEN_DIR="${2:?--gen-dir needs a value}"; shift 2 ;;
        --module)    MODULE="${2:?--module needs a value}"; shift 2 ;;
        --java-home) JAVA_HOME_ARG="${2:?--java-home needs a value}"; shift 2 ;;
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

# --- 5. JDK required by the target project -------------------------------------------------
java_major() {   # 1.8.0_492 -> 8, 17.0.19 -> 17
    local v="$1"
    case "$v" in 1.*) v="${v#1.}" ;; esac
    echo "${v%%[!0-9]*}"
}

if [ -n "$JAVA_HOME_ARG" ]; then
    [ -d "$JAVA_HOME_ARG" ] || die "JDK not found: $JAVA_HOME_ARG"
    JAVA_HOME_ARG="$(cd "$JAVA_HOME_ARG" && pwd -P)"
    echo "Java      : $JAVA_HOME_ARG (--java-home)"
else
    # Highest version wins: sentinel compiles module-info with release 17 and the rest with 8
    tags='java\.version|jdk\.version|maven\.compiler\.(release|source|target)|release|source|target'
    required="$( { grep -oE "<($tags)>[[:space:]]*[0-9.]+[[:space:]]*</" "$REPO/pom.xml" 2>/dev/null || true; } |
        sed -E 's#.*>[[:space:]]*([0-9.]+)[[:space:]]*</#\1#' |
        while IFS= read -r v; do java_major "$v"; done | sort -n | tail -1)"

    if [ -z "$required" ]; then
        echo "Java      : not declared in pom.xml, using JAVA_HOME=${JAVA_HOME:-}"
    else
        command -v mvn >/dev/null 2>&1 || die "mvn not found on PATH"
        # Maven finds the JDKs installed on this machine (JAVA_HOME, JAVAxx_HOME, ~/.jdks, SDKMAN,
        # /usr/lib/jvm, Program Files, ...); the user's ~/.m2/toolchains.xml adds declared ones
        discovered="$(mktemp)"
        # Run away from the repo pom, which Maven would otherwise load
        (cd "${TMPDIR:-/tmp}" && mvn -B -q org.apache.maven.plugins:maven-toolchains-plugin:3.2.0:generate-jdk-toolchains-xml \
            "-Dtoolchain.file=$discovered") >/dev/null 2>&1 ||
            echo "warning: JDK discovery failed (mvn exit $?)." >&2

        # "<major>\t<jdkHome>" per toolchain; version ranges and ${...} give no major and are skipped
        jdks="$( { cat "$discovered"; cat "$HOME/.m2/toolchains.xml" 2>/dev/null || true; } | awk '
            /<toolchain>/   { v = ""; h = "" }
            /<version>/     { v = $0; sub(/.*<version>[[:space:]]*/, "", v); sub(/[[:space:]]*<\/version>.*/, "", v) }
            /<jdkHome>/     { h = $0; sub(/.*<jdkHome>[[:space:]]*/, "", h); sub(/[[:space:]]*<\/jdkHome>.*/, "", h) }
            /<\/toolchain>/ { if (v != "" && h != "") print v "\t" h }' |
            while IFS="$(printf '\t')" read -r v h; do
                m="$(java_major "$v")"
                if [ -n "$m" ] && [ -d "$h" ]; then printf '%s\t%s\n' "$m" "$h"; fi
            done)"
        rm -f "$discovered"

        # Lowest installed JDK that is >= the required one: old Lombok breaks on newer JDKs
        pick="$(printf '%s\n' "$jdks" | awk -F'\t' -v req="$required" 'NF == 2 && $1 >= req' |
            sort -n | head -1 | cut -f2-)"
        if [ -z "$pick" ]; then
            found="$(printf '%s\n' "$jdks" | cut -f1 | awk 'NF' | sort -nu | paste -sd, -)"
            die "pom.xml requires Java $required but no installed JDK >= $required was found (found: ${found:-none}). Install JDK $required or pass --java-home <dir>."
        fi
        JAVA_HOME_ARG="$pick"
        echo "Java      : $required required by pom.xml -> $JAVA_HOME_ARG"
    fi
fi

# --- 6. Build ------------------------------------------------------------------------------
mvn_args=(-B clean test-compile)
if [ -n "$MODULE_REL" ]; then
    # Modules that compile C/C++ (org.codehaus.mojo:native-maven-plugin, e.g. arthas-vmtool) need g++;
    # take their released jar from the Maven repository instead of building them. Not GraalVM's
    # native-maven-plugin (nacos console): Maven rejects '!x' when x is outside the -am set
    pl="$MODULE_REL"
    while IFS= read -r pom; do
        dir="${pom%/pom.xml}"; rel="${dir#"$REPO"/}"
        if [ "$dir" != "$REPO" ] && [ "$rel" != "$MODULE_REL" ]; then pl+=",!$rel"; fi
    done < <(find "$REPO" "${prune[@]}" -type f -name pom.xml -exec grep -lzE 'org\.codehaus\.mojo</groupId>[[:space:]]*<artifactId>native-maven-plugin' {} +)
    mvn_args+=(-pl "$pl" -am)
fi
mvn_args+=(
    -DskipTests -Dcheckstyle.skip=true -Drat.skip=true
    -Dspotless.check.skip=true -Dspotless.apply.skip=true
    -Dmaven.javadoc.skip=true -Djacoco.skip=true
)
echo
if [ -n "$JAVA_HOME_ARG" ]; then printf 'JAVA_HOME=%q ' "$JAVA_HOME_ARG"; fi
printf 'cd %q; mvn' "$REPO"; printf ' %q' "${mvn_args[@]}"; echo
if [ "$DRY_RUN" -eq 1 ]; then exit 0; fi

command -v mvn >/dev/null 2>&1 || die "mvn not found on PATH"
log="$(mktemp)"
trap 'rm -f "$log"' EXIT

set +e
# mvn runs on JAVA_HOME, and javac comes from that JVM
(cd "$REPO" && if [ -n "$JAVA_HOME_ARG" ]; then export JAVA_HOME="$JAVA_HOME_ARG"; fi
 mvn "${mvn_args[@]}") 2>&1 | tee "$log"
exit_code=${PIPESTATUS[0]}
set -e

# --- 7. Report -----------------------------------------------------------------------------
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