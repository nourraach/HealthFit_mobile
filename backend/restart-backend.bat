@echo off
echo ========================================
echo Redemarrage complet du backend
echo ========================================
echo.

echo 1. Arret de tous les serveurs Java...
taskkill /F /IM java.exe 2>nul
taskkill /F /IM javaw.exe 2>nul
timeout /t 2 /nobreak >nul

echo 2. Nettoyage du projet...
call mvnw.cmd clean

echo 3. Compilation...
call mvnw.cmd compile

echo 4. Demarrage du serveur...
start "Backend Server" cmd /k "mvnw.cmd spring-boot:run"

echo.
echo ========================================
echo Serveur en cours de demarrage...
echo Attendez 30 secondes puis testez
echo ========================================
pause
