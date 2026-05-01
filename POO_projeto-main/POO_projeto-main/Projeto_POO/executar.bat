@echo off
echo Compilando o projeto...
if not exist "bin" mkdir bin
javac -cp "lib/mysql-connector-j-8.3.0.jar" -d bin src/main/java/rh/modelo/*.java src/main/java/rh/dao/*.java src/main/java/rh/Main.java
echo.
echo Iniciando Sistema...
java -cp "bin;lib/mysql-connector-j-8.3.0.jar" rh.Main
pause
