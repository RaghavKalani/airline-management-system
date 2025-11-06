# 🚀 Airline Management System - Deployment Guide

## ❌ Why Vercel Won't Work

**Vercel is for web applications only.** Your application is a Java Swing desktop application that:
- Runs as a native GUI window on the user's computer
- Uses JFrame, JPanel, and other Swing components
- Cannot run in a web browser
- Requires Java Runtime Environment (JRE) installed on the user's machine

---

## ✅ Recommended Deployment Options

### Option 1: 🖥️ Standalone Executable JAR (Best for Desktop Distribution)

#### Step 1: Build the Executable JAR

```powershell
# Navigate to your project directory
cd C:\Users\ragha\Desktop\flight_management

# Clean and package the application
mvn clean package
```

This creates `airline-management-system.jar` in the `target` folder with all dependencies included.

#### Step 2: Run the Application

```powershell
# Simple double-click the JAR file, or run:
java -jar target/airline-management-system.jar
```

#### Step 3: Distribution

**Distribute the JAR file to users:**
1. Upload to Google Drive, Dropbox, or GitHub Releases
2. Share the download link
3. Users need Java 17+ installed to run it

**User Instructions:**
```
1. Install Java 17 or higher from https://adoptium.net/
2. Download airline-management-system.jar
3. Double-click the JAR file to run
   OR open terminal and run: java -jar airline-management-system.jar
4. Make sure MySQL is installed and airline_db database exists
```

---

### Option 2: 🌐 Convert to Web Application (Requires Major Rewrite)

To deploy on Vercel/Heroku/AWS, you need to **rebuild as a web app**:

#### Technology Options:

**A. Spring Boot + React (Recommended)**
- **Backend**: Convert Java services to Spring Boot REST API
- **Frontend**: Create React/Angular/Vue web interface
- **Database**: Use cloud MySQL (AWS RDS, Azure MySQL, PlanetScale)
- **Deploy**: Backend on Heroku/Railway, Frontend on Vercel

**B. Spring Boot + Thymeleaf**
- **Full-stack**: Spring Boot with server-side rendering
- **Deploy**: Heroku, Railway, or AWS Elastic Beanstalk

#### What Needs to Change:
- ❌ Remove all Swing UI code (JFrame, JPanel, etc.)
- ✅ Create REST API endpoints for all operations
- ✅ Build web-based frontend (HTML/CSS/JavaScript)
- ✅ Use cloud database instead of local MySQL
- ✅ Implement JWT authentication for web

**Estimated Time**: 2-3 weeks for full conversion

---

### Option 3: 🐳 Docker Container (Advanced)

Package the app with MySQL in Docker containers:

#### Create Dockerfile:

```dockerfile
FROM openjdk:17-slim

# Install MySQL client
RUN apt-get update && apt-get install -y mysql-client

# Copy JAR file
COPY target/airline-management-system.jar /app/airline-management-system.jar

# Copy database initialization
COPY database.sql /docker-entrypoint-initdb.d/

WORKDIR /app

# Run the application
CMD ["java", "-jar", "airline-management-system.jar"]
```

#### Create docker-compose.yml:

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: 6y6fCAZj05
      MYSQL_DATABASE: airline_db
    ports:
      - "3306:3306"
    volumes:
      - ./database.sql:/docker-entrypoint-initdb.d/init.sql
      - mysql_data:/var/lib/mysql

  app:
    build: .
    depends_on:
      - mysql
    environment:
      DB_HOST: mysql
      DB_PORT: 3306
      DB_NAME: airline_db
      DB_USER: root
      DB_PASSWORD: 6y6fCAZj05
    ports:
      - "8080:8080"

volumes:
  mysql_data:
```

**Note**: Docker is great for deployment but still requires GUI access (X11 forwarding or VNC).

---

### Option 4: 📦 Native Executable with JPackage

Create platform-specific installers:

```powershell
# Windows installer (.exe)
jpackage --input target --name "Airline Management System" \
  --main-jar airline-management-system.jar \
  --main-class com.airline.AirlineManagementApp \
  --type exe \
  --icon icon.ico \
  --win-menu \
  --win-shortcut

# This creates a Windows installer that bundles Java runtime
```

**Advantages**:
- Users don't need to install Java separately
- Professional installer experience
- Desktop shortcut created automatically

---

## 🎯 Quick Start - Recommended Path

### For Desktop Distribution (Easiest):

1. **Build the JAR**:
```powershell
mvn clean package
```

2. **Test it**:
```powershell
java -jar target/airline-management-system.jar
```

3. **Share the JAR**:
   - Upload to GitHub Releases
   - Share via Google Drive
   - Email to users

4. **Provide Setup Instructions**:
   - Users need Java 17+
   - Users need MySQL with airline_db
   - Provide database.sql for setup

---

### For Web Deployment (More Work):

If you want to deploy on Vercel/cloud, you need to:

1. **Create a new Spring Boot project**
2. **Convert services to REST controllers**
3. **Build React/Vue frontend**
4. **Deploy backend to Railway/Heroku**
5. **Deploy frontend to Vercel**
6. **Use cloud database (PlanetScale/AWS RDS)**

**Would you like me to help convert this to a web application?**

---

## 📋 Current Application Status

✅ **Fully Functional Desktop Application**
- Beautiful animated UI
- Complete CRUD operations
- Secure authentication
- MySQL database integration
- Zero errors or warnings

❌ **Cannot Deploy to Vercel** (Vercel = Web Apps Only)

✅ **Can Deploy As**: Desktop JAR, Docker Container, or Native Installer

---

## 💡 Recommendation

**For immediate use**: Build the JAR file and distribute it as a desktop application.

**For professional deployment**: Convert to Spring Boot + React web application (requires significant development time).

---

## 🆘 Need Help?

Let me know which option you'd like to pursue:
1. Build executable JAR for desktop distribution
2. Convert to web application for Vercel deployment
3. Create Docker container
4. Create native installer with JPackage
