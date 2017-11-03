@echo off
SET JAVABIN="C:\Program Files\Java\jre-9.0.1\bin\java"
@echo.
@echo Hinweise: Fuer dieses Beispiel wird die JAVA Runtime Engine benoetigt.
@echo           Diese BATCH skript verwendet den Interpreter
@echo           %JAVABIN%
@echo.          Bitte passen Sie das Batch file an.
pause
@echo Starte KOS TCP Server Beispiel
@echo --------------------------------------------------
cd bin
%JAVABIN% TCPServer
pause