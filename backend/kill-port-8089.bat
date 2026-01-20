@echo off
echo Recherche du processus sur le port 8089...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8089') do (
    echo Arret du processus %%a
    taskkill /F /PID %%a
)
echo Termine!
pause
