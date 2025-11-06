-- Seed Data for Airline Management System
USE airline_db;

-- Insert Admin and Sample Users (password: admin123 and user123 hashed with BCrypt)
INSERT INTO users (username, password_hash, full_name, email, phone, role) VALUES
('admin', '$2a$10$X7d2K5Z8n0FKzHjGH7YGS.rN0l5U9cQ8LrYJQz5sK2aYRZY8YJzIK', 'System Administrator', 'admin@airline.com', '1234567890', 'ADMIN'),
('john_doe', '$2a$10$Y8e3L6A9o1GLAiKjI8ZHT.sO1m6V0dR9MsZKRa6tL3bZSaZ9ZKaJL', 'John Doe', 'john@email.com', '9876543210', 'CUSTOMER'),
('jane_smith', '$2a$10$Y8e3L6A9o1GLAiKjI8ZHT.sO1m6V0dR9MsZKRa6tL3bZSaZ9ZKaJL', 'Jane Smith', 'jane@email.com', '9876543211', 'CUSTOMER');

-- Insert Airlines
INSERT INTO airlines (airline_code, airline_name, country) VALUES
('AI', 'Air India', 'India'),
('6E', 'IndiGo', 'India'),
('SG', 'SpiceJet', 'India'),
('UK', 'Vistara', 'India'),
('BA', 'British Airways', 'United Kingdom'),
('EK', 'Emirates', 'UAE');

-- Insert Airports
INSERT INTO airports (airport_code, airport_name, city, country) VALUES
('DEL', 'Indira Gandhi International Airport', 'Delhi', 'India'),
('BOM', 'Chhatrapati Shivaji Maharaj International Airport', 'Mumbai', 'India'),
('BLR', 'Kempegowda International Airport', 'Bangalore', 'India'),
('MAA', 'Chennai International Airport', 'Chennai', 'India'),
('CCU', 'Netaji Subhas Chandra Bose International Airport', 'Kolkata', 'India'),
('HYD', 'Rajiv Gandhi International Airport', 'Hyderabad', 'India'),
('GOI', 'Goa International Airport', 'Goa', 'India'),
('AMD', 'Sardar Vallabhbhai Patel International Airport', 'Ahmedabad', 'India'),
('LHR', 'London Heathrow Airport', 'London', 'United Kingdom'),
('DXB', 'Dubai International Airport', 'Dubai', 'UAE');

-- Insert Flights
INSERT INTO flights (flight_number, airline_id, origin_airport_id, destination_airport_id, total_seats, base_price, status) VALUES
('AI101', 1, 1, 2, 180, 4500.00, 'ACTIVE'),
('6E202', 2, 2, 3, 150, 3200.00, 'ACTIVE'),
('SG303', 3, 3, 4, 160, 2800.00, 'ACTIVE'),
('UK404', 4, 1, 3, 170, 4000.00, 'ACTIVE'),
('AI505', 1, 2, 5, 180, 5000.00, 'ACTIVE'),
('6E606', 2, 4, 6, 150, 3500.00, 'ACTIVE'),
('SG707', 3, 1, 7, 140, 6500.00, 'ACTIVE'),
('UK808', 4, 3, 8, 170, 3000.00, 'ACTIVE'),
('BA901', 5, 1, 9, 250, 35000.00, 'ACTIVE'),
('EK902', 6, 2, 10, 300, 28000.00, 'ACTIVE');

-- Insert Schedules (next 7 days)
INSERT INTO schedules (flight_id, departure_time, arrival_time, available_seats, status) VALUES
-- Today
(1, DATE_ADD(NOW(), INTERVAL 5 HOUR), DATE_ADD(NOW(), INTERVAL 7 HOUR), 180, 'SCHEDULED'),
(2, DATE_ADD(NOW(), INTERVAL 8 HOUR), DATE_ADD(NOW(), INTERVAL 10 HOUR), 150, 'SCHEDULED'),
(3, DATE_ADD(NOW(), INTERVAL 12 HOUR), DATE_ADD(NOW(), INTERVAL 14 HOUR), 160, 'SCHEDULED'),
-- Tomorrow
(4, DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL 2 HOUR, 170, 'SCHEDULED'),
(5, DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL 6 HOUR, DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL 8 HOUR, 180, 'SCHEDULED'),
(6, DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL 10 HOUR, DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL 12 HOUR, 150, 'SCHEDULED'),
-- Day after tomorrow
(7, DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 2 DAY) + INTERVAL 2 HOUR, 140, 'SCHEDULED'),
(8, DATE_ADD(NOW(), INTERVAL 2 DAY) + INTERVAL 8 HOUR, DATE_ADD(NOW(), INTERVAL 2 DAY) + INTERVAL 10 HOUR, 170, 'SCHEDULED'),
-- International flights (3 days ahead)
(9, DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 3 DAY) + INTERVAL 9 HOUR, 250, 'SCHEDULED'),
(10, DATE_ADD(NOW(), INTERVAL 3 DAY) + INTERVAL 4 HOUR, DATE_ADD(NOW(), INTERVAL 3 DAY) + INTERVAL 8 HOUR, 300, 'SCHEDULED'),
-- More schedules for next week
(1, DATE_ADD(NOW(), INTERVAL 4 DAY), DATE_ADD(NOW(), INTERVAL 4 DAY) + INTERVAL 2 HOUR, 180, 'SCHEDULED'),
(2, DATE_ADD(NOW(), INTERVAL 5 DAY), DATE_ADD(NOW(), INTERVAL 5 DAY) + INTERVAL 2 HOUR, 150, 'SCHEDULED'),
(3, DATE_ADD(NOW(), INTERVAL 6 DAY), DATE_ADD(NOW(), INTERVAL 6 DAY) + INTERVAL 2 HOUR, 160, 'SCHEDULED');

-- Insert Sample Bookings (for customer john_doe)
INSERT INTO bookings (pnr, user_id, schedule_id, total_passengers, total_amount, status, payment_status) VALUES
('PNR001', 2, 1, 2, 9000.00, 'CONFIRMED', 'PAID'),
('PNR002', 2, 4, 1, 4000.00, 'CONFIRMED', 'PAID');

-- Insert Sample Passengers
INSERT INTO passengers (booking_id, first_name, last_name, age, gender, seat_number) VALUES
(1, 'John', 'Doe', 35, 'MALE', '12A'),
(1, 'Mary', 'Doe', 32, 'FEMALE', '12B'),
(2, 'John', 'Doe', 35, 'MALE', '15C');

-- Update available seats after bookings
UPDATE schedules SET available_seats = available_seats - 2 WHERE schedule_id = 1;
UPDATE schedules SET available_seats = available_seats - 1 WHERE schedule_id = 4;
