# 🚀 VS Code - Quick Start Guide

## ✅ Step 1: Install MySQL (If Not Already Installed)

1. Download MySQL Community Server: https://dev.mysql.com/downloads/mysql/
2. During installation, remember the **root password** you set
3. Add MySQL to PATH or use full path: `C:\Program Files\MySQL\MySQL Server 8.x\bin\mysql.exe`

---

## ✅ Step 2: Setup Database

Open PowerShell as Administrator and run:

```powershell
# Option A: If MySQL is in PATH
mysql -u root -p

# Option B: If MySQL is NOT in PATH (adjust version number)
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
```

Inside MySQL console, run:
```sql
source C:/Users/ragha/Desktop/flight_management/database/schema.sql
source C:/Users/ragha/Desktop/flight_management/database/seed.sql
exit
```

---

## ✅ Step 3: Update Database Password

1. Open: `src/main/java/com/airline/config/DatabaseConfig.java`
2. Find line 9: `private static final String PASSWORD = "root";`
3. Change `"root"` to your actual MySQL root password
4. Save the file

---

## ✅ Step 4: Run the Application in VS Code

### Method 1: Using Run Button (Easiest)
1. Open file: `src/main/java/com/airline/AirlineManagementApp.java`
2. Look for the **`main`** method
3. Click the **"Run"** or **"Debug"** button that appears above the main method
4. The application GUI will launch!

### Method 2: Using Run Configuration
1. Press `F5` or click **Run and Debug** icon (left sidebar)
2. Select **"Run Airline Management System"** from dropdown
3. Click the green play button

### Method 3: Using Command Palette
1. Press `Ctrl+Shift+P`
2. Type: "Java: Run Java Program"
3. Select `AirlineManagementApp`

---

## 🔐 Default Login Credentials

After the GUI launches:

### Admin Account:
- Username: `admin`
- Password: `admin123`

### Customer Account:
- Username: `john_doe`
- Password: `user123`

---

## 🐛 Troubleshooting

### "Cannot resolve dependencies" or "Build failed"
VS Code needs to download Maven dependencies first. Look at the bottom right corner for progress:
- You should see: "Building... (Maven)"
- Wait for it to complete (may take 2-5 minutes first time)
- If stuck, press `Ctrl+Shift+P` → "Java: Clean Java Language Server Workspace"

### "Connection refused" or "Access denied"
- Make sure MySQL is running
- Check your password in `DatabaseConfig.java`
- Verify database was created: `mysql -u root -p -e "SHOW DATABASES;"`

### "Java version mismatch"
- Your system has Java 24, project uses Java 17
- This is fine! Java 24 is backward compatible
- If issues occur, download Java 17 from: https://adoptium.net/

---

## 📁 Project Structure

```
flight_management/
├── src/main/java/com/airline/
│   ├── AirlineManagementApp.java    ← START HERE (main method)
│   ├── config/DatabaseConfig.java    ← Update password here
│   ├── model/                        ← Data models
│   ├── dao/                          ← Database operations
│   ├── service/                      ← Business logic
│   └── ui/                           ← GUI screens
├── database/
│   ├── schema.sql                    ← Run this first
│   └── seed.sql                      ← Run this second
└── pom.xml                           ← Maven dependencies
```

---

## 🎯 Next Steps

1. ✅ Install MySQL (if needed)
2. ✅ Setup database (schema.sql + seed.sql)
3. ✅ Update password in DatabaseConfig.java
4. ✅ Open AirlineManagementApp.java
5. ✅ Click "Run" button
6. ✅ Login with admin/admin123
7. 🎉 Enjoy your Airline Management System!

---

**Need help?** Check the terminal output for error messages!
