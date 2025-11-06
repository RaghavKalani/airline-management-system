package com.airline.dao;

import com.airline.config.DatabaseConfig;
import com.airline.model.Passenger;
import com.airline.util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAO {

    public boolean createPassenger(Passenger passenger) {
        String sql = "INSERT INTO passengers (booking_id, first_name, last_name, age, gender, seat_number) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, passenger.getBookingId());
            pstmt.setString(2, passenger.getFirstName());
            pstmt.setString(3, passenger.getLastName());
            pstmt.setInt(4, passenger.getAge());
            pstmt.setString(5, passenger.getGender().name());
            pstmt.setString(6, passenger.getSeatNumber());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    passenger.setPassengerId(rs.getInt(1));
                }
                Logger.info("Passenger added: " + passenger.getFullName());
                return true;
            }
        } catch (SQLException e) {
            Logger.error("Error creating passenger", e);
        }
        return false;
    }

    public Passenger findById(int passengerId) {
        String sql = "SELECT * FROM passengers WHERE passenger_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, passengerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractPassengerFromResultSet(rs);
            }
        } catch (SQLException e) {
            Logger.error("Error finding passenger", e);
        }
        return null;
    }

    public List<Passenger> findByBookingId(int bookingId) {
        List<Passenger> passengers = new ArrayList<>();
        String sql = "SELECT * FROM passengers WHERE booking_id = ? ORDER BY passenger_id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                passengers.add(extractPassengerFromResultSet(rs));
            }
        } catch (SQLException e) {
            Logger.error("Error finding passengers by booking", e);
        }
        return passengers;
    }

    public boolean updatePassenger(Passenger passenger) {
        String sql = "UPDATE passengers SET first_name = ?, last_name = ?, age = ?, gender = ?, seat_number = ? WHERE passenger_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, passenger.getFirstName());
            pstmt.setString(2, passenger.getLastName());
            pstmt.setInt(3, passenger.getAge());
            pstmt.setString(4, passenger.getGender().name());
            pstmt.setString(5, passenger.getSeatNumber());
            pstmt.setInt(6, passenger.getPassengerId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error updating passenger", e);
        }
        return false;
    }

    public boolean deletePassenger(int passengerId) {
        String sql = "DELETE FROM passengers WHERE passenger_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, passengerId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error deleting passenger", e);
        }
        return false;
    }

    public boolean deleteByBookingId(int bookingId) {
        String sql = "DELETE FROM passengers WHERE booking_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bookingId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error deleting passengers by booking", e);
        }
        return false;
    }

    private Passenger extractPassengerFromResultSet(ResultSet rs) throws SQLException {
        Passenger passenger = new Passenger();
        passenger.setPassengerId(rs.getInt("passenger_id"));
        passenger.setBookingId(rs.getInt("booking_id"));
        passenger.setFirstName(rs.getString("first_name"));
        passenger.setLastName(rs.getString("last_name"));
        passenger.setAge(rs.getInt("age"));
        passenger.setGender(Passenger.Gender.valueOf(rs.getString("gender")));
        passenger.setSeatNumber(rs.getString("seat_number"));
        return passenger;
    }
}
