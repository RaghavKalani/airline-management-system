package com.airline.dao;

import com.airline.config.DatabaseConfig;
import com.airline.model.Airport;
import com.airline.util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AirportDAO {

    public boolean createAirport(Airport airport) {
        String sql = "INSERT INTO airports (airport_code, airport_name, city, country) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, airport.getAirportCode());
            pstmt.setString(2, airport.getAirportName());
            pstmt.setString(3, airport.getCity());
            pstmt.setString(4, airport.getCountry());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    airport.setAirportId(rs.getInt(1));
                }
                Logger.info("Airport created: " + airport.getAirportName());
                return true;
            }
        } catch (SQLException e) {
            Logger.error("Error creating airport", e);
        }
        return false;
    }

    public Airport findById(int airportId) {
        String sql = "SELECT * FROM airports WHERE airport_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, airportId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractAirportFromResultSet(rs);
            }
        } catch (SQLException e) {
            Logger.error("Error finding airport", e);
        }
        return null;
    }

    public List<Airport> findAll() {
        List<Airport> airports = new ArrayList<>();
        String sql = "SELECT * FROM airports ORDER BY city";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                airports.add(extractAirportFromResultSet(rs));
            }
        } catch (SQLException e) {
            Logger.error("Error fetching airports", e);
        }
        return airports;
    }

    public List<Airport> searchByCity(String city) {
        List<Airport> airports = new ArrayList<>();
        String sql = "SELECT * FROM airports WHERE city LIKE ? ORDER BY city";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + city + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                airports.add(extractAirportFromResultSet(rs));
            }
        } catch (SQLException e) {
            Logger.error("Error searching airports by city", e);
        }
        return airports;
    }

    public boolean updateAirport(Airport airport) {
        String sql = "UPDATE airports SET airport_code = ?, airport_name = ?, city = ?, country = ? WHERE airport_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, airport.getAirportCode());
            pstmt.setString(2, airport.getAirportName());
            pstmt.setString(3, airport.getCity());
            pstmt.setString(4, airport.getCountry());
            pstmt.setInt(5, airport.getAirportId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error updating airport", e);
        }
        return false;
    }

    public boolean deleteAirport(int airportId) {
        String sql = "DELETE FROM airports WHERE airport_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, airportId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error deleting airport", e);
        }
        return false;
    }

    private Airport extractAirportFromResultSet(ResultSet rs) throws SQLException {
        Airport airport = new Airport();
        airport.setAirportId(rs.getInt("airport_id"));
        airport.setAirportCode(rs.getString("airport_code"));
        airport.setAirportName(rs.getString("airport_name"));
        airport.setCity(rs.getString("city"));
        airport.setCountry(rs.getString("country"));
        airport.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return airport;
    }
}
