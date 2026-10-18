@echo off
chcp 65001 >nul
if not exist "%JAVA_HOME%\bin\javac.exe" set "JAVA_HOME=C:\Program Files\JetBrains\PyCharm Community Edition 2024.2.3\jbr"
cmd /c ""%~dp0gradlew.bat" classes --quiet <nul"
if errorlevel 1 exit /b 1
"%JAVA_HOME%\bin\java.exe" -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -cp "%~dp0build\classes\java\main" ru.university.task7.Main
