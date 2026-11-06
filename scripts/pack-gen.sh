#!/usr/bin/env bash
#
# pack-gen.sh - zips the UnMock4j generated sources and the participant's versions of them.
# Bash port of pack-gen.ps1 for Linux and macOS.
#
# Run it after check-gen.sh (and after fixing the copied files, if needed). It asks
# "check-gen.sh --dry-run" which files were copied, then writes unmock-<UnitTest>.zip in the
# unmock4j root with two folders:
#   original/  the files UnMock generated (<gen-dir>/<package path>/*.java)
#   modified/  the same files as they are now in the target project
# The zip is built with the JDK's "jar" tool, which every participant already has.
#
# Compatible with the bash 3.2 that ships with macOS.

set -eo pipefail

usage() {
    cat <<'EOF'
Usage: pack-gen.sh --repo <dir> [--unit-test <UNIT.txt>] [--gen-dir <dir>]
                   [--module <relative path>] [--java-home <dir>]

Example:
  ./scripts/pack-gen.sh --repo ~/repos/dolphinscheduler
EOF
    exit "${1:-0}"
}

die() { echo "error: $*" >&2; exit 1; }

REPO=""; check_args=()
while [ $# -gt 0 ]; do
    case "$1" in
        --repo) REPO="${2:?--repo needs a value}"; shift 2 ;;
        --unit-test|--gen-dir|--module|--java-home) check_args+=("$1" "${2:?$1 needs a value}"); shift 2 ;;
        -h|--help) usage 0 ;;
        *) echo "Unknown option: $1" >&2; usage 1 ;;
    esac
done
[ -n "$REPO" ] || { echo "--repo is required" >&2; usage 1; }
command -v jar >/dev/null 2>&1 || die "jar not found on PATH (it ships with the JDK)"

# --- 1. Copied files, as check-gen.sh resolves them ----------------------------------------
info="$(bash "$(dirname "$0")/check-gen.sh" --repo "$REPO" ${check_args[@]+"${check_args[@]}"} --dry-run)" ||
    { printf '%s\n' "$info"; die "check-gen.sh --dry-run failed."; }

TEST_CLASS="$(printf '%s\n' "$info" | sed -n 's/^Unit test : .*\.\([[:alnum:]_]*\)$/\1/p')"
[ -n "$TEST_CLASS" ] || { printf '%s\n' "$info"; die "check-gen.sh --dry-run did not report the test class."; }

ROOT="$(cd "$(dirname "$0")/.." && pwd -P)"
ZIP="$ROOT/unmock-$TEST_CLASS.zip"
stage="$(mktemp -d)"
trap 'rm -rf "$stage"' EXIT
mkdir -p "$stage/original" "$stage/modified"

# --- 2. Stage original/ and modified/ ------------------------------------------------------
count=0
while IFS= read -r line; do
    src="${line#copy }"; src="${src%% -> *}"
    dest="${line#* -> }"
    cp "$src" "$stage/original/"
    if [ -f "$dest" ]; then
        cp "$dest" "$stage/modified/"
        echo "added $(basename "$src")"
    else
        echo "warning: $dest not found (not copied by check-gen, or deleted); only the original is packed." >&2
    fi
    count=$((count + 1))
done < <(printf '%s\n' "$info" | grep '^copy .* -> ')
[ "$count" -gt 0 ] || die "check-gen.sh --dry-run listed no generated files."

# --- 3. Zip --------------------------------------------------------------------------------
rm -f "$ZIP"
jar -cMf "$ZIP" -C "$stage" original -C "$stage" modified
echo
echo "Zip       : $ZIP"
