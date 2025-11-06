@echo off
echo ===============================================
echo MySQL Password Reset Tool
echo ===============================================
echo.
echo This will reset your MySQL root password to: newpassword123
echo.
pause

echo Step 1: Stopping MySQL service...
net stop MySQL80

echo.
echo Step 2: Creating password reset file...
echo ALTER USER 'root'@'localhost' IDENTIFIED BY 'newpassword123'; > C:\mysql-init.txt

echo.
echo Step 3: Starting MySQL with password reset...
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqld.exe" --init-file=C:\mysql-init.txt --console

echo.
echo Step 4: Cleaning up...
del C:\mysql-init.txt

echo.
echo ===============================================
echo Password reset complete!
echo New password: newpassword123
echo ===============================================
echo.
echo Now update DatabaseConfig.java:
echo   PASSWORD = "newpassword123"
echo.
pause
