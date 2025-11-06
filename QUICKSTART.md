# Quick Start Guide - Airline Management System

## 🚀 Quick Setup (5 Minutes)

### Step 1: Install Prerequisites
- ✅ Java 17+ installed
- ✅ MySQL 8.x installed and running
- ✅ IDE (IntelliJ/Eclipse/VS Code) - optional but recommended

### Step 2: Set Up Database

**Windows PowerShell:**
```powershell
cd C:\Users\ragha\Desktop\flight_management
.\setup-database.bat
```

**Or manually:**
```bash
mysql -u root -p
# Enter password, then:
source database/schema.sql
source database/seed.sql
exit
```

### Step 3: Update Database Password

Edit `src/main/java/com/airline/config/DatabaseConfig.java`:
```java
private static final String PASSWORD = "your_password"; // Line 9
```

### Step 4: Run the Application

**Option A: Using IDE (Recommended)**
- Open project in IntelliJ IDEA / Eclipse / VS Code
- Run `AirlineManagementApp.java`

**Option B: Using Maven**
```bash
mvn clean package
java -jar target/airline-management-system.jar
```

**Option C: Using Script**
```bash
.\run.bat
```

### Step 5: Login

**Admin Account:**
- Username: `admin`
- Password: `admin123`

**Customer Account:**
- Username: `john_doe`
- Password: `user123`

---

## 📁 Project Structure

```
flight_management/
├── src/main/java/com/airline/
│   ├── AirlineManagementApp.java    ← START HERE
│   ├── config/DatabaseConfig.java   ← Update MySQL password
│   ├── dao/          ← Database operations (JDBC)
│   ├── model/        ← Domain objects
│   ├── service/      ← Business logic
│   ├── ui/           ← Swing GUI
│   └── util/         ← Helper utilities
├── database/
│   ├── schema.sql    ← Database structure
│   └── seed.sql      ← Sample data
├── docs/
│   └── setup-guide.md
├── pom.xml           ← Maven config
└── README.md
```

---

## 🎯 Key Features

### For Customers
- 🔍 Search flights by origin/destination
- 📝 Book tickets with passenger details
- 📋 View booking history
- ❌ Cancel bookings
- 🎫 Get PNR for each booking

### For Admins
- 📊 Dashboard with statistics
- ✈️ View all airlines, airports, flights
- 📅 Manage schedules
- 📖 View all bookings
- 📈 Revenue reports

---

## 🛠️ Common Tasks

### Create New Booking (Customer)
1. Login as customer
2. Click "Search Flights" tab
3. Enter origin (e.g., "Delhi") and destination (e.g., "Mumbai")
4. Click "Search"
5. Select a flight → Click "Book Selected Flight"
6. Enter number of passengers
7. Fill passenger details
8. Get PNR confirmation

### Cancel Booking (Customer)
1. Go to "My Bookings" tab
2. Select booking to cancel
3. Click "Cancel Booking"
4. Confirm cancellation
5. Seats automatically restored

### View Statistics (Admin)
1. Login as admin
2. Dashboard shows:
   - Total bookings
   - Confirmed/Cancelled/Completed counts
   - Total revenue
3. Click tabs to view Airlines, Airports, Flights, Bookings

---

## ⚡ Troubleshooting

### Problem: "Failed to connect to database"
**Solutions:**
1. Check MySQL is running: `mysql -u root -p`
2. Verify password in `DatabaseConfig.java`
3. Ensure database exists: `SHOW DATABASES;`

### Problem: "Login failed"
**Solutions:**
1. Use default credentials (admin/admin123)
2. Check users table: `SELECT * FROM users;`
3. Re-run seed.sql if no users exist

### Problem: "No flights found"
**Solutions:**
1. Check schedules table: `SELECT * FROM schedules;`
2. Try searching "Mumbai" to "Bangalore"
3. Re-run seed.sql to add sample flights

### Problem: Maven not found
**Solutions:**
1. Use IDE to build and run instead
2. Or install Maven: https://maven.apache.org/download.cgi
3. Add to PATH environment variable

---

## 📊 Database Tables

| Table | Description | Key Columns |
|-------|-------------|-------------|
| `users` | User accounts | username, password_hash, role |
| `airlines` | Airline info | airline_code, airline_name |
| `airports` | Airport details | airport_code, city |
| `flights` | Flight routes | flight_number, origin, destination |
| `schedules` | Flight timings | departure_time, available_seats |
| `bookings` | Booking records | pnr, user_id, schedule_id |
| `passengers` | Passenger info | first_name, last_name, age |

---

## 🔐 Security Features

- ✅ **BCrypt** password hashing (10 rounds)
- ✅ **Parameterized SQL** queries (SQL injection prevention)
- ✅ **Role-based access** control (Admin/Customer)
- ✅ **Session management** with logout
- ✅ **Input validation** (email, phone, username)

---

## 📝 Sample Workflow

### Complete Booking Flow:
1. Customer searches: Delhi → Mumbai
2. System queries schedules with available seats
3. Customer selects flight AI101
4. Enters 2 passengers
5. Fills details for each passenger
6. System:
   - Generates unique PNR
   - Creates booking record
   - Adds passenger records
   - Reduces available seats
   - All in single ACID transaction
7. Customer receives PNR confirmation

---

## 🎓 Technologies Used

- **Java 17** - Programming language
- **Java Swing** - GUI framework
- **MySQL 8.x** - Database
- **JDBC** - Database connectivity
- **BCrypt** - Password encryption
- **Maven** - Build tool

---

## 📞 Support

**For issues:**
1. Check [setup-guide.md](docs/setup-guide.md)
2. Review console logs
3. Verify MySQL connection
4. Check database has data

**Team:**
- Aryan Gupta (RA2411026011336)
- Raghav Kalani (RA2411026011312)
- Ranveer Sinha (RA2411026011339)
- Alok Pradhan (RA2411026011344)

---

## ✅ Testing Checklist

- [ ] MySQL installed and running
- [ ] Database created and populated
- [ ] Password updated in DatabaseConfig.java
- [ ] Project opens without errors in IDE
- [ ] Can login as admin (admin/admin123)
- [ ] Can login as customer (john_doe/user123)
- [ ] Can search flights
- [ ] Can create booking
- [ ] Can view bookings
- [ ] Can cancel booking
- [ ] Admin can view dashboard
- [ ] Admin can see all bookings

---

**Happy Coding! ✈️**
