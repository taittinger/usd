#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

# Additional Maven options (such as -s settings.xml) can be passed to this script.
mvn -B -ntp -C "$@" verify org.apache.maven.plugins:maven-dependency-plugin:3.8.1:copy-dependencies \
    -DincludeScope=runtime -DoutputDirectory="$PWD/target/smoke/lib"
mkdir -p target/smoke/classes
javac --release 17 --module-path target/smoke/lib --add-modules javafx.controls,javafx.fxml \
    -cp target/classes -d target/smoke/classes tests/ThreeRoundSmoke.java
classpath="target/classes:target/smoke/classes"
case "$(uname -s)" in
    MINGW*|MSYS*|CYGWIN*) classpath="target/classes;target/smoke/classes" ;;
esac
java_args=(-Djavafx.cachedir="$PWD/target/smoke/javafx-cache"
    --module-path target/smoke/lib --add-modules javafx.controls,javafx.fxml
    -cp "$classpath" ThreeRoundSmoke)
if [[ "$(uname -s)" == Linux && -z "${DISPLAY:-}" ]]; then
    if ! command -v xvfb-run >/dev/null; then
        echo 'DISPLAY がない場合は xvfb-run をインストールしてください。' >&2
        exit 1
    fi
    xvfb-run -a java "${java_args[@]}"
else
    java "${java_args[@]}"
fi
