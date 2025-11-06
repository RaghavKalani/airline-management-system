package com.airline.service;

import com.airline.dao.BookingDAO;
import com.airline.dao.PassengerDAO;
import com.airline.dao.ScheduleDAO;
import com.airline.model.Booking;
import com.airline.model.Passenger;
import com.airline.model.Schedule;
import com.airline.util.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class BookingService {
    private final BookingDAO bookingDAO;
    private final PassengerDAO passengerDAO;
    private final ScheduleDAO scheduleDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
        this.passengerDAO = new PassengerDAO();
        this.scheduleDAO = new ScheduleDAO();
    }

    /**
     * Create a new booking with passengers (simplified - no transaction for now)
     */
    public Booking createBooking(int userId, int scheduleId, List<Passenger> passengers, BigDecimal totalAmount) {
        try {
            System.out.println("✓ Starting booking for user: " + userId + ", schedule: " + scheduleId);

            // Check schedule availability
            Schedule schedule = scheduleDAO.findById(scheduleId);
            if (schedule == null) {
                Logger.warn("Schedule not found: ID " + scheduleId);
                System.out.println("❌ Schedule not found: " + scheduleId);
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Schedule not found!\nSchedule ID: " + scheduleId, 
                    "Booking Failed", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return null;
            }

            System.out.println("✓ Schedule found: " + schedule.getFlightNumber() + " - Available seats: " + schedule.getAvailableSeats());

            if (schedule.getAvailableSeats() < passengers.size()) {
                Logger.warn("Not enough seats available. Need: " + passengers.size() + ", Available: " + schedule.getAvailableSeats());
                System.out.println("❌ Not enough seats. Need: " + passengers.size() + ", Available: " + schedule.getAvailableSeats());
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Not enough seats available!\nRequired: " + passengers.size() + "\nAvailable: " + schedule.getAvailableSeats(), 
                    "Booking Failed", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Create booking
            Booking booking = new Booking();
            String pnr = generatePNR();
            booking.setPnr(pnr);
            booking.setUserId(userId);
            booking.setScheduleId(scheduleId);
            booking.setTotalPassengers(passengers.size());
            booking.setTotalAmount(totalAmount);
            booking.setStatus(Booking.BookingStatus.CONFIRMED);
            booking.setPaymentStatus(Booking.PaymentStatus.PAID);

            System.out.println("✓ Creating booking with PNR: " + pnr);
            boolean bookingCreated = bookingDAO.createBooking(booking);
            if (!bookingCreated) {
                Logger.error("Failed to create booking in database");
                System.out.println("❌ Failed to create booking in database");
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Failed to create booking in database!", 
                    "Booking Failed", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return null;
            }
            System.out.println("✓ Booking created with ID: " + booking.getBookingId());

            // Add passengers
            System.out.println("✓ Adding " + passengers.size() + " passengers...");
            for (int i = 0; i < passengers.size(); i++) {
                Passenger passenger = passengers.get(i);
                passenger.setBookingId(booking.getBookingId());
                boolean passengerAdded = passengerDAO.createPassenger(passenger);
                if (!passengerAdded) {
                    Logger.error("Failed to add passenger: " + passenger.getFirstName());
                    System.out.println("❌ Failed to add passenger " + (i+1) + ": " + passenger.getFirstName());
                    javax.swing.JOptionPane.showMessageDialog(null, 
                        "Failed to add passenger: " + passenger.getFirstName(), 
                        "Booking Failed", 
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                System.out.println("✓ Added passenger " + (i+1) + ": " + passenger.getFirstName() + " " + passenger.getLastName());
            }

            // Update available seats
            int newAvailableSeats = schedule.getAvailableSeats() - passengers.size();
            System.out.println("✓ Updating seats: " + schedule.getAvailableSeats() + " -> " + newAvailableSeats);
            boolean seatsUpdated = scheduleDAO.updateAvailableSeats(scheduleId, newAvailableSeats);
            if (!seatsUpdated) {
                Logger.error("Failed to update available seats");
                System.out.println("❌ Failed to update available seats");
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Failed to update available seats!", 
                    "Booking Failed", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return null;
            }

            Logger.info("Booking created successfully: PNR " + booking.getPnr());
            System.out.println("✅ BOOKING SUCCESS! PNR: " + pnr + ", Amount: ₹" + totalAmount);
            return booking;

        } catch (Exception e) {
            Logger.error("Booking failed", e);
            String errorMsg = "❌ Error: " + e.getMessage();
            System.out.println(errorMsg);
            e.printStackTrace();
            // Show error dialog
            javax.swing.JOptionPane.showMessageDialog(null, 
                "Booking Error:\n" + e.getMessage() + "\n\nType: " + e.getClass().getSimpleName(), 
                "Booking Failed", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Get bookings for a user
     */
    public List<Booking> getUserBookings(int userId) {
        return bookingDAO.findByUserId(userId);
    }

    /**
     * Get booking by PNR
     */
    public Booking getBookingByPNR(String pnr) {
        return bookingDAO.findByPNR(pnr);
    }

    /**
     * Get booking by ID
     */
    public Booking getBookingById(int bookingId) {
        return bookingDAO.findById(bookingId);
    }

    /**
     * Get passengers for a booking
     */
    public List<Passenger> getPassengers(int bookingId) {
        return passengerDAO.findByBookingId(bookingId);
    }

    /**
     * Cancel booking (simplified)
     */
    public boolean cancelBooking(int bookingId) {
        try {
            System.out.println("✓ Starting cancellation for booking ID: " + bookingId);

            Booking booking = bookingDAO.findById(bookingId);
            if (booking == null) {
                Logger.warn("Booking not found: ID " + bookingId);
                System.out.println("❌ Booking not found: " + bookingId);
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Booking not found!\nBooking ID: " + bookingId, 
                    "Cancel Failed", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }

            System.out.println("✓ Booking found: PNR " + booking.getPnr() + ", Status: " + booking.getStatus());

            if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
                Logger.warn("Booking already cancelled");
                System.out.println("❌ Booking already cancelled");
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "This booking is already cancelled!\nPNR: " + booking.getPnr(), 
                    "Already Cancelled", 
                    javax.swing.JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Check if departure is in the future
            if (booking.getDepartureTime().isBefore(LocalDateTime.now())) {
                Logger.warn("Cannot cancel past bookings");
                System.out.println("❌ Cannot cancel past bookings");
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Cannot cancel past flights!\nDeparture was: " + booking.getDepartureTime(), 
                    "Cancel Failed", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Update booking status
            System.out.println("✓ Updating booking status to CANCELLED...");
            boolean statusUpdated = bookingDAO.updateBookingStatus(bookingId, Booking.BookingStatus.CANCELLED);
            if (!statusUpdated) {
                System.out.println("❌ Failed to update booking status");
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Failed to update booking status in database!", 
                    "Cancel Failed", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Restore seats
            System.out.println("✓ Restoring " + booking.getTotalPassengers() + " seats...");
            Schedule schedule = scheduleDAO.findById(booking.getScheduleId());
            if (schedule != null) {
                int newAvailableSeats = schedule.getAvailableSeats() + booking.getTotalPassengers();
                boolean seatsRestored = scheduleDAO.updateAvailableSeats(booking.getScheduleId(), newAvailableSeats);
                if (seatsRestored) {
                    System.out.println("✓ Seats restored: " + schedule.getAvailableSeats() + " -> " + newAvailableSeats);
                }
            }

            Logger.info("Booking cancelled: PNR " + booking.getPnr());
            System.out.println("✅ CANCELLATION SUCCESS! PNR: " + booking.getPnr());
            return true;

        } catch (Exception e) {
            Logger.error("Cancel booking failed", e);
            System.out.println("❌ Cancel booking failed: " + e.getMessage());
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, 
                "Cancellation Error:\n" + e.getMessage(), 
                "Cancel Failed", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Generate unique PNR
     */
    private String generatePNR() {
        Random random = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder pnr = new StringBuilder();
        
        for (int i = 0; i < 6; i++) {
            pnr.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        // Check if PNR already exists (rare case)
        if (bookingDAO.findByPNR(pnr.toString()) != null) {
            return generatePNR(); // Regenerate
        }
        
        return pnr.toString();
    }

    /**
     * Calculate booking amount
     */
    public BigDecimal calculateAmount(Schedule schedule, int passengers) {
        if (schedule == null) return BigDecimal.ZERO;
        
        // Get base price from flight (need to enhance this with actual pricing logic)
        // For now, assume a fixed price per passenger
        BigDecimal pricePerPassenger = new BigDecimal("5000.00"); // Default price
        return pricePerPassenger.multiply(new BigDecimal(passengers));
    }
}
