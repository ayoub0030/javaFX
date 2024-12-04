@echo off
set JAVAFX_PATH="C:\Program Files\Java\javafx-sdk-23.0.1\lib"

:: Create directories if they don't exist
mkdir ".\bin" 2>nul

:: Compile
javac --module-path %JAVAFX_PATH% --add-modules javafx.controls,javafx.fxml -d bin src/main/java/module-info.java src/main/java/com/example/*.java

:: Copy resources
mkdir ".\bin\resources" 2>nul
copy "src\main\resources\*.*" "bin\resources\" >nul

:: Run
java --module-path %JAVAFX_PATH% --add-modules javafx.controls,javafx.fxml -cp "bin;src/main/resources" com.example.LoginApp

pause
