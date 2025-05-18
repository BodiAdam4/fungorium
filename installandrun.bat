@echo off
rem 1. Fordítás
echo Fordítás...

javac -d out controller\*.java graphics\*.java listeners\*.java model\*.java userinterface\*.java

if %errorlevel% neq 0 (
    echo Hiba történt a fordítás során!
    pause
    exit /b %errorlevel%
)

rem 2. Futtatás
echo Futtatás...
java -cp out graphics.GraphicMain

pause
