@echo off
REM build.bat — compiles and runs the Snake Game on Windows

set OUT_DIR=out
set SRC_DIR=src
set MAIN_CLASS=com.snakegame.Main

echo 🐍 Building Snake Game...
if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"

for /r "%SRC_DIR%" %%f in (*.java) do set SOURCES=!SOURCES! "%%f"
setlocal enabledelayedexpansion

javac -d "%OUT_DIR%" %SOURCES%

if %ERRORLEVEL% == 0 (
    echo Build successful!
    echo Launching game...
    java -cp "%OUT_DIR%" %MAIN_CLASS%
) else (
    echo Build failed. Make sure Java 17+ is installed.
)
