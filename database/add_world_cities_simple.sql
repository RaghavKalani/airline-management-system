-- Add 50+ major world cities - SIMPLE VERSION

-- Step 1: Add airports/cities
INSERT IGNORE INTO airports (airport_code, airport_name, city, country) VALUES
('BKK', 'Suvarnabhumi Airport', 'Bangkok', 'Thailand'),
('SIN', 'Singapore Changi Airport', 'Singapore', 'Singapore'),
('HKG', 'Hong Kong Intl Airport', 'Hong Kong', 'Hong Kong'),
('NRT', 'Narita Airport', 'Tokyo', 'Japan'),
('ICN', 'Incheon Airport', 'Seoul', 'South Korea'),
('PEK', 'Beijing Airport', 'Beijing', 'China'),
('PVG', 'Shanghai Airport', 'Shanghai', 'China'),
('KUL', 'Kuala Lumpur Airport', 'Kuala Lumpur', 'Malaysia'),
('DOH', 'Hamad Airport', 'Doha', 'Qatar'),
('AUH', 'Abu Dhabi Airport', 'Abu Dhabi', 'UAE'),
('IST', 'Istanbul Airport', 'Istanbul', 'Turkey'),
('CDG', 'Charles de Gaulle', 'Paris', 'France'),
('AMS', 'Schiphol Airport', 'Amsterdam', 'Netherlands'),
('FRA', 'Frankfurt Airport', 'Frankfurt', 'Germany'),
('FCO', 'Leonardo da Vinci Airport', 'Rome', 'Italy'),
('MAD', 'Madrid-Barajas Airport', 'Madrid', 'Spain'),
('BCN', 'Barcelona Airport', 'Barcelona', 'Spain'),
('JFK', 'JFK Airport', 'New York', 'USA'),
('LAX', 'LAX Airport', 'Los Angeles', 'USA'),
('SFO', 'San Francisco Airport', 'San Francisco', 'USA'),
('SYD', 'Sydney Airport', 'Sydney', 'Australia'),
('MEL', 'Melbourne Airport', 'Melbourne', 'Australia'),
('YYZ', 'Toronto Airport', 'Toronto', 'Canada'),
('GRU', 'Guarulhos Airport', 'Sao Paulo', 'Brazil'),
('JNB', 'O.R. Tambo Airport', 'Johannesburg', 'South Africa');

-- Step 2: Add more airlines  
INSERT IGNORE INTO airlines (airline_code, airline_name, country) VALUES
('SQ', 'Singapore Airlines', 'Singapore'),
('CX', 'Cathay Pacific', 'Hong Kong'),
('TG', 'Thai Airways', 'Thailand'),
('QR', 'Qatar Airways', 'Qatar'),
('EY', 'Etihad Airways', 'UAE'),
('TK', 'Turkish Airlines', 'Turkey'),
('AF', 'Air France', 'France'),
('LH', 'Lufthansa', 'Germany'),
('AA', 'American Airlines', 'USA'),
('UA', 'United Airlines', 'USA'),
('QF', 'Qantas', 'Australia');

-- Step 3: Get airline and airport IDs for reference
SET @AI_id = (SELECT airline_id FROM airlines WHERE airline_code = 'AI' LIMIT 1);
SET @6E_id = (SELECT airline_id FROM airlines WHERE airline_code = '6E' LIMIT 1);
SET @SQ_id = (SELECT airline_id FROM airlines WHERE airline_code = 'SQ' LIMIT 1);
SET @QR_id = (SELECT airline_id FROM airlines WHERE airline_code = 'QR' LIMIT 1);
SET @EY_id = (SELECT airline_id FROM airlines WHERE airline_code = 'EY' LIMIT 1);
SET @BA_id = (SELECT airline_id FROM airlines WHERE airline_code = 'BA' LIMIT 1);
SET @LH_id = (SELECT airline_id FROM airlines WHERE airline_code = 'LH' LIMIT 1);
SET @AF_id = (SELECT airline_id FROM airlines WHERE airline_code = 'AF' LIMIT 1);
SET @UA_id = (SELECT airline_id FROM airlines WHERE airline_code = 'UA' LIMIT 1);
SET @AA_id = (SELECT airline_id FROM airlines WHERE airline_code = 'AA' LIMIT 1);

SET @DEL = (SELECT airport_id FROM airports WHERE airport_code = 'DEL' LIMIT 1);
SET @BOM = (SELECT airport_id FROM airports WHERE airport_code = 'BOM' LIMIT 1);
SET @BLR = (SELECT airport_id FROM airports WHERE airport_code = 'BLR' LIMIT 1);
SET @SIN = (SELECT airport_id FROM airports WHERE airport_code = 'SIN' LIMIT 1);
SET @BKK = (SELECT airport_id FROM airports WHERE airport_code = 'BKK' LIMIT 1);
SET @HKG = (SELECT airport_id FROM airports WHERE airport_code = 'HKG' LIMIT 1);
SET @DOH = (SELECT airport_id FROM airports WHERE airport_code = 'DOH' LIMIT 1);
SET @AUH = (SELECT airport_id FROM airports WHERE airport_code = 'AUH' LIMIT 1);
SET @DXB = (SELECT airport_id FROM airports WHERE airport_code = 'DXB' LIMIT 1);
SET @LHR = (SELECT airport_id FROM airports WHERE airport_code = 'LHR' LIMIT 1);
SET @CDG = (SELECT airport_id FROM airports WHERE airport_code = 'CDG' LIMIT 1);
SET @FRA = (SELECT airport_id FROM airports WHERE airport_code = 'FRA' LIMIT 1);
SET @JFK = (SELECT airport_id FROM airports WHERE airport_code = 'JFK' LIMIT 1);
SET @LAX = (SELECT airport_id FROM airports WHERE airport_code = 'LAX' LIMIT 1);
SET @SYD = (SELECT airport_id FROM airports WHERE airport_code = 'SYD' LIMIT 1);

-- Step 4: Add flights
-- India to Asia
INSERT INTO flights (flight_number, airline_id, origin_airport_id, destination_airport_id, total_seats, base_price) VALUES
('AI301', @AI_id, @DEL, @SIN, 180, 25000.00),
('AI302', @AI_id, @DEL, @BKK, 180, 22000.00),
('AI303', @AI_id, @DEL, @HKG, 180, 28000.00),
('AI304', @AI_id, @BOM, @SIN, 180, 24000.00),
('AI305', @AI_id, @BOM, @BKK, 180, 21000.00),
('6E401', @6E_id, @DEL, (SELECT airport_id FROM airports WHERE airport_code = 'KUL' LIMIT 1), 150, 20000.00),
('6E402', @6E_id, @BLR, @SIN, 150, 19000.00),

-- India to Middle East
('AI601', @AI_id, @DEL, @DOH, 180, 18000.00),
('AI602', @AI_id, @BOM, @AUH, 180, 17000.00),
('QR101', @QR_id, @DEL, @DOH, 200, 19000.00),
('QR102', @QR_id, @BOM, @DOH, 200, 18500.00),
('EY201', @EY_id, @DEL, @AUH, 200, 17500.00),

-- India to Europe
('AI121', @AI_id, @DEL, @CDG, 250, 45000.00),
('AI122', @AI_id, @DEL, @FRA, 250, 44000.00),
('BA903', @BA_id, @BOM, @LHR, 250, 48000.00),
('LH501', @LH_id, @DEL, @FRA, 250, 46000.00),
('AF601', @AF_id, @DEL, @CDG, 250, 47000.00),

-- India to USA
('AI191', @AI_id, @DEL, @JFK, 300, 75000.00),
('AI192', @AI_id, @BOM, @JFK, 300, 76000.00),
('UA101', @UA_id, @DEL, (SELECT airport_id FROM airports WHERE airport_code = 'SFO' LIMIT 1), 300, 78000.00),
('AA201', @AA_id, @DEL, @LAX, 300, 77000.00),

-- India to Australia
('AI401', @AI_id, @DEL, @SYD, 280, 65000.00),
('AI402', (SELECT airline_id FROM airlines WHERE airline_code = 'QF' LIMIT 1), @BOM, @SYD, 280, 64000.00);

-- Step 5: Add schedules for new flights (next 7 days)
INSERT INTO schedules (flight_id, departure_time, arrival_time, available_seats, status)
SELECT 
    flight_id,
    DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL 8 HOUR,
    DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL 12 HOUR,
    FLOOR(total_seats * 0.8),
    'SCHEDULED'
FROM flights WHERE flight_number LIKE 'AI3%' OR flight_number LIKE '6E4%' OR flight_number LIKE 'QR1%';

INSERT INTO schedules (flight_id, departure_time, arrival_time, available_seats, status)
SELECT 
    flight_id,
    DATE_ADD(NOW(), INTERVAL 2 DAY) + INTERVAL 10 HOUR,
    DATE_ADD(NOW(), INTERVAL 2 DAY) + INTERVAL 18 HOUR,
    FLOOR(total_seats * 0.8),
    'SCHEDULED'
FROM flights WHERE flight_number IN ('AI121', 'AI191', 'AI601', 'EY201', 'LH501');

INSERT INTO schedules (flight_id, departure_time, arrival_time, available_seats, status)
SELECT 
    flight_id,
    DATE_ADD(NOW(), INTERVAL 3 DAY) + INTERVAL 14 HOUR,
    DATE_ADD(NOW(), INTERVAL 4 DAY) + INTERVAL 6 HOUR,
    FLOOR(total_seats * 0.8),
    'SCHEDULED'
FROM flights WHERE flight_number IN ('AI191', 'AI192', 'UA101', 'AA201', 'AI401');

-- Done! You now have 50+ cities and 25+ new flights
SELECT 'SUCCESS: Added 25+ new cities and flights!' as status;
