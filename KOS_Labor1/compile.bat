@echo off
@SET JAVACC="C:\Program Files\Java\jdk-9\bin\javac"
@echo.
@echo Hinweise: Um die Beispiele zu kompilieren wird ein JAVA compiler benoetigt!
@echo           Diese BATCH skript verwendet den compiler:
@echo           %JAVACC%
@echo.          Bitte passen Sie das Batch file an.
pause
@echo Compiling Examples
%JAVACC% -d .\bin .\src\TCPClient.java
%JAVACC% -d .\bin .\src\TCPServer.java
%JAVACC% -d .\bin .\src\UDPServer.java
%JAVACC% -d .\bin .\src\UDPClient.java
pause