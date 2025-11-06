@echo off
echo ================================================================
echo Airline Management System - IDE Instructions
echo ================================================================
echo.
echo Maven is not installed on your system.
echo.
echo *** Please use an IDE to run this project ***
echo.
echo OPTION 1 - IntelliJ IDEA (Recommended):
echo   1. Open IntelliJ IDEA
echo   2. File -^> Open
echo   3. Select: C:\Users\ragha\Desktop\flight_management
echo   4. Wait for Maven dependencies to download (bottom right)
echo   5. Navigate to: src/main/java/com/airline/AirlineManagementApp.java
echo   6. Right-click -^> Run 'AirlineManagementApp.main()'
echo.
echo OPTION 2 - VS Code:
echo   1. Open VS Code
echo   2. File -^> Open Folder
echo   3. Select: C:\Users\ragha\Desktop\flight_management
echo   4. Install "Extension Pack for Java" if prompted
echo   5. Open: src/main/java/com/airline/AirlineManagementApp.java
echo   6. Click "Run" button above the main method
echo.
echo OPTION 3 - Eclipse:
echo   1. File -^> Import -^> Maven -^> Existing Maven Projects
echo   2. Browse to: C:\Users\ragha\Desktop\flight_management
echo   3. Right-click AirlineManagementApp.java
echo   4. Run As -^> Java Application
echo.
echo ================================================================
echo Before Running - IMPORTANT:
echo ================================================================
echo 1. Ensure MySQL is running
echo 2. Create database: mysql -u root -p ^< database\schema.sql
echo 3. Load data:       mysql -u root -p ^< database\seed.sql
echo 4. Update password in: src\main\java\com\airline\config\DatabaseConfig.java
echo.
echo Default Login:
echo   Username: admin
echo   Password: admin123
echo ================================================================
echo.
pause
