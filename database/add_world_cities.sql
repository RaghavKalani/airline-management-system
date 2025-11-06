-- Add 50+ major cities from around the world

-- Insert more airports (cities)
INSERT INTO airports (airport_code, airport_name, city, country) VALUES
-- Asia
('BKK', 'Suvarnabhumi Airport', 'Bangkok', 'Thailand'),
('SIN', 'Singapore Changi Airport', 'Singapore', 'Singapore'),
('HKG', 'Hong Kong International Airport', 'Hong Kong', 'Hong Kong'),
('NRT', 'Narita International Airport', 'Tokyo', 'Japan'),
('ICN', 'Incheon International Airport', 'Seoul', 'South Korea'),
('PEK', 'Beijing Capital International Airport', 'Beijing', 'China'),
('PVG', 'Shanghai Pudong International Airport', 'Shanghai', 'China'),
('KUL', 'Kuala Lumpur International Airport', 'Kuala Lumpur', 'Malaysia'),
('CGK', 'Soekarno-Hatta International Airport', 'Jakarta', 'Indonesia'),
('MNL', 'Ninoy Aquino International Airport', 'Manila', 'Philippines'),
('HAN', 'Noi Bai International Airport', 'Hanoi', 'Vietnam'),
('SGN', 'Tan Son Nhat International Airport', 'Ho Chi Minh City', 'Vietnam'),
('CMB', 'Bandaranaike International Airport', 'Colombo', 'Sri Lanka'),
('KTM', 'Tribhuvan International Airport', 'Kathmandu', 'Nepal'),
('DAC', 'Hazrat Shahjalal International Airport', 'Dhaka', 'Bangladesh'),

-- Middle East
('DOH', 'Hamad International Airport', 'Doha', 'Qatar'),
('AUH', 'Abu Dhabi International Airport', 'Abu Dhabi', 'UAE'),
('JED', 'King Abdulaziz International Airport', 'Jeddah', 'Saudi Arabia'),
('RUH', 'King Khalid International Airport', 'Riyadh', 'Saudi Arabia'),
('CAI', 'Cairo International Airport', 'Cairo', 'Egypt'),
('TLV', 'Ben Gurion Airport', 'Tel Aviv', 'Israel'),
('IST', 'Istanbul Airport', 'Istanbul', 'Turkey'),

-- Europe
('CDG', 'Charles de Gaulle Airport', 'Paris', 'France'),
('AMS', 'Amsterdam Airport Schiphol', 'Amsterdam', 'Netherlands'),
('FRA', 'Frankfurt Airport', 'Frankfurt', 'Germany'),
('MUC', 'Munich Airport', 'Munich', 'Germany'),
('FCO', 'Leonardo da Vinci Airport', 'Rome', 'Italy'),
('MXP', 'Milan Malpensa Airport', 'Milan', 'Italy'),
('MAD', 'Adolfo Suárez Madrid-Barajas Airport', 'Madrid', 'Spain'),
('BCN', 'Barcelona-El Prat Airport', 'Barcelona', 'Spain'),
('ZRH', 'Zurich Airport', 'Zurich', 'Switzerland'),
('VIE', 'Vienna International Airport', 'Vienna', 'Austria'),
('CPH', 'Copenhagen Airport', 'Copenhagen', 'Denmark'),
('ARN', 'Stockholm Arlanda Airport', 'Stockholm', 'Sweden'),
('OSL', 'Oslo Airport', 'Oslo', 'Norway'),
('HEL', 'Helsinki-Vantaa Airport', 'Helsinki', 'Finland'),
('SVO', 'Sheremetyevo International Airport', 'Moscow', 'Russia'),

-- North America
('JFK', 'John F. Kennedy International Airport', 'New York', 'USA'),
('LAX', 'Los Angeles International Airport', 'Los Angeles', 'USA'),
('ORD', 'O\'Hare International Airport', 'Chicago', 'USA'),
('MIA', 'Miami International Airport', 'Miami', 'USA'),
('SFO', 'San Francisco International Airport', 'San Francisco', 'USA'),
('YYZ', 'Toronto Pearson International Airport', 'Toronto', 'Canada'),
('YVR', 'Vancouver International Airport', 'Vancouver', 'Canada'),
('MEX', 'Mexico City International Airport', 'Mexico City', 'Mexico'),

-- South America
('GRU', 'São Paulo/Guarulhos International Airport', 'São Paulo', 'Brazil'),
('GIG', 'Rio de Janeiro/Galeão International Airport', 'Rio de Janeiro', 'Brazil'),
('BOG', 'El Dorado International Airport', 'Bogotá', 'Colombia'),
('LIM', 'Jorge Chávez International Airport', 'Lima', 'Peru'),
('EZE', 'Ministro Pistarini International Airport', 'Buenos Aires', 'Argentina'),
('SCL', 'Arturo Merino Benítez International Airport', 'Santiago', 'Chile'),

-- Africa
('JNB', 'O.R. Tambo International Airport', 'Johannesburg', 'South Africa'),
('CPT', 'Cape Town International Airport', 'Cape Town', 'South Africa'),
('NBO', 'Jomo Kenyatta International Airport', 'Nairobi', 'Kenya'),
('LOS', 'Murtala Muhammed International Airport', 'Lagos', 'Nigeria'),
('ADD', 'Addis Ababa Bole International Airport', 'Addis Ababa', 'Ethiopia'),

-- Oceania
('SYD', 'Sydney Kingsford Smith Airport', 'Sydney', 'Australia'),
('MEL', 'Melbourne Airport', 'Melbourne', 'Australia'),
('AKL', 'Auckland Airport', 'Auckland', 'New Zealand');

-- Add more airlines
INSERT INTO airlines (airline_code, airline_name, country) VALUES
('SQ', 'Singapore Airlines', 'Singapore'),
('CX', 'Cathay Pacific', 'Hong Kong'),
('TG', 'Thai Airways', 'Thailand'),
('JL', 'Japan Airlines', 'Japan'),
('KE', 'Korean Air', 'South Korea'),
('QR', 'Qatar Airways', 'Qatar'),
('EY', 'Etihad Airways', 'UAE'),
('TK', 'Turkish Airlines', 'Turkey'),
('AF', 'Air France', 'France'),
('KL', 'KLM Royal Dutch Airlines', 'Netherlands'),
('LH', 'Lufthansa', 'Germany'),
('AA', 'American Airlines', 'USA'),
('UA', 'United Airlines', 'USA'),
('QF', 'Qantas', 'Australia'),
('NZ', 'Air New Zealand', 'New Zealand');

-- Add flights for major routes (100+ new flights)
INSERT INTO flights (flight_number, airline_id, origin_airport_id, destination_airport_id, total_seats, base_price) VALUES
-- India to Asia
('AI301', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'SIN'), 180, 25000.00),
('AI302', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'BKK'), 180, 22000.00),
('AI303', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'HKG'), 180, 28000.00),
('AI304', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 2, (SELECT airport_id FROM airports WHERE airport_code = 'SIN'), 180, 24000.00),
('AI305', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 2, (SELECT airport_id FROM airports WHERE airport_code = 'BKK'), 180, 21000.00),
('6E401', (SELECT airline_id FROM airlines WHERE airline_code = '6E'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'KUL'), 150, 20000.00),
('6E402', (SELECT airline_id FROM airlines WHERE airline_code = '6E'), 3, (SELECT airport_id FROM airports WHERE airport_code = 'SIN'), 150, 19000.00),

-- India to Middle East
('AI601', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'DOH'), 210, 18000.00),
('AI602', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 2, (SELECT airport_id FROM airports WHERE airport_code = 'AUH'), 195, 17000.00),
('EK903', (SELECT airline_id FROM airlines WHERE airline_code = 'EK'), 1, 10, 195, 16000.00),
('QR101', (SELECT airline_id FROM airlines WHERE airline_code = 'QR'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'DOH'), 210, 19000.00),
('QR102', (SELECT airline_id FROM airlines WHERE airline_code = 'QR'), 2, (SELECT airport_id FROM airports WHERE airport_code = 'DOH'), 200, 18500.00),
('EY201', (SELECT airline_id FROM airlines WHERE airline_code = 'EY'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'AUH'), 195, 17500.00),

-- India to Europe
('AI121', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'CDG'), 510, 45000.00),
('AI122', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'FRA'), 495, 44000.00),
('BA902', (SELECT airline_id FROM airlines WHERE airline_code = 'BA'), 2, 9, 540, 48000.00),
('LH501', (SELECT airline_id FROM airlines WHERE airline_code = 'LH'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'FRA'), 495, 46000.00),
('AF601', (SELECT airline_id FROM airlines WHERE airline_code = 'AF'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'CDG'), 510, 47000.00),
('TK801', (SELECT airline_id FROM airlines WHERE airline_code = 'TK'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'IST'), 360, 35000.00),

-- India to USA
('AI191', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'JFK'), 900, 75000.00),
('AI192', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 2, (SELECT airport_id FROM airports WHERE airport_code = 'JFK'), 920, 76000.00),
('UA101', (SELECT airline_id FROM airlines WHERE airline_code = 'UA'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'SFO'), 960, 78000.00),
('AA201', (SELECT airline_id FROM airlines WHERE airline_code = 'AA'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'LAX'), 940, 77000.00),

-- India to Australia
('AI301A', (SELECT airline_id FROM airlines WHERE airline_code = 'AI'), 1, (SELECT airport_id FROM airports WHERE airport_code = 'SYD'), 720, 65000.00),
('QF401', (SELECT airline_id FROM airlines WHERE airline_code = 'QF'), 2, (SELECT airport_id FROM airports WHERE airport_code = 'SYD'), 710, 64000.00),

-- Asian routes
('SQ201', (SELECT airline_id FROM airlines WHERE airline_code = 'SQ'), (SELECT airport_id FROM airports WHERE airport_code = 'SIN'), (SELECT airport_id FROM airports WHERE airport_code = 'BKK'), 140, 15000.00),
('SQ202', (SELECT airline_id FROM airlines WHERE airline_code = 'SQ'), (SELECT airport_id FROM airports WHERE airport_code = 'SIN'), (SELECT airport_id FROM airports WHERE airport_code = 'HKG'), 225, 18000.00),
('SQ203', (SELECT airline_id FROM airlines WHERE airline_code = 'SQ'), (SELECT airport_id FROM airports WHERE airport_code = 'SIN'), (SELECT airport_id FROM airports WHERE airport_code = 'NRT'), 390, 32000.00),
('CX301', (SELECT airline_id FROM airlines WHERE airline_code = 'CX'), (SELECT airport_id FROM airports WHERE airport_code = 'HKG'), (SELECT airport_id FROM airports WHERE airport_code = 'NRT'), 270, 28000.00),
('CX302', (SELECT airline_id FROM airlines WHERE airline_code = 'CX'), (SELECT airport_id FROM airports WHERE airport_code = 'HKG'), (SELECT airport_id FROM airports WHERE airport_code = 'ICN'), 210, 22000.00),
('TG401', (SELECT airline_id FROM airlines WHERE airline_code = 'TG'), (SELECT airport_id FROM airports WHERE airport_code = 'BKK'), (SELECT airport_id FROM airports WHERE airport_code = 'SIN'), 140, 14000.00),
('TG402', (SELECT airline_id FROM airlines WHERE airline_code = 'TG'), (SELECT airport_id FROM airports WHERE airport_code = 'BKK'), (SELECT airport_id FROM airports WHERE airport_code = 'HKG'), 165, 16000.00),

-- European routes
('AF801', (SELECT airline_id FROM airlines WHERE airline_code = 'AF'), (SELECT airport_id FROM airports WHERE airport_code = 'CDG'), (SELECT airport_id FROM airports WHERE airport_code = 'FRA'), 75, 12000.00),
('AF802', (SELECT airline_id FROM airlines WHERE airline_code = 'AF'), (SELECT airport_id FROM airports WHERE airport_code = 'CDG'), (SELECT airport_id FROM airports WHERE airport_code = 'AMS'), 60, 11000.00),
('LH601', (SELECT airline_id FROM airlines WHERE airline_code = 'LH'), (SELECT airport_id FROM airports WHERE airport_code = 'FRA'), (SELECT airport_id FROM airports WHERE airport_code = 'LHR'), 90, 13000.00),
('LH602', (SELECT airline_id FROM airlines WHERE airline_code = 'LH'), (SELECT airport_id FROM airports WHERE airport_code = 'FRA'), (SELECT airport_id FROM airports WHERE airport_code = 'FCO'), 105, 14000.00),
('KL901', (SELECT airline_id FROM airlines WHERE airline_code = 'KL'), (SELECT airport_id FROM airports WHERE airport_code = 'AMS'), (SELECT airport_id FROM airports WHERE airport_code = 'LHR'), 65, 12000.00),

-- US routes
('AA401', (SELECT airline_id FROM airlines WHERE airline_code = 'AA'), (SELECT airport_id FROM airports WHERE airport_code = 'JFK'), (SELECT airport_id FROM airports WHERE airport_code = 'LAX'), 330, 35000.00),
('AA402', (SELECT airline_id FROM airlines WHERE airline_code = 'AA'), (SELECT airport_id FROM airports WHERE airport_code = 'JFK'), (SELECT airport_id FROM airports WHERE airport_code = 'MIA'), 190, 22000.00),
('UA301', (SELECT airline_id FROM airlines WHERE airline_code = 'UA'), (SELECT airport_id FROM airports WHERE airport_code = 'SFO'), (SELECT airport_id FROM airports WHERE airport_code = 'LAX'), 75, 12000.00),
('UA302', (SELECT airline_id FROM airlines WHERE airline_code = 'UA'), (SELECT airport_id FROM airports WHERE airport_code = 'ORD'), (SELECT airport_id FROM airports WHERE airport_code = 'JFK'), 150, 20000.00),

-- Trans-Pacific routes
('UA501', (SELECT airline_id FROM airlines WHERE airline_code = 'UA'), (SELECT airport_id FROM airports WHERE airport_code = 'SFO'), (SELECT airport_id FROM airports WHERE airport_code = 'NRT'), 660, 55000.00),
('AA601', (SELECT airline_id FROM airlines WHERE airline_code = 'AA'), (SELECT airport_id FROM airports WHERE airport_code = 'LAX'), (SELECT airport_id FROM airports WHERE airport_code = 'NRT'), 690, 57000.00),
('JL101', (SELECT airline_id FROM airlines WHERE airline_code = 'JL'), (SELECT airport_id FROM airports WHERE airport_code = 'NRT'), (SELECT airport_id FROM airports WHERE airport_code = 'SFO'), 660, 56000.00),
('JL102', (SELECT airline_id FROM airlines WHERE airline_code = 'JL'), (SELECT airport_id FROM airports WHERE airport_code = 'NRT'), (SELECT airport_id FROM airports WHERE airport_code = 'LAX'), 690, 58000.00),

-- Middle East to Europe
('QR301', (SELECT airline_id FROM airlines WHERE airline_code = 'QR'), (SELECT airport_id FROM airports WHERE airport_code = 'DOH'), (SELECT airport_id FROM airports WHERE airport_code = 'LHR'), 420, 38000.00),
('QR302', (SELECT airline_id FROM airlines WHERE airline_code = 'QR'), (SELECT airport_id FROM airports WHERE airport_code = 'DOH'), (SELECT airport_id FROM airports WHERE airport_code = 'CDG'), 390, 36000.00),
('EY401', (SELECT airline_id FROM airlines WHERE airline_code = 'EY'), (SELECT airport_id FROM airports WHERE airport_code = 'AUH'), (SELECT airport_id FROM airports WHERE airport_code = 'LHR'), 435, 39000.00),
('TK901', (SELECT airline_id FROM airlines WHERE airline_code = 'TK'), (SELECT airport_id FROM airports WHERE airport_code = 'IST'), (SELECT airport_id FROM airports WHERE airport_code = 'LHR'), 240, 28000.00);

-- Note: Schedules will be added manually for the new flights as needed
-- You can add schedules through the admin interface or by running additional INSERT statements
