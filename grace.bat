@echo off
REM Grace Compiler Launcher for Windows
REM Usage: grace.bat [options] <grace_file>

set GRACE_HOME=%~dp0
set CLASSPATH=%GRACE_HOME%target\classes;C:\Users\stefanos\.m2\repository\com\github\vbmacher\java-cup-runtime\11b-20160615-2\java-cup-runtime-11b-20160615-2.jar

if not exist "%GRACE_HOME%target\classes" (
    echo Building Grace compiler...
    cd /d "%GRACE_HOME%"
    call mvn clean compile -q
    if errorlevel 1 (
        echo Build failed!
        exit /b 1
    )
)

java -cp "%CLASSPATH%" gr.hua.dit.compiler.Main %*
