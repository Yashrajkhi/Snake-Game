#!/bin/bash
# build.sh — compiles and runs the Snake Game

OUT_DIR="out"
SRC_DIR="src"
MAIN_CLASS="com.snakegame.Main"

echo "🐍 Building Snake Game..."
mkdir -p "$OUT_DIR"

javac -d "$OUT_DIR" $(find "$SRC_DIR" -name "*.java")

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "🚀 Launching game..."
    java -cp "$OUT_DIR" "$MAIN_CLASS"
else
    echo "❌ Build failed. Make sure Java 17+ is installed."
fi
