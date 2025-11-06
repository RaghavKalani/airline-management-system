package com.airline.service;

import com.airline.dao.FlightDAO;
import com.airline.dao.ScheduleDAO;
import com.airline.model.Flight;
import com.airline.model.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public class FlightService {
    private final FlightDAO flightDAO;
    private final ScheduleDAO scheduleDAO;

    public FlightService() {
        this.flightDAO = new FlightDAO();
        this.scheduleDAO = new ScheduleDAO();
    }

    /**
     * Search available flights by origin and destination
     */
    public List<Flight> searchFlights(String origin, String destination) {
        return flightDAO.searchFlights(origin, destination);
    }

    /**
     * Search available schedules for flights
     */
    public List<Schedule> searchSchedules(String origin, String destination, LocalDateTime fromDate) {
        return scheduleDAO.searchSchedules(origin, destination, fromDate);
    }

    /**
     * Get upcoming schedules
     */
    public List<Schedule> getUpcomingSchedules() {
        return scheduleDAO.getUpcomingSchedules();
    }

    /**
     * Get schedule by ID
     */
    public Schedule getScheduleById(int scheduleId) {
        return scheduleDAO.findById(scheduleId);
    }

    /**
     * Get all flights
     */
    public List<Flight> getAllFlights() {
        return flightDAO.findAll();
    }

    /**
     * Get flight by ID
     */
    public Flight getFlightById(int flightId) {
        return flightDAO.findById(flightId);
    }

    /**
     * Get schedules for a specific flight
     */
    public List<Schedule> getSchedulesForFlight(int flightId) {
        return scheduleDAO.findByFlightId(flightId);
    }
}
