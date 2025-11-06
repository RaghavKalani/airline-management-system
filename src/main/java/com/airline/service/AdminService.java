package com.airline.service;

import com.airline.dao.*;
import com.airline.model.*;
import com.airline.util.Logger;

import java.util.List;

public class AdminService {
    private final AirlineDAO airlineDAO;
    private final AirportDAO airportDAO;
    private final FlightDAO flightDAO;
    private final ScheduleDAO scheduleDAO;
    private final BookingDAO bookingDAO;

    public AdminService() {
        this.airlineDAO = new AirlineDAO();
        this.airportDAO = new AirportDAO();
        this.flightDAO = new FlightDAO();
        this.scheduleDAO = new ScheduleDAO();
        this.bookingDAO = new BookingDAO();
    }

    // ========== Airline Management ==========
    
    public boolean createAirline(Airline airline) {
        return airlineDAO.createAirline(airline);
    }

    public List<Airline> getAllAirlines() {
        return airlineDAO.findAll();
    }

    public boolean updateAirline(Airline airline) {
        return airlineDAO.updateAirline(airline);
    }

    public boolean deleteAirline(int airlineId) {
        return airlineDAO.deleteAirline(airlineId);
    }

    // ========== Airport Management ==========
    
    public boolean createAirport(Airport airport) {
        return airportDAO.createAirport(airport);
    }

    public List<Airport> getAllAirports() {
        return airportDAO.findAll();
    }

    public List<Airport> searchAirportsByCity(String city) {
        return airportDAO.searchByCity(city);
    }

    public boolean updateAirport(Airport airport) {
        return airportDAO.updateAirport(airport);
    }

    public boolean deleteAirport(int airportId) {
        return airportDAO.deleteAirport(airportId);
    }

    // ========== Flight Management ==========
    
    public boolean createFlight(Flight flight) {
        return flightDAO.createFlight(flight);
    }

    public List<Flight> getAllFlights() {
        return flightDAO.findAll();
    }

    public Flight getFlightById(int flightId) {
        return flightDAO.findById(flightId);
    }

    public boolean updateFlight(Flight flight) {
        return flightDAO.updateFlight(flight);
    }

    public boolean deleteFlight(int flightId) {
        return flightDAO.deleteFlight(flightId);
    }

    // ========== Schedule Management ==========
    
    public boolean createSchedule(Schedule schedule) {
        return scheduleDAO.createSchedule(schedule);
    }

    public List<Schedule> getSchedulesByFlight(int flightId) {
        return scheduleDAO.findByFlightId(flightId);
    }

    public List<Schedule> getUpcomingSchedules() {
        return scheduleDAO.getUpcomingSchedules();
    }

    public boolean updateSchedule(Schedule schedule) {
        return scheduleDAO.updateSchedule(schedule);
    }

    public boolean deleteSchedule(int scheduleId) {
        return scheduleDAO.deleteSchedule(scheduleId);
    }

    // ========== Booking Management ==========
    
    public List<Booking> getAllBookings() {
        return bookingDAO.findAll();
    }

    public Booking getBookingByPNR(String pnr) {
        return bookingDAO.findByPNR(pnr);
    }

    public boolean cancelBooking(int bookingId) {
        Booking booking = bookingDAO.findById(bookingId);
        if (booking == null) {
            Logger.warn("Booking not found: ID " + bookingId);
            return false;
        }

        // Update booking status
        boolean updated = bookingDAO.updateBookingStatus(bookingId, Booking.BookingStatus.CANCELLED);
        
        if (updated) {
            // Restore available seats
            Schedule schedule = scheduleDAO.findById(booking.getScheduleId());
            if (schedule != null) {
                int newAvailableSeats = schedule.getAvailableSeats() + booking.getTotalPassengers();
                scheduleDAO.updateAvailableSeats(booking.getScheduleId(), newAvailableSeats);
            }
            Logger.info("Booking cancelled: PNR " + booking.getPnr());
        }
        
        return updated;
    }
}
