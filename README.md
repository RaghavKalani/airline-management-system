# Airline Management System

A comprehensive Java-based airline management application with Java Swing GUI and MySQL database backend.

## Team Members
- **Aryan Gupta** (RA2411026011336)
- **Raghav Kalani** (RA2411026011312)
- **Ranveer Sinha** (RA2411026011339)
- **Alok Pradhan** (RA2411026011344)

**Course:** Advanced Programming Practice (21CSC203P)  
**Faculty:** R.Vidhya (AJ-2)

## 🌟 Features

### 🎨 Beautiful Animated UI
- **Login Screen**: Floating particles, bouncing airplane icon, smooth gradient animations
- **Customer Dashboard**: Ripple effects, glowing search button, pulsing user icon, animated gradient header
- **Admin Dashboard**: Clean professional interface optimized for productivity
- **Dark Theme**: Carefully designed color scheme with excellent text contrast
- **Responsive Design**: Smooth hover effects and intuitive navigation

### 🔐 Security Features
- **BCrypt Password Hashing**: Industry-standard password encryption
- **Role-Based Access Control**: Separate dashboards for Admin and Customer roles
- **Session Management**: Secure user sessions with proper logout functionality
- **SQL Injection Prevention**: Parameterized queries throughout

### 👤 For Customers
- ✈️ **Search Flights**: Advanced search with origin, destination, and date filters
- 📋 **Book Flights**: Easy booking process with seat selection
- �️ **View Bookings**: See all personal bookings with status
- ❌ **Cancel Bookings**: Cancel reservations with confirmation
- 👤 **User Registration**: New customers can register with validation

### 👨‍💼 For Administrators
- 📊 **Dashboard Statistics**: Real-time metrics (total flights, bookings, revenue)
- ✈️ **Airline Management**: Add, edit, delete, and search airlines
- 🛫 **Airport Management**: Complete CRUD operations for airports
- 📅 **Flight Management**: Create and manage flight schedules with dynamic form
- 📖 **Booking Management**: View all bookings with search and filter capabilities
- 📈 **Generate Reports**: Comprehensive operational reports

## Technology Stack

- **Language:** Java 17
- **GUI Framework:** Java Swing
- **Database:** MySQL 8.x
- **Build Tool:** Maven
- **JDBC Driver:** MySQL Connector/J 8.0.33
- **Password Hashing:** BCrypt (jBCrypt 0.4)

## Architecture

The application follows a **layered architecture**:

```
┌─────────────────────────────────┐
│     UI Layer (Swing)            │
├─────────────────────────────────┤
│     Service Layer               │
├─────────────────────────────────┤
│     DAO Layer (JDBC)            │
├─────────────────────────────────┤
│     Database (MySQL)            │
└─────────────────────────────────┘
```

## Project Structure

```
airline-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/airline/
│   │   │   ├── AirlineManagementApp.java    # Main entry point
│   │   │   ├── config/
│   │   │   │   └── DatabaseConfig.java       # Database connection
│   │   │   ├── dao/                          # Data Access Objects
│   │   │   │   ├── UserDAO.java
│   │   │   │   ├── FlightDAO.java
│   │   │   │   ├── BookingDAO.java
│   │   │   │   ├── PassengerDAO.java
│   │   │   │   ├── AirlineDAO.java
│   │   │   │   └── AirportDAO.java
│   │   │   ├── model/                        # Domain models
│   │   │   │   ├── User.java
│   │   │   │   ├── Flight.java
│   │   │   │   ├── Booking.java
│   │   │   │   ├── Passenger.java
│   │   │   │   ├── Airline.java
│   │   │   │   ├── Airport.java
│   │   │   │   └── Schedule.java
│   │   │   ├── service/                      # Business logic
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── FlightService.java
│   │   │   │   ├── BookingService.java
│   │   │   │   ├── AdminService.java
│   │   │   │   └── ReportService.java
│   │   │   ├── ui/                           # Swing GUI
│   │   │   │   ├── LoginFrame.java
│   │   │   │   ├── RegisterFrame.java
│   │   │   │   ├── AdminDashboardFrame.java
│   │   │   │   ├── CustomerDashboardFrame.java
│   │   │   │   └── FlightSearchFrame.java
│   │   │   └── util/                         # Utilities
│   │   │       ├── PasswordUtil.java
│   │   │       ├── ValidationUtil.java
│   │   │       └── Logger.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
├── database/
│   ├── schema.sql                            # Database schema
│   └── seed.sql                              # Sample data
├── pom.xml                                   # Maven configuration
└── README.md
```

## Prerequisites

1. **Java Development Kit (JDK) 17+**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify installation: `java -version`

2. **MySQL 8.x**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Verify installation: `mysql --version`

3. **Maven** (optional, embedded in most IDEs)
   - Download from: https://maven.apache.org/download.cgi
   - Verify installation: `mvn -version`

4. **IDE** (recommended)
   - IntelliJ IDEA / Eclipse / VS Code

## 🚀 Installation & Setup

### Prerequisites

1. **Java Development Kit (JDK) 17+**
   - Download from [Adoptium](https://adoptium.net/)
   - Verify: `java -version`

2. **Apache Maven 3.8+**
   - Download from [Maven Official Site](https://maven.apache.org/download.cgi)
   - Verify: `mvn -version`

3. **MySQL Server 8.0+**
   - Download from [MySQL Official Site](https://dev.mysql.com/downloads/mysql/)
   - Verify: `mysql --version`

### Quick Start

1. **Clone the Repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/flight_management.git
   cd flight_management
   ```

2. **Set up MySQL Database**
   ```bash
   mysql -u root -p < database.sql
   ```
   Or using MySQL Workbench:
   - Open MySQL Workbench
   - File → Run SQL Script
   - Select `database.sql`

3. **Configure Database Connection** (if needed)
   - Edit `src/main/java/com/airline/util/DatabaseConnection.java`
   - Update MySQL credentials:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/airline_db";
   private static final String USER = "root";
   private static final String PASSWORD = "your_password";
   ```

4. **Build the Project**
   ```bash
   mvn clean install
   ```

5. **Run the Application**
   ```bash
   # Option 1: Run the JAR file
   java -jar target/airline-management-system.jar
   
   # Option 2: Using Maven
   mvn exec:java
   
   # Option 3: From IDE - Run AirlineManagementApp.java
   ```

## 🔑 Default Login Credentials

### Admin Account
- **Username:** `admin`
- **Password:** `admin123`
- **Access:** Full system management, reports, all CRUD operations

### Customer Account
- **Username:** `raghav`
- **Password:** `raghav123`
- **Access:** Flight search, booking, view/cancel own bookings

*Note: You can register new customer accounts through the registration page.*

## Database Schema

The system uses the following main tables:
- `users` - User accounts (admin/customer)
- `airlines` - Airline information
- `airports` - Airport details
- `flights` - Flight routes
- `schedules` - Flight schedules with timing
- `bookings` - Booking records
- `passengers` - Passenger details

## Key Features Implementation

### Authentication & Security
- ✅ BCrypt password hashing
- ✅ Role-based access control (Admin/Customer)
- ✅ Parameterized SQL queries (SQL injection prevention)

### Booking Engine
- ✅ Real-time seat availability
- ✅ ACID transactions for booking
- ✅ Automatic PNR generation
- ✅ Booking cancellation with seat restoration

### Admin Features
- ✅ CRUD operations for airlines, airports, flights
- ✅ Schedule management
- ✅ Booking reports and statistics

### Performance
- ✅ Search < 2 seconds
- ✅ Booking < 3 seconds
- ✅ Indexed database queries

## Development

### Build Commands

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package as JAR
mvn clean package

# Run from source
mvn exec:java -Dexec.mainClass="com.airline.AirlineManagementApp"
```

## Troubleshooting

### Database Connection Failed
- Ensure MySQL is running: `mysql -u root -p`
- Check credentials in `DatabaseConfig.java`
- Verify database exists: `SHOW DATABASES;`

### Port Already in Use
- MySQL default port: 3306
- Change port in connection string if needed

### Class Not Found Error
- Run: `mvn clean install`
- Rebuild project in IDE

## Contributors

This project was developed as part of the Advanced Programming Practice course at SRM Institute of Science and Technology.

## License

This project is for educational purposes only.

## Contact

For issues or questions, please contact the team members listed above.

---
**© 2025 Airline Management System - AJ-2 Team**
