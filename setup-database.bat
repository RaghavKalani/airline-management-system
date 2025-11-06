@echo off
echo ===============================================
echo Setting up MySQL Database
echo ===============================================
echo.
echo This script will create and populate the database.
echo Make sure MySQL is running before proceeding!
echo.
pause

set /p MYSQL_USER="Enter MySQL username (default: root): "
if "%MYSQL_USER%"=="" set MYSQL_USER=root

set /p MYSQL_PASSWORD="Enter MySQL password: "

echo.
echo Creating database schema...
mysql -u %MYSQL_USER% -p%MYSQL_PASSWORD% < database\schema.sql
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Failed to create schema
    pause
    exit /b 1
)

echo.
echo Loading seed data...
mysql -u %MYSQL_USER% -p%MYSQL_PASSWORD% airline_db < database\seed.sql
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Failed to load seed data
    pause
    exit /b 1
)

echo.
echo ===============================================
echo Database setup completed successfully!
echo ===============================================
echo.
echo Default login credentials:
echo   Admin:    admin / admin123
echo   Customer: john_doe / user123
echo.
pause
