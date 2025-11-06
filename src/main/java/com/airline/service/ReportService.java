package com.airline.service;

import com.airline.dao.BookingDAO;
import com.airline.model.Booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
    private final BookingDAO bookingDAO;

    public ReportService() {
        this.bookingDAO = new BookingDAO();
    }

    /**
     * Get booking statistics
     */
    public Map<String, Object> getBookingStatistics() {
        Map<String, Object> stats = new HashMap<>();
        List<Booking> allBookings = bookingDAO.findAll();

        int totalBookings = allBookings.size();
        int confirmedBookings = 0;
        int cancelledBookings = 0;
        int completedBookings = 0;
        double totalRevenue = 0;

        for (Booking booking : allBookings) {
            switch (booking.getStatus()) {
                case CONFIRMED:
                    confirmedBookings++;
                    break;
                case CANCELLED:
                    cancelledBookings++;
                    break;
                case COMPLETED:
                    completedBookings++;
                    break;
            }

            if (booking.getPaymentStatus() == Booking.PaymentStatus.PAID) {
                totalRevenue += booking.getTotalAmount().doubleValue();
            }
        }

        stats.put("totalBookings", totalBookings);
        stats.put("confirmedBookings", confirmedBookings);
        stats.put("cancelledBookings", cancelledBookings);
        stats.put("completedBookings", completedBookings);
        stats.put("totalRevenue", totalRevenue);

        return stats;
    }

    /**
     * Get all bookings (for reports)
     */
    public List<Booking> getAllBookings() {
        return bookingDAO.findAll();
    }
}
