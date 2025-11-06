@echo off
echo ===============================================
echo MySQL Password Tester
echo ===============================================
echo.
echo This will help you find your MySQL root password
echo.
set /p PASS="Enter your MySQL root password: "

echo.
echo Testing password...
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p%PASS% -e "SELECT 'SUCCESS! Password is correct!' AS Result;"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ===============================================
    echo ✓ SUCCESS! Your password works!
    echo ===============================================
    echo.
    echo Now update DatabaseConfig.java:
    echo   USERNAME = "root"
    echo   PASSWORD = "%PASS%"
    echo.
) else (
    echo.
    echo ❌ Password incorrect. Try again or use reset-mysql-password.bat
)

pause
