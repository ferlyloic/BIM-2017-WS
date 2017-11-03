@echo off
SET JAVABIN="C:\Program Files (x86)\Java\jre1.8.0_144\bin\java"
@echo.
@echo Hinweise: Fuer dieses Beispiel wird die JAVA Runtime Engine benoetigt.
@echo           Diese BATCH skript verwendet den Interpreter
@echo           %JAVABIN%
@echo.          Bitte passen Sie das Batch file an.
pause
@echo Starte KOS UDP Client Beispiel
@echo --------------------------------------------------
cd bin
%JAVABIN% UDPClient
pause