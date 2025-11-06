# Airline Management System - Setup Guide

This guide provides detailed step-by-step instructions for setting up and running the Airline Management System.

## Table of Contents
1. [Prerequisites Installation](#prerequisites-installation)
2. [Database Setup](#database-setup)
3. [Project Configuration](#project-configuration)
4. [Build and Run](#build-and-run)
5. [Troubleshooting](#troubleshooting)

---

## 1. Prerequisites Installation

### 1.1 Install Java JDK 17+

**Windows:**
1. Download JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
2. Run the installer
3. Set `JAVA_HOME` environment variable:
   - Right-click "This PC" → Properties → Advanced System Settings
   - Environment Variables → New System Variable
   - Variable name: `JAVA_HOME`
   - Variable value: `C:\Program Files\Java\jdk-17` (your JDK path)
4. Add to PATH: `%JAVA_HOME%\bin`
5. Verify installation:
   ```powershell
   java -version
   javac -version
   ```

**Linux/Mac:**
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-17-jdk

# macOS (using Homebrew)
brew install openjdk@17

# Verify
java -version
```

### 1.2 Install MySQL 8.x

**Windows:**
1. Download MySQL Installer from [MySQL Downloads](https://dev.mysql.com/downloads/installer/)
2. Run the installer and choose "Custom" installation
3. Select MySQL Server 8.x
4. Set root password (remember this!)
5. Complete installation

**Linux:**
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install mysql-server

# Start MySQL service
sudo systemctl start mysql
sudo systemctl enable mysql

# Secure installation
sudo mysql_secure_installation
```

**macOS:**
```bash
# Using Homebrew
brew install mysql

# Start MySQL
brew services start mysql

# Secure installation
mysql_secure_installation
```

**Verify MySQL Installation:**
```bash
mysql --version
mysql -u root -p
```

### 1.3 Install Maven (Optional)

Maven is required for building the project, but most IDEs have it built-in.

**Windows:**
1. Download from [Maven Downloads](https://maven.apache.org/download.cgi)
2. Extract to `C:\Program Files\Apache\maven`
3. Add to PATH: `C:\Program Files\Apache\maven\bin`
4. Verify: `mvn -version`

**Linux/Mac:**
```bash
# Ubuntu/Debian
sudo apt install maven

# macOS
brew install maven

# Verify
mvn -version
```

---

## 2. Database Setup

### 2.1 Create Database

**Option 1: Using MySQL Command Line**

```bash
# Login to MySQL
mysql -u root -p

# Enter your root password when prompted
```

```sql
-- Create the database
CREATE DATABASE airline_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Verify
SHOW DATABASES;

-- Exit
exit;
```

**Option 2: Using MySQL Workbench**
1. Open MySQL Workbench
2. Connect to local instance
3. File → Run SQL Script
4. Select `database/schema.sql`
5. Execute

### 2.2 Run Schema and Seed Files

**From Command Line:**
```bash
# Navigate to project directory
cd C:\Users\ragha\Desktop\flight_management

# Run schema
mysql -u root -p airline_db < database/schema.sql

# Run seed data
mysql -u root -p airline_db < database/seed.sql
```

**From MySQL Client:**
```bash
mysql -u root -p
```

```sql
USE airline_db;
SOURCE C:/Users/ragha/Desktop/flight_management/database/schema.sql;
SOURCE C:/Users/ragha/Desktop/flight_management/database/seed.sql;

-- Verify tables
SHOW TABLES;

-- Check sample data
SELECT * FROM users;
SELECT * FROM airlines;
SELECT * FROM airports;
```

### 2.3 Verify Database Setup

```sql
-- Check all tables are created
SHOW TABLES;

-- Should show:
-- airlines, airports, bookings, flights, passengers, schedules, users
-- booking_summary (view), flight_occupancy (view)

-- Check sample data
SELECT COUNT(*) FROM users;      -- Should be 3
SELECT COUNT(*) FROM airlines;   -- Should be 6
SELECT COUNT(*) FROM airports;   -- Should be 10
SELECT COUNT(*) FROM flights;    -- Should be 10
SELECT COUNT(*) FROM schedules;  -- Should be 13

-- Test a query
SELECT * FROM booking_summary;
```

---

## 3. Project Configuration

### 3.1 Update Database Credentials

Open `src/main/java/com/airline/config/DatabaseConfig.java` and update:

```java
private static final String URL = "jdbc:mysql://localhost:3306/airline_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_mysql_password_here"; // ← CHANGE THIS
```

### 3.2 Verify Maven Dependencies

The `pom.xml` should contain:
- MySQL Connector/J 8.0.33
- BCrypt (jBCrypt) 0.4
- JUnit 4.13.2 (for testing)

---

## 4. Build and Run

### 4.1 Using Command Line (Maven)

```bash
# Navigate to project directory
cd C:\Users\ragha\Desktop\flight_management

# Clean and compile
mvn clean compile

# Package (creates JAR)
mvn clean package

# Run the application
java -jar target/airline-management-system.jar
```

### 4.2 Using IntelliJ IDEA

1. **Open Project:**
   - File → Open
   - Select `flight_management` folder
   - Click OK

2. **Wait for Maven Import:**
   - IntelliJ will automatically download dependencies
   - Wait for indexing to complete

3. **Configure Run Configuration:**
   - Run → Edit Configurations
   - Click + → Application
   - Name: Airline Management System
   - Main class: `com.airline.AirlineManagementApp`
   - Click OK

4. **Run Application:**
   - Click green play button
   - Or press `Shift + F10`

### 4.3 Using Eclipse

1. **Import Project:**
   - File → Import → Maven → Existing Maven Projects
   - Browse to `flight_management` folder
   - Finish

2. **Update Maven Project:**
   - Right-click project → Maven → Update Project
   - Check "Force Update"

3. **Run Application:**
   - Right-click `AirlineManagementApp.java`
   - Run As → Java Application

### 4.4 Using VS Code

1. **Open Folder:**
   - File → Open Folder
   - Select `flight_management`

2. **Install Extensions:**
   - Extension Pack for Java
   - Maven for Java

3. **Run:**
   - Open `AirlineManagementApp.java`
   - Click "Run" above the main method
   - Or press `F5`

---

## 5. Using the Application

### 5.1 First Login

When the application starts, you'll see the login screen.

**Admin Login:**
- Username: `admin`
- Password: `admin123`

**Customer Login:**
- Username: `john_doe`
- Password: `user123`

### 5.2 Customer Features

After logging in as a customer:

1. **Search Flights:**
   - Go to "Search Flights" tab
   - Enter origin (e.g., "Mumbai")
   - Enter destination (e.g., "Bangalore")
   - Click "Search"
   - Select a flight and click "Book"

2. **Enter Passenger Details:**
   - Fill in passenger information
   - First Name, Last Name, Age, Gender
   - Click OK

3. **View Bookings:**
   - Go to "My Bookings" tab
   - See all your bookings
   - PNR, flight details, status

4. **Cancel Booking:**
   - Select a booking
   - Click "Cancel Booking"
   - Confirm cancellation

### 5.3 Admin Features

After logging in as admin:

1. **Dashboard:**
   - View statistics
   - Total bookings, revenue, etc.

2. **Manage Airlines:**
   - View all airlines
   - See airline codes and countries

3. **Manage Airports:**
   - View all airports
   - See airport codes and cities

4. **Manage Flights:**
   - View all flights
   - See routes, prices, seats

5. **View Bookings:**
   - See all customer bookings
   - Track booking status

### 5.4 Create New Account

1. Click "Register" on login screen
2. Fill in all details:
   - Username (3-20 characters, alphanumeric)
   - Password (minimum 6 characters, must contain letter and digit)
   - Full Name
   - Email
   - Phone
   - Role (Customer/Admin)
3. Click "Register"
4. Login with new credentials

---

## 6. Troubleshooting

### 6.1 Database Connection Errors

**Error:** "Failed to connect to database"

**Solutions:**
1. Check MySQL is running:
   ```bash
   # Windows
   net start MySQL80
   
   # Linux/Mac
   sudo systemctl status mysql
   ```

2. Verify credentials in `DatabaseConfig.java`

3. Test MySQL connection:
   ```bash
   mysql -u root -p
   ```

4. Check database exists:
   ```sql
   SHOW DATABASES;
   USE airline_db;
   SHOW TABLES;
   ```

### 6.2 Compilation Errors

**Error:** "Package does not exist" or "Cannot find symbol"

**Solutions:**
1. Clean and rebuild:
   ```bash
   mvn clean install
   ```

2. In IntelliJ: File → Invalidate Caches → Restart

3. In Eclipse: Project → Clean

### 6.3 Port Already in Use

**Error:** "Port 3306 is already in use"

**Solutions:**
1. Check what's using the port:
   ```bash
   # Windows
   netstat -ano | findstr :3306
   
   # Linux/Mac
   lsof -i :3306
   ```

2. Stop other MySQL instances

3. Change MySQL port or update connection string

### 6.4 Maven Dependency Download Issues

**Solutions:**
1. Check internet connection

2. Clear Maven cache:
   ```bash
   # Windows
   rmdir /s %USERPROFILE%\.m2\repository
   
   # Linux/Mac
   rm -rf ~/.m2/repository
   ```

3. Re-download dependencies:
   ```bash
   mvn clean install -U
   ```

### 6.5 Login Not Working

**Solutions:**
1. Check database has users:
   ```sql
   SELECT * FROM users;
   ```

2. If no users, re-run seed.sql:
   ```bash
   mysql -u root -p airline_db < database/seed.sql
   ```

3. Try default credentials:
   - admin/admin123
   - john_doe/user123

### 6.6 GUI Not Displaying Properly

**Solutions:**
1. Update Java to latest version

2. Try different look and feel (edit `AirlineManagementApp.java`)

3. Check display scaling in OS settings

---

## 7. Additional Configuration

### 7.1 Change Default Port

If port 3306 is occupied, change MySQL to different port:

1. Edit MySQL config:
   ```bash
   # Windows: C:\ProgramData\MySQL\MySQL Server 8.0\my.ini
   # Linux: /etc/mysql/my.cnf
   
   [mysqld]
   port=3307
   ```

2. Restart MySQL

3. Update `DatabaseConfig.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3307/airline_db...";
   ```

### 7.2 Enable Logging

Logs are printed to console by default. To save to file, modify `Logger.java` class.

---

## 8. Next Steps

- Explore all features as Customer and Admin
- Try creating test bookings
- View reports and statistics
- Customize the code for your needs

---

## Support

For issues or questions:
- Check the README.md
- Review error logs in console
- Verify all setup steps completed
- Contact team members listed in README.md

---

**Congratulations! Your Airline Management System is now set up and ready to use!** ✈️
