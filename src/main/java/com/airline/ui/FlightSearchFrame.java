package com.airline.ui;

import com.airline.model.Passenger;
import com.airline.model.Schedule;
import com.airline.service.AuthService;
import com.airline.service.BookingService;
import com.airline.service.FlightService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FlightSearchFrame extends JFrame {
    private final AuthService authService;
    private final BookingService bookingService;
    private final FlightService flightService;

    private JTextField originField;
    private JTextField destinationField;
    private JTable resultsTable;
    private DefaultTableModel tableModel;

    public FlightSearchFrame(AuthService authService, BookingService bookingService) {
        this.authService = authService;
        this.bookingService = bookingService;
        this.flightService = new FlightService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Search and Book Flights");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));

        // Search Panel with dark gradient header
        JPanel searchPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 32, 39), getWidth(), 0, new Color(32, 58, 67));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        searchPanel.setPreferredSize(new Dimension(getWidth(), 100));

        // Title
        JLabel titleLabel = new JLabel("✈️ Search Flights");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        searchPanel.add(titleLabel);

        // Origin field with styling
        JLabel originLabel = new JLabel("From:");
        originLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        originLabel.setForeground(Color.WHITE);
        searchPanel.add(originLabel);
        
        originField = new JTextField(12);
        originField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        originField.setBackground(new Color(45, 45, 45));
        originField.setForeground(new Color(230, 230, 230));
        originField.setCaretColor(new Color(100, 200, 255));
        originField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        originField.setToolTipText("Enter origin city or airport code (e.g., Delhi, DEL)");
        searchPanel.add(originField);

        // Destination field with styling
        JLabel destLabel = new JLabel("To:");
        destLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        destLabel.setForeground(Color.WHITE);
        searchPanel.add(destLabel);
        
        destinationField = new JTextField(12);
        destinationField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        destinationField.setBackground(new Color(45, 45, 45));
        destinationField.setForeground(new Color(230, 230, 230));
        destinationField.setCaretColor(new Color(100, 200, 255));
        destinationField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        destinationField.setToolTipText("Enter destination city or airport code (e.g., Mumbai, BOM)");
        searchPanel.add(destinationField);

        JButton searchButton = new JButton("🔍 SEARCH");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setBackground(new Color(0, 150, 220));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setPreferredSize(new Dimension(120, 35));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(0, 120, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(0, 150, 220));
            }
        });
        searchButton.addActionListener(e -> searchFlights());
        searchPanel.add(searchButton);

        // Results Table with modern styling
        String[] columns = {"Schedule ID", "Flight", "Airline", "Origin", "Destination", "Departure", "Arrival", "Available Seats"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultsTable = new JTable(tableModel);
        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        resultsTable.setRowHeight(35);
        resultsTable.setBackground(new Color(35, 35, 35));
        resultsTable.setForeground(new Color(230, 230, 230));
        resultsTable.setSelectionBackground(new Color(0, 120, 180));
        resultsTable.setSelectionForeground(Color.WHITE);
        resultsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        resultsTable.getTableHeader().setBackground(new Color(45, 45, 45));
        resultsTable.getTableHeader().setForeground(new Color(230, 230, 230));
        resultsTable.setGridColor(new Color(60, 60, 60));
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        scrollPane.getViewport().setBackground(new Color(35, 35, 35));

        // Button Panel with modern styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(new Color(25, 25, 25));
        
        JButton clearButton = new JButton("🗑️ CLEAR");
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        clearButton.setBackground(new Color(60, 60, 60));
        clearButton.setForeground(new Color(200, 200, 200));
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setPreferredSize(new Dimension(100, 35));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(80, 80, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(60, 60, 60));
            }
        });
        clearButton.addActionListener(e -> {
            originField.setText("");
            destinationField.setText("");
            loadUpcomingFlights();
        });
        
        JButton bookButton = new JButton("✓ BOOK SELECTED FLIGHT");
        bookButton.setBackground(new Color(0, 150, 220));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bookButton.setFocusPainted(false);
        bookButton.setBorderPainted(false);
        bookButton.setPreferredSize(new Dimension(220, 40));
        bookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bookButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bookButton.setBackground(new Color(0, 120, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bookButton.setBackground(new Color(0, 150, 220));
            }
        });
        bookButton.addActionListener(e -> bookSelectedFlight());
        
        buttonPanel.add(clearButton);
        buttonPanel.add(bookButton);

        // Add panels
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Load initial data (upcoming flights)
        loadUpcomingFlights();
    }

    private void searchFlights() {
        String origin = originField.getText().trim();
        String destination = destinationField.getText().trim();

        // Fix common misspellings
        origin = fixCityName(origin);
        destination = fixCityName(destination);

        System.out.println("\n🔍 SEARCH REQUEST:");
        System.out.println("  Origin: '" + origin + "'");
        System.out.println("  Destination: '" + destination + "'");

        if (origin.isEmpty() || destination.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both origin and destination");
            return;
        }

        tableModel.setRowCount(0);
        System.out.println("  Searching from: " + LocalDateTime.now());
        List<Schedule> schedules = flightService.searchSchedules(origin, destination, LocalDateTime.now());
        System.out.println("✓ Found " + schedules.size() + " flight(s)");

        if (schedules.isEmpty()) {
            System.out.println("❌ No flights found!");
            System.out.println("💡 Available cities: Delhi, Mumbai, Bangalore, Chennai, Kolkata, Hyderabad, Goa, Ahmedabad, London, Dubai");
            JOptionPane.showMessageDialog(this, 
                "No flights found for the given route.\n\n" +
                "Available cities: Delhi, Mumbai, Bangalore, Chennai, Kolkata,\n" +
                "Hyderabad, Goa, Ahmedabad, London, Dubai\n\n" +
                "Note: City names are case-insensitive and partial matches work.\n" +
                "Common spellings: Delhi (not Dehli), Mumbai (not Bombay), Bangalore (not Bengaluru)",
                "No Flights Found",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM HH:mm");
        for (Schedule schedule : schedules) {
            System.out.println("  - " + schedule.getFlightNumber() + ": " + 
                schedule.getOriginCity() + " → " + schedule.getDestinationCity() + 
                " (" + schedule.getAvailableSeats() + " seats)");
            tableModel.addRow(new Object[]{
                    schedule.getScheduleId(),
                    schedule.getFlightNumber(),
                    schedule.getAirlineName(),
                    schedule.getOriginCity(),
                    schedule.getDestinationCity(),
                    schedule.getDepartureTime().format(formatter),
                    schedule.getArrivalTime().format(formatter),
                    schedule.getAvailableSeats()
            });
        }
    }

    private String fixCityName(String city) {
        if (city == null || city.isEmpty()) return city;
        
        String lower = city.toLowerCase().trim();
        
        // Fix common misspellings
        if (lower.equals("dehli") || lower.equals("dilli")) return "Delhi";
        if (lower.equals("bombay") || lower.contains("bombay")) return "Mumbai";
        if (lower.equals("bengaluru") || lower.contains("bengaluru")) return "Bangalore";
        if (lower.equals("calcutta") || lower.contains("calcutta")) return "Kolkata";
        if (lower.equals("madras") || lower.contains("madras")) return "Chennai";
        
        // Return original if no misspelling detected
        return city;
    }

    private void loadUpcomingFlights() {
        tableModel.setRowCount(0);
        List<Schedule> schedules = flightService.getUpcomingSchedules();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM HH:mm");
        for (Schedule schedule : schedules) {
            tableModel.addRow(new Object[]{
                    schedule.getScheduleId(),
                    schedule.getFlightNumber(),
                    schedule.getAirlineName(),
                    schedule.getOriginCity(),
                    schedule.getDestinationCity(),
                    schedule.getDepartureTime().format(formatter),
                    schedule.getArrivalTime().format(formatter),
                    schedule.getAvailableSeats()
            });
        }
    }

    private void bookSelectedFlight() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a flight to book");
            return;
        }

        int scheduleId = (int) resultsTable.getValueAt(selectedRow, 0);
        int availableSeats = (int) resultsTable.getValueAt(selectedRow, 7);

        if (availableSeats == 0) {
            JOptionPane.showMessageDialog(this, "No seats available on this flight");
            return;
        }

        // Ask for number of passengers
        String passengersStr = JOptionPane.showInputDialog(this, "Number of passengers (1-" + Math.min(availableSeats, 9) + "):");
        if (passengersStr == null || passengersStr.trim().isEmpty()) return;

        int numPassengers;
        try {
            numPassengers = Integer.parseInt(passengersStr.trim());
            if (numPassengers < 1 || numPassengers > availableSeats) {
                JOptionPane.showMessageDialog(this, "Invalid number of passengers");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number");
            return;
        }

        // Collect passenger details
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < numPassengers; i++) {
            PassengerDetailsDialog dialog = new PassengerDetailsDialog(this, i + 1);
            dialog.setVisible(true);

            Passenger passenger = dialog.getPassenger();
            if (passenger == null) {
                return; // User cancelled
            }
            passengers.add(passenger);
        }

        // Calculate amount (simplified)
        BigDecimal amount = new BigDecimal("5000").multiply(new BigDecimal(numPassengers));

        System.out.println("\n========== BOOKING ATTEMPT ==========");
        System.out.println("User ID: " + authService.getCurrentUser().getUserId());
        System.out.println("Schedule ID: " + scheduleId);
        System.out.println("Passengers: " + numPassengers);
        System.out.println("Amount: ₹" + amount);
        System.out.println("=====================================\n");

        // Create booking
        var booking = bookingService.createBooking(
                authService.getCurrentUser().getUserId(),
                scheduleId,
                passengers,
                amount
        );

        if (booking != null) {
            JOptionPane.showMessageDialog(this,
                    "✅ Booking successful!\n\n" +
                            "PNR: " + booking.getPnr() + "\n" +
                            "Passengers: " + numPassengers + "\n" +
                            "Amount: ₹" + amount + "\n\n" +
                            "Check 'My Bookings' tab to view details.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "❌ Booking failed!\n\n" +
                            "Please check the console output for detailed error.\n" +
                            "Common issues:\n" +
                            "• Not enough seats available\n" +
                            "• Database connection issue\n" +
                            "• Invalid passenger details",
                    "Booking Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Inner class for passenger details dialog
    private static class PassengerDetailsDialog extends JDialog {
        private Passenger passenger;
        private JTextField firstNameField;
        private JTextField lastNameField;
        private JTextField ageField;
        private JComboBox<String> genderComboBox;

        public PassengerDetailsDialog(Frame parent, int passengerNumber) {
            super(parent, "Passenger " + passengerNumber + " Details", true);
            initializeUI();
        }

        private void initializeUI() {
            setSize(450, 350);
            setLocationRelativeTo(getParent());

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(new Color(30, 30, 30));
            panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(8, 8, 8, 8);

            // First Name
            gbc.gridx = 0;
            gbc.gridy = 0;
            JLabel fnLabel = new JLabel("First Name:");
            fnLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            fnLabel.setForeground(new Color(200, 200, 200));
            panel.add(fnLabel, gbc);
            gbc.gridx = 1;
            firstNameField = new JTextField(20);
            firstNameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            firstNameField.setBackground(new Color(45, 45, 45));
            firstNameField.setForeground(new Color(230, 230, 230));
            firstNameField.setCaretColor(new Color(100, 200, 255));
            firstNameField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(60, 60, 60)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
            panel.add(firstNameField, gbc);

            // Last Name
            gbc.gridx = 0;
            gbc.gridy = 1;
            JLabel lnLabel = new JLabel("Last Name:");
            lnLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lnLabel.setForeground(new Color(200, 200, 200));
            panel.add(lnLabel, gbc);
            gbc.gridx = 1;
            lastNameField = new JTextField(20);
            lastNameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lastNameField.setBackground(new Color(45, 45, 45));
            lastNameField.setForeground(new Color(230, 230, 230));
            lastNameField.setCaretColor(new Color(100, 200, 255));
            lastNameField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(60, 60, 60)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
            panel.add(lastNameField, gbc);

            // Age
            gbc.gridx = 0;
            gbc.gridy = 2;
            JLabel ageLabel = new JLabel("Age:");
            ageLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            ageLabel.setForeground(new Color(200, 200, 200));
            panel.add(ageLabel, gbc);
            gbc.gridx = 1;
            ageField = new JTextField(20);
            ageField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            ageField.setBackground(new Color(45, 45, 45));
            ageField.setForeground(new Color(230, 230, 230));
            ageField.setCaretColor(new Color(100, 200, 255));
            ageField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(60, 60, 60)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
            panel.add(ageField, gbc);

            // Gender
            gbc.gridx = 0;
            gbc.gridy = 3;
            JLabel genderLabel = new JLabel("Gender:");
            genderLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            genderLabel.setForeground(new Color(200, 200, 200));
            panel.add(genderLabel, gbc);
            gbc.gridx = 1;
            genderComboBox = new JComboBox<>(new String[]{"MALE", "FEMALE", "OTHER"});
            genderComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            genderComboBox.setBackground(new Color(45, 45, 45));
            genderComboBox.setForeground(new Color(230, 230, 230));
            panel.add(genderComboBox, gbc);

            // Buttons with modern styling
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
            buttonPanel.setOpaque(false);
            
            JButton okButton = new JButton("✓ CONFIRM");
            okButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
            okButton.setBackground(new Color(0, 150, 220));
            okButton.setForeground(Color.WHITE);
            okButton.setFocusPainted(false);
            okButton.setBorderPainted(false);
            okButton.setPreferredSize(new Dimension(120, 35));
            okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            okButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    okButton.setBackground(new Color(0, 120, 180));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    okButton.setBackground(new Color(0, 150, 220));
                }
            });
            okButton.addActionListener(e -> handleOK());
            
            JButton cancelButton = new JButton("✕ CANCEL");
            cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
            cancelButton.setBackground(new Color(60, 60, 60));
            cancelButton.setForeground(new Color(200, 200, 200));
            cancelButton.setFocusPainted(false);
            cancelButton.setBorderPainted(false);
            cancelButton.setPreferredSize(new Dimension(120, 35));
            cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    cancelButton.setBackground(new Color(80, 80, 80));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    cancelButton.setBackground(new Color(60, 60, 60));
                }
            });
            cancelButton.addActionListener(e -> dispose());

            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            panel.add(buttonPanel, gbc);

            add(panel);
        }

        private void handleOK() {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String ageStr = ageField.getText().trim();
            String genderStr = (String) genderComboBox.getSelectedItem();

            if (firstName.isEmpty() || lastName.isEmpty() || ageStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageStr);
                if (age < 0 || age > 120) {
                    JOptionPane.showMessageDialog(this, "Invalid age");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Age must be a number");
                return;
            }

            passenger = new Passenger();
            passenger.setFirstName(firstName);
            passenger.setLastName(lastName);
            passenger.setAge(age);
            passenger.setGender(Passenger.Gender.valueOf(genderStr));

            dispose();
        }

        public Passenger getPassenger() {
            return passenger;
        }
    }
}
