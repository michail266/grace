@echo off
cd /d "%~dp0"

if "%1"=="" (
    set GRACE_FILE=minimal_test.grace
) else (
    set GRACE_FILE=%1
)

echo Compiling Grace file: %GRACE_FILE%

java -cp "target/classes;%USERPROFILE%\.m2\repository\com\github\vbmacher\java-cup-runtime\11b-20160615-1\java-cup-runtime-11b-20160615-1.jar;%USERPROFILE%\.m2\repository\org\ow2\asm\asm\9.7\asm-9.7.jar;%USERPROFILE%\.m2\repository\org\ow2\asm\asm-tree\9.7\asm-tree-9.7.jar;%USERPROFILE%\.m2\repository\org\ow2\asm\asm-util\9.7\asm-util-9.7.jar" gr.hua.dit.compiler.Main %GRACE_FILE%
