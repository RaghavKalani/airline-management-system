package com.airline.dao;

import com.airline.config.DatabaseConfig;
import com.airline.model.Flight;
import com.airline.util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    public boolean createFlight(Flight flight) {
        String sql = "INSERT INTO flights (flight_number, airline_id, origin_airport_id, destination_airport_id, total_seats, base_price, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, flight.getFlightNumber());
            pstmt.setInt(2, flight.getAirlineId());
            pstmt.setInt(3, flight.getOriginAirportId());
            pstmt.setInt(4, flight.getDestinationAirportId());
            pstmt.setInt(5, flight.getTotalSeats());
            pstmt.setBigDecimal(6, flight.getBasePrice());
            pstmt.setString(7, flight.getStatus().name());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    flight.setFlightId(rs.getInt(1));
                }
                Logger.info("Flight created: " + flight.getFlightNumber());
                return true;
            }
        } catch (SQLException e) {
            Logger.error("Error creating flight", e);
        }
        return false;
    }

    public Flight findById(int flightId) {
        String sql = "SELECT f.*, al.airline_name, " +
                     "a1.city as origin_city, a1.airport_code as origin_code, " +
                     "a2.city as destination_city, a2.airport_code as destination_code " +
                     "FROM flights f " +
                     "JOIN airlines al ON f.airline_id = al.airline_id " +
                     "JOIN airports a1 ON f.origin_airport_id = a1.airport_id " +
                     "JOIN airports a2 ON f.destination_airport_id = a2.airport_id " +
                     "WHERE f.flight_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, flightId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractFlightFromResultSet(rs);
            }
        } catch (SQLException e) {
            Logger.error("Error finding flight", e);
        }
        return null;
    }

    public List<Flight> findAll() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT f.*, al.airline_name, " +
                     "a1.city as origin_city, a1.airport_code as origin_code, " +
                     "a2.city as destination_city, a2.airport_code as destination_code " +
                     "FROM flights f " +
                     "JOIN airlines al ON f.airline_id = al.airline_id " +
                     "JOIN airports a1 ON f.origin_airport_id = a1.airport_id " +
                     "JOIN airports a2 ON f.destination_airport_id = a2.airport_id " +
                     "ORDER BY f.flight_number";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                flights.add(extractFlightFromResultSet(rs));
            }
        } catch (SQLException e) {
            Logger.error("Error fetching flights", e);
        }
        return flights;
    }

    public List<Flight> searchFlights(String origin, String destination) {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT f.*, al.airline_name, " +
                     "a1.city as origin_city, a1.airport_code as origin_code, " +
                     "a2.city as destination_city, a2.airport_code as destination_code " +
                     "FROM flights f " +
                     "JOIN airlines al ON f.airline_id = al.airline_id " +
                     "JOIN airports a1 ON f.origin_airport_id = a1.airport_id " +
                     "JOIN airports a2 ON f.destination_airport_id = a2.airport_id " +
                     "WHERE a1.city LIKE ? AND a2.city LIKE ? AND f.status = 'ACTIVE' " +
                     "ORDER BY f.flight_number";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + origin + "%");
            pstmt.setString(2, "%" + destination + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                flights.add(extractFlightFromResultSet(rs));
            }
        } catch (SQLException e) {
            Logger.error("Error searching flights", e);
        }
        return flights;
    }

    public boolean updateFlight(Flight flight) {
        String sql = "UPDATE flights SET flight_number = ?, airline_id = ?, origin_airport_id = ?, " +
                     "destination_airport_id = ?, total_seats = ?, base_price = ?, status = ? " +
                     "WHERE flight_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, flight.getFlightNumber());
            pstmt.setInt(2, flight.getAirlineId());
            pstmt.setInt(3, flight.getOriginAirportId());
            pstmt.setInt(4, flight.getDestinationAirportId());
            pstmt.setInt(5, flight.getTotalSeats());
            pstmt.setBigDecimal(6, flight.getBasePrice());
            pstmt.setString(7, flight.getStatus().name());
            pstmt.setInt(8, flight.getFlightId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error updating flight", e);
        }
        return false;
    }

    public boolean deleteFlight(int flightId) {
        String sql = "DELETE FROM flights WHERE flight_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, flightId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error deleting flight", e);
        }
        return false;
    }

    private Flight extractFlightFromResultSet(ResultSet rs) throws SQLException {
        Flight flight = new Flight();
        flight.setFlightId(rs.getInt("flight_id"));
        flight.setFlightNumber(rs.getString("flight_number"));
        flight.setAirlineId(rs.getInt("airline_id"));
        flight.setOriginAirportId(rs.getInt("origin_airport_id"));
        flight.setDestinationAirportId(rs.getInt("destination_airport_id"));
        flight.setTotalSeats(rs.getInt("total_seats"));
        flight.setBasePrice(rs.getBigDecimal("base_price"));
        flight.setStatus(Flight.FlightStatus.valueOf(rs.getString("status")));
        flight.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        
        // Joined data
        flight.setAirlineName(rs.getString("airline_name"));
        flight.setOriginCity(rs.getString("origin_city"));
        flight.setDestinationCity(rs.getString("destination_city"));
        flight.setOriginCode(rs.getString("origin_code"));
        flight.setDestinationCode(rs.getString("destination_code"));
        
        return flight;
    }
}
