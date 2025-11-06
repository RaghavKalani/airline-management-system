package com.airline.dao;

import com.airline.config.DatabaseConfig;
import com.airline.model.Booking;
import com.airline.util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (pnr, user_id, schedule_id, total_passengers, total_amount, status, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, booking.getPnr());
            pstmt.setInt(2, booking.getUserId());
            pstmt.setInt(3, booking.getScheduleId());
            pstmt.setInt(4, booking.getTotalPassengers());
            pstmt.setBigDecimal(5, booking.getTotalAmount());
            pstmt.setString(6, booking.getStatus().name());
            pstmt.setString(7, booking.getPaymentStatus().name());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    booking.setBookingId(rs.getInt(1));
                }
                Logger.info("Booking created: PNR " + booking.getPnr());
                return true;
            }
        } catch (SQLException e) {
            Logger.error("Error creating booking", e);
        }
        return false;
    }

    public Booking findById(int bookingId) {
        String sql = "SELECT b.*, u.full_name as customer_name, f.flight_number, " +
                     "a1.city as origin_city, a2.city as destination_city, " +
                     "s.departure_time, s.arrival_time " +
                     "FROM bookings b " +
                     "JOIN users u ON b.user_id = u.user_id " +
                     "JOIN schedules s ON b.schedule_id = s.schedule_id " +
                     "JOIN flights f ON s.flight_id = f.flight_id " +
                     "JOIN airports a1 ON f.origin_airport_id = a1.airport_id " +
                     "JOIN airports a2 ON f.destination_airport_id = a2.airport_id " +
                     "WHERE b.booking_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractBookingFromResultSet(rs);
            }
        } catch (SQLException e) {
            Logger.error("Error finding booking", e);
        }
        return null;
    }

    public Booking findByPNR(String pnr) {
        String sql = "SELECT b.*, u.full_name as customer_name, f.flight_number, " +
                     "a1.city as origin_city, a2.city as destination_city, " +
                     "s.departure_time, s.arrival_time " +
                     "FROM bookings b " +
                     "JOIN users u ON b.user_id = u.user_id " +
                     "JOIN schedules s ON b.schedule_id = s.schedule_id " +
                     "JOIN flights f ON s.flight_id = f.flight_id " +
                     "JOIN airports a1 ON f.origin_airport_id = a1.airport_id " +
                     "JOIN airports a2 ON f.destination_airport_id = a2.airport_id " +
                     "WHERE b.pnr = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pnr);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractBookingFromResultSet(rs);
            }
        } catch (SQLException e) {
            Logger.error("Error finding booking by PNR", e);
        }
        return null;
    }

    public List<Booking> findByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, u.full_name as customer_name, f.flight_number, " +
                     "a1.city as origin_city, a2.city as destination_city, " +
                     "s.departure_time, s.arrival_time " +
                     "FROM bookings b " +
                     "JOIN users u ON b.user_id = u.user_id " +
                     "JOIN schedules s ON b.schedule_id = s.schedule_id " +
                     "JOIN flights f ON s.flight_id = f.flight_id " +
                     "JOIN airports a1 ON f.origin_airport_id = a1.airport_id " +
                     "JOIN airports a2 ON f.destination_airport_id = a2.airport_id " +
                     "WHERE b.user_id = ? ORDER BY b.booking_date DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                bookings.add(extractBookingFromResultSet(rs));
            }
        } catch (SQLException e) {
            Logger.error("Error finding bookings by user", e);
        }
        return bookings;
    }

    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, u.full_name as customer_name, f.flight_number, " +
                     "a1.city as origin_city, a2.city as destination_city, " +
                     "s.departure_time, s.arrival_time " +
                     "FROM bookings b " +
                     "JOIN users u ON b.user_id = u.user_id " +
                     "JOIN schedules s ON b.schedule_id = s.schedule_id " +
                     "JOIN flights f ON s.flight_id = f.flight_id " +
                     "JOIN airports a1 ON f.origin_airport_id = a1.airport_id " +
                     "JOIN airports a2 ON f.destination_airport_id = a2.airport_id " +
                     "ORDER BY b.booking_date DESC LIMIT 100";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                bookings.add(extractBookingFromResultSet(rs));
            }
        } catch (SQLException e) {
            Logger.error("Error fetching all bookings", e);
        }
        return bookings;
    }

    public boolean updateBookingStatus(int bookingId, Booking.BookingStatus status) {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status.name());
            pstmt.setInt(2, bookingId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error updating booking status", e);
        }
        return false;
    }

    public boolean updatePaymentStatus(int bookingId, Booking.PaymentStatus paymentStatus) {
        String sql = "UPDATE bookings SET payment_status = ? WHERE booking_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, paymentStatus.name());
            pstmt.setInt(2, bookingId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error updating payment status", e);
        }
        return false;
    }

    public boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bookingId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error deleting booking", e);
        }
        return false;
    }

    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setPnr(rs.getString("pnr"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setScheduleId(rs.getInt("schedule_id"));
        booking.setBookingDate(rs.getTimestamp("booking_date").toLocalDateTime());
        booking.setTotalPassengers(rs.getInt("total_passengers"));
        booking.setTotalAmount(rs.getBigDecimal("total_amount"));
        booking.setStatus(Booking.BookingStatus.valueOf(rs.getString("status")));
        booking.setPaymentStatus(Booking.PaymentStatus.valueOf(rs.getString("payment_status")));
        
        // Joined data
        booking.setCustomerName(rs.getString("customer_name"));
        booking.setFlightNumber(rs.getString("flight_number"));
        booking.setOriginCity(rs.getString("origin_city"));
        booking.setDestinationCity(rs.getString("destination_city"));
        booking.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        booking.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
        
        return booking;
    }
}
