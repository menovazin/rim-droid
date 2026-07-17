#!/usr/bin/env bash
# run.sh — Build and launch the Android app from the presentation module.
# Usage: ./run.sh [--release] [--help]

set -euo pipefail

BUILD_TYPE="debug"

# --- Parse args ---
while [[ $# -gt 0 ]]; do
  case "$1" in
    -h|--help)
      cat <<EOF
Usage: $(basename "$0") [--release] [--help]

Build and install the Android APK for the presentation module,
then launch the main Activity on the connected device/emulator.

Options:
  --release     Build and install the release APK (default: debug)
  -h, --help    Show this help message and exit

Environment variables:
  APPLICATION_ID   Override the application ID (default: com.rim.droid)
EOF
      exit 0
      ;;
    --release)
      BUILD_TYPE="release"
      shift
      ;;
    *)
      echo "Error: unknown option: $1" >&2
      echo "Run '$(basename "$0") --help' for usage." >&2
      exit 1
      ;;
  esac
done

# --- Resolve project root ---
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

# --- Check gradlew ---
if [[ ! -x "$PROJECT_ROOT/gradlew" ]]; then
  echo "Error: gradlew not found or not executable in $PROJECT_ROOT" >&2
  exit 1
fi

# --- Check adb ---
if ! command -v adb &>/dev/null; then
  echo "Error: adb not found on PATH." >&2
  echo "Install Android SDK platform-tools and add them to PATH." >&2
  echo "See: https://developer.android.com/tools/releases/platform-tools" >&2
  exit 1
fi

# --- Determine application ID ---
APP_ID="${APPLICATION_ID:-}"
if [[ -z "$APP_ID" ]]; then
  BUILD_GRADLE="$SCRIPT_DIR/build.gradle.kts"
  if [[ -f "$BUILD_GRADLE" ]]; then
    # Try to extract applicationId from build.gradle.kts
    APP_ID=$(grep -E 'applicationId\s*=' "$BUILD_GRADLE" | head -1 | awk -F'"' '{print $2}' | tr -d '[:space:]' || true)
  fi
fi
APP_ID="${APP_ID:-com.rim.droid}"

if [[ "$BUILD_TYPE" == "release" ]]; then
  GRADLE_TASK=":presentation:installRelease"
else
  GRADLE_TASK=":presentation:installDebug"
fi

echo "Building and installing presentation module ($BUILD_TYPE)..."
echo "Application ID: $APP_ID"

# --- Build and install ---
cd "$PROJECT_ROOT"
if ! ./gradlew "$GRADLE_TASK"; then
  echo "Error: Gradle build/install failed." >&2
  exit 1
fi

# --- Check device/emulator ---
if ! adb devices | grep -q 'device$'; then
  echo "Error: No Android device or emulator detected." >&2
  echo "Start an emulator or connect a device and ensure USB debugging is enabled." >&2
  exit 1
fi

# --- Launch app ---
echo "Launching app on device..."
adb shell monkey -p "$APP_ID" -c android.intent.category.LAUNCHER 1
echo "App launched successfully."
