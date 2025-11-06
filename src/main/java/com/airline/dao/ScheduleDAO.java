package com.airline.dao;

import com.airline.config.DatabaseConfig;
import com.airline.model.Schedule;
import com.airline.util.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    public boolean createSchedule(Schedule schedule) {
        String sql = "INSERT INTO schedules (flight_id, departure_time, arrival_time, available_seats, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, schedule.getFlightId());
            pstmt.setTimestamp(2, Timestamp.valueOf(schedule.getDepartureTime()));
            pstmt.setTimestamp(3, Timestamp.valueOf(schedule.getArrivalTime()));
            pstmt.setInt(4, schedule.getAvailableSeats());
            pstmt.setString(5, schedule.getStatus().name());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    schedule.setScheduleId(rs.getInt(1));
                }
                Logger.info("Schedule created for flight ID: " + schedule.getFlightId());
                return true;
            }
        } catch (SQLException e) {
            Logger.error("Error creating schedule", e);
        }
        return false;
    }

    public Schedule findById(int scheduleId) {
        String sql = "SELECT s.*, f.flight_number, f.total_seats, al.airline_name, " +
                     "a1.city as origin_city, a1.airport_code as origin_code, " +
                     "a2.city as destination_city, a2.airport_code as destination_code " +
                     "FROM schedules s " +
                     "JOIN flights f ON s.flight_id = f.flight_id " +
                     "JOIN airlines al ON f.airline_id = al.airline_id " +
                     "JOIN airports a1 ON f.origin_airport_id = a1.airport_id " +
                     "JOIN airports a2 ON f.destination_airport_id = a2.airport_id " +
                     "WHERE s.schedule_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, scheduleId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractScheduleFromResultSet(rs);
            }
        } catch (SQLException e) {
            Logger.error("Error finding schedule", e);
        }
        return null;
    }

    public List<Schedule> findByFlightId(int flightId) {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT s.*, f.flight_number, f.total_seats, al.airline_name, " +
                     "a1.city as origin_city, a1.airport_code as origin_code, " +
                     "a2.city as destination_city, a2.airport_code as destination_code " +
                     "FROM schedules s " +
                     "JOIN flights f ON s.flight_id = f.flight_id " +
                     "JOIN airlines al ON f.airline_id = al.airline_id " +
                     "JOIN airports a1 ON f.origin_airport_id = a1.airport_id " +
                     "JOIN airports a2 ON f.destination_airport_id = a2.airport_id " +
                     "WHERE s.flight_id = ? ORDER BY s.departure_time";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, flightId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                schedules.add(extractScheduleFromResultSet(rs));
            }
        } catch (SQLException e) {
            Logger.error("Error finding schedules by flight", e);
        }
        return schedules;
    }

    public List<Schedule> searchSchedules(String origin, String destination, LocalDateTime fromDate) {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT s.*, f.flight_number, f.total_seats, al.airline_name, " +
                     "a1.city as origin_city, a1.airport_code as origin_code, " +
                     "a2.city as destination_city, a2.airport_code as destination_code " +
                     "FROM schedules s " +
                     "JOIN flights f ON s.flight_id = f.flight_id " +
                     "JOIN airlines al ON f.airline_id = al.airline_id " +
                     "JOIN airports a1 ON f.origin_airport_id = a1.airport_id " +
                     "JOIN airports a2 ON f.destination_airport_id = a2.airport_id " +
                     "WHERE LOWER(a1.city) LIKE LOWER(?) AND LOWER(a2.city) LIKE LOWER(?) " +
                     "AND s.departure_time >= ? " +
                     "AND s.status = 'SCHEDULED' AND s.available_seats > 0 " +
                     "ORDER BY s.departure_time";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            System.out.println("🔎 Database query:");
            System.out.println("  Origin pattern: '%" + origin + "%'");
            System.out.println("  Destination pattern: '%" + destination + "%'");
            System.out.println("  From date: " + fromDate);
            
            pstmt.setString(1, "%" + origin + "%");
            pstmt.setString(2, "%" + destination + "%");
            pstmt.setTimestamp(3, Timestamp.valueOf(fromDate));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                schedules.add(extractScheduleFromResultSet(rs));
            }
            System.out.println("  Query returned: " + schedules.size() + " result(s)");
        } catch (SQLException e) {
            Logger.error("Error searching schedules", e);
            System.err.println("❌ SQL Error: " + e.getMessage());
        }
        return schedules;
    }

    public List<Schedule> getUpcomingSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT s.*, f.flight_number, f.total_seats, al.airline_name, " +
                     "a1.city as origin_city, a1.airport_code as origin_code, " +
                     "a2.city as destination_city, a2.airport_code as destination_code " +
                     "FROM schedules s " +
                     "JOIN flights f ON s.flight_id = f.flight_id " +
                     "JOIN airlines al ON f.airline_id = al.airline_id " +
                     "JOIN airports a1 ON f.origin_airport_id = a1.airport_id " +
                     "JOIN airports a2 ON f.destination_airport_id = a2.airport_id " +
                     "WHERE s.departure_time >= NOW() AND s.status = 'SCHEDULED' " +
                     "ORDER BY s.departure_time LIMIT 50";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                schedules.add(extractScheduleFromResultSet(rs));
            }
        } catch (SQLException e) {
            Logger.error("Error getting upcoming schedules", e);
        }
        return schedules;
    }

    public boolean updateSchedule(Schedule schedule) {
        String sql = "UPDATE schedules SET departure_time = ?, arrival_time = ?, available_seats = ?, status = ? WHERE schedule_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setTimestamp(1, Timestamp.valueOf(schedule.getDepartureTime()));
            pstmt.setTimestamp(2, Timestamp.valueOf(schedule.getArrivalTime()));
            pstmt.setInt(3, schedule.getAvailableSeats());
            pstmt.setString(4, schedule.getStatus().name());
            pstmt.setInt(5, schedule.getScheduleId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error updating schedule", e);
        }
        return false;
    }

    public boolean updateAvailableSeats(int scheduleId, int newAvailableSeats) {
        String sql = "UPDATE schedules SET available_seats = ? WHERE schedule_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, newAvailableSeats);
            pstmt.setInt(2, scheduleId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error updating available seats", e);
        }
        return false;
    }

    public boolean deleteSchedule(int scheduleId) {
        String sql = "DELETE FROM schedules WHERE schedule_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, scheduleId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.error("Error deleting schedule", e);
        }
        return false;
    }

    private Schedule extractScheduleFromResultSet(ResultSet rs) throws SQLException {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(rs.getInt("schedule_id"));
        schedule.setFlightId(rs.getInt("flight_id"));
        schedule.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        schedule.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
        schedule.setAvailableSeats(rs.getInt("available_seats"));
        schedule.setStatus(Schedule.ScheduleStatus.valueOf(rs.getString("status")));
        schedule.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        
        // Joined data
        schedule.setFlightNumber(rs.getString("flight_number"));
        schedule.setTotalSeats(rs.getInt("total_seats"));
        schedule.setAirlineName(rs.getString("airline_name"));
        schedule.setOriginCity(rs.getString("origin_city"));
        schedule.setDestinationCity(rs.getString("destination_city"));
        schedule.setOriginCode(rs.getString("origin_code"));
        schedule.setDestinationCode(rs.getString("destination_code"));
        
        return schedule;
    }
}
