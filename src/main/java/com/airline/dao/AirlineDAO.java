package com.airline.dao;

import com.airline.config.DatabaseConfig;
import com.airline.model.Airline;
import com.airline.util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AirlineDAO {

    public boolean createAirline(Airline airline) {
        String sql = "INSERT INTO airlines (airline_code, airline_name, country) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, airline.getAirlineCode());
            pstmt.setString(2, airline.getAirlineName());
            pstmt.setString(3, airline.getCountry());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    airline.setAirlineId(rs.getInt(1));
                }
                Logger.info("Airline created: " + airline.getAirlineName());
                return true;
            }
        } catch (SQLException e) {
            Logger.error("Error creating airline", e);
        }
        return false;
    }

    public Airline findById(int airlineId) {
        String sql = "SELECT * FROM airlines WHERE airline_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, airlineId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractAirlineFromResultSet(rs);
            }
        } catch (SQLException e) {
            Logger.error("Error finding airline", e);
        }
        return null;
    }

    public List<Airline> findAll() {
        List<Airline> airlines = new ArrayList<>();
        String sql = "SELECT * FROM airlines ORDER BY airline_name";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                airlines.add(extractAirlineFromResultSet(rs));
            }
        } catch (SQLException e) {
            Logger.error("Error fetching airlines", e);
        }
        return airlines;
    }

    public boolean updateAirline(Airline airline) {
        String sql = "UPDATE airlines SET airline_code = ?, airline_name = ?, country = ? WHERE airline_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, airline.getAirlineCode());
            pstmt.setString(2, airline.getAirlineName());
            pstmt.setString(3, airline.getCountry());
            pstmt.setInt(4, airline.getAirlineId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error updating airline", e);
        }
        return false;
    }

    public boolean deleteAirline(int airlineId) {
        String sql = "DELETE FROM airlines WHERE airline_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, airlineId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error deleting airline", e);
        }
        return false;
    }

    private Airline extractAirlineFromResultSet(ResultSet rs) throws SQLException {
        Airline airline = new Airline();
        airline.setAirlineId(rs.getInt("airline_id"));
        airline.setAirlineCode(rs.getString("airline_code"));
        airline.setAirlineName(rs.getString("airline_name"));
        airline.setCountry(rs.getString("country"));
        airline.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return airline;
    }
}
