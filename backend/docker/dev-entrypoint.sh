#!/bin/sh
set -e

cd /workspace

echo "[dev] Resolving Maven dependencies..."
mvn -q -B dependency:go-offline -DskipTests 2>/dev/null \
  || mvn -q -B dependency:resolve -DskipTests

echo "[dev] Initial compile..."
mvn -q -B compile -DskipTests

compile_on_change() {
  while inotifywait -r -e modify,create,delete,move \
    /workspace/src/main/java \
    /workspace/src/main/resources \
    /workspace/pom.xml 2>/dev/null; do
    echo "[dev] Source change detected, recompiling..."
    if mvn -q -B compile -DskipTests; then
      echo "[dev] Compile OK (spring-boot-devtools will restart the app)"
    else
      echo "[dev] Compile failed — fix errors and save again"
    fi
  done
}

compile_on_change &
WATCHER_PID=$!
trap 'kill "$WATCHER_PID" 2>/dev/null || true' EXIT INT TERM

echo "[dev] Starting Spring Boot (profile: docker)..."
exec mvn -B spring-boot:run \
  -Dspring-boot.run.profiles=docker \
  -Dspring-boot.run.jvmArguments="${JAVA_OPTS:-}"
