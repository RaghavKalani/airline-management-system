@echo off
echo ===============================================
echo Airline Management System - Build and Run
echo ===============================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Maven is not installed or not in PATH!
    echo.
    echo Please install Maven or use your IDE to build and run the project.
    echo.
    echo Using IDE:
    echo   - IntelliJ IDEA: Right-click AirlineManagementApp.java ^> Run
    echo   - Eclipse: Right-click AirlineManagementApp.java ^> Run As ^> Java Application
    echo   - VS Code: Open AirlineManagementApp.java ^> Press F5
    echo.
    pause
    exit /b 1
)

echo [1/3] Cleaning previous builds...
call mvn clean
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Build failed during clean phase
    pause
    exit /b 1
)

echo.
echo [2/3] Compiling and packaging...
call mvn package
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Build failed during package phase
    pause
    exit /b 1
)

echo.
echo [3/3] Running application...
echo.
java -jar target\airline-management-system.jar

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Application failed to start
    echo Please check:
    echo   1. MySQL is running
    echo   2. Database is set up (run schema.sql and seed.sql)
    echo   3. Credentials in DatabaseConfig.java are correct
    pause
    exit /b 1
)
