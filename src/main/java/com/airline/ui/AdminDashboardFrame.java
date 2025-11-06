package com.airline.ui;

import com.airline.service.*;
import com.airline.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class AdminDashboardFrame extends JFrame {
    private final AuthService authService;
    private final AdminService adminService;
    private final ReportService reportService;

    public AdminDashboardFrame(AuthService authService) {
        this.authService = authService;
        this.adminService = new AdminService();
        this.reportService = new ReportService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Dashboard - " + authService.getCurrentUser().getFullName());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(25, 25, 25));

        // Top Panel with dark gradient
        JPanel topPanel = new JPanel(new BorderLayout()) {
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
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel welcomeLabel = new JLabel("Admin Dashboard - " + authService.getCurrentUser().getFullName());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(230, 230, 230));
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(255, 87, 34));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(230, 74, 25));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(255, 87, 34));
            }
        });
        logoutButton.addActionListener(e -> logout());
        topPanel.add(logoutButton, BorderLayout.EAST);

        // Tabbed Pane with dark theme
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));
        tabbedPane.setBackground(new Color(25, 25, 25));
        tabbedPane.setForeground(Color.BLACK);
        tabbedPane.setOpaque(true);

        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Airlines", createAirlinesPanel());
        tabbedPane.addTab("Airports", createAirportsPanel());
        tabbedPane.addTab("Flights", createFlightsPanel());
        tabbedPane.addTab("Bookings", createBookingsPanel());

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(25, 25, 25));

        Map<String, Object> stats = reportService.getBookingStatistics();

        addStatCard(panel, "Total Bookings", stats.get("totalBookings").toString(), new Color(0, 150, 220));
        addStatCard(panel, "Confirmed", stats.get("confirmedBookings").toString(), new Color(0, 150, 136));
        addStatCard(panel, "Cancelled", stats.get("cancelledBookings").toString(), new Color(233, 30, 99));
        addStatCard(panel, "Completed", stats.get("completedBookings").toString(), new Color(156, 39, 176));
        addStatCard(panel, "Total Revenue", "₹" + String.format("%.2f", stats.get("totalRevenue")), new Color(255, 152, 0));

        return panel;
    }

    private void addStatCard(JPanel panel, String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        valueLabel.setForeground(Color.WHITE);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        panel.add(card);
    }

    private JPanel createAirlinesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(25, 25, 25));

        String[] columns = {"ID", "Code", "Name", "Country"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setBackground(new Color(35, 35, 35));
        table.setForeground(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(0, 120, 180));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(45, 45, 45));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setGridColor(new Color(60, 60, 60));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(35, 35, 35));

        loadAirlines(model);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 150, 220));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0, 120, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0, 150, 220));
            }
        });
        refreshButton.addActionListener(e -> loadAirlines(model));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(refreshButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadAirlines(DefaultTableModel model) {
        model.setRowCount(0);
        List<Airline> airlines = adminService.getAllAirlines();
        for (Airline airline : airlines) {
            model.addRow(new Object[]{
                    airline.getAirlineId(),
                    airline.getAirlineCode(),
                    airline.getAirlineName(),
                    airline.getCountry()
            });
        }
    }

    private JPanel createAirportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(25, 25, 25));

        String[] columns = {"ID", "Code", "Name", "City", "Country"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setBackground(new Color(35, 35, 35));
        table.setForeground(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(0, 120, 180));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(45, 45, 45));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setGridColor(new Color(60, 60, 60));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(35, 35, 35));

        loadAirports(model);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 150, 220));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0, 120, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0, 150, 220));
            }
        });
        refreshButton.addActionListener(e -> loadAirports(model));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(refreshButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadAirports(DefaultTableModel model) {
        model.setRowCount(0);
        List<Airport> airports = adminService.getAllAirports();
        for (Airport airport : airports) {
            model.addRow(new Object[]{
                    airport.getAirportId(),
                    airport.getAirportCode(),
                    airport.getAirportName(),
                    airport.getCity(),
                    airport.getCountry()
            });
        }
    }

    private JPanel createFlightsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(25, 25, 25));

        String[] columns = {"ID", "Flight #", "Airline", "Route", "Seats", "Price", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setBackground(new Color(35, 35, 35));
        table.setForeground(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(0, 120, 180));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(45, 45, 45));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setGridColor(new Color(60, 60, 60));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(35, 35, 35));

        loadFlights(model);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 150, 220));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0, 120, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0, 150, 220));
            }
        });
        refreshButton.addActionListener(e -> loadFlights(model));
        
        JButton addFlightButton = new JButton("✈ Add Flight");
        addFlightButton.setBackground(new Color(76, 175, 80));
        addFlightButton.setForeground(Color.WHITE);
        addFlightButton.setFocusPainted(false);
        addFlightButton.setBorderPainted(false);
        addFlightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addFlightButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addFlightButton.setBackground(new Color(56, 142, 60));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addFlightButton.setBackground(new Color(76, 175, 80));
            }
        });
        addFlightButton.addActionListener(e -> showAddFlightDialog(model));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(refreshButton);
        buttonPanel.add(addFlightButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadFlights(DefaultTableModel model) {
        model.setRowCount(0);
        List<Flight> flights = adminService.getAllFlights();
        for (Flight flight : flights) {
            model.addRow(new Object[]{
                    flight.getFlightId(),
                    flight.getFlightNumber(),
                    flight.getAirlineName(),
                    flight.getOriginCity() + " → " + flight.getDestinationCity(),
                    flight.getTotalSeats(),
                    "₹" + flight.getBasePrice(),
                    flight.getStatus()
            });
        }
    }

    private JPanel createBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(25, 25, 25));

        String[] columns = {"PNR", "Customer", "Flight", "Route", "Passengers", "Amount", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setBackground(new Color(35, 35, 35));
        table.setForeground(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(0, 120, 180));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(45, 45, 45));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setGridColor(new Color(60, 60, 60));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(35, 35, 35));

        loadBookings(model);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(0, 150, 220));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0, 120, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0, 150, 220));
            }
        });
        refreshButton.addActionListener(e -> loadBookings(model));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(refreshButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadBookings(DefaultTableModel model) {
        model.setRowCount(0);
        List<Booking> bookings = adminService.getAllBookings();
        for (Booking booking : bookings) {
            model.addRow(new Object[]{
                    booking.getPnr(),
                    booking.getCustomerName(),
                    booking.getFlightNumber(),
                    booking.getOriginCity() + " → " + booking.getDestinationCity(),
                    booking.getTotalPassengers(),
                    "₹" + booking.getTotalAmount(),
                    booking.getStatus()
            });
        }
    }

    private void showAddFlightDialog(DefaultTableModel model) {
        JDialog dialog = new JDialog(this, "Add New Flight", true);
        dialog.setSize(550, 600);
        dialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        // Title
        JLabel titleLabel = new JLabel("✈ Add New Flight");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        
        // Flight Number
        addFormField(formPanel, gbc, 0, "Flight Number:", createStyledTextField());
        JTextField flightNumberField = (JTextField) ((JPanel)formPanel.getComponent(1)).getComponent(0);
        
        // Airline Selection
        List<Airline> airlines = adminService.getAllAirlines();
        JComboBox<Airline> airlineCombo = new JComboBox<>(airlines.toArray(new Airline[0]));
        styleComboBox(airlineCombo);
        addFormField(formPanel, gbc, 1, "Airline:", airlineCombo);
        
        // Origin Airport
        List<Airport> airports = adminService.getAllAirports();
        JComboBox<Airport> originCombo = new JComboBox<>(airports.toArray(new Airport[0]));
        styleComboBox(originCombo);
        addFormField(formPanel, gbc, 2, "Origin Airport:", originCombo);
        
        // Destination Airport
        JComboBox<Airport> destCombo = new JComboBox<>(airports.toArray(new Airport[0]));
        styleComboBox(destCombo);
        addFormField(formPanel, gbc, 3, "Destination Airport:", destCombo);
        
        // Total Seats
        addFormField(formPanel, gbc, 4, "Total Seats:", createStyledTextField());
        JTextField seatsField = (JTextField) ((JPanel)formPanel.getComponent(9)).getComponent(0);
        
        // Base Price
        addFormField(formPanel, gbc, 5, "Base Price (₹):", createStyledTextField());
        JTextField priceField = (JTextField) ((JPanel)formPanel.getComponent(11)).getComponent(0);
        
        // Status
        JComboBox<Flight.FlightStatus> statusCombo = new JComboBox<>(Flight.FlightStatus.values());
        statusCombo.setSelectedItem(Flight.FlightStatus.ACTIVE);
        styleComboBox(statusCombo);
        addFormField(formPanel, gbc, 6, "Status:", statusCombo);
        
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        
        JButton saveButton = new JButton("💾 Save Flight");
        styleButton(saveButton, new Color(76, 175, 80), new Color(56, 142, 60));
        saveButton.addActionListener(e -> {
            try {
                // Validate
                if (flightNumberField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Flight number is required");
                    return;
                }
                
                // Create flight object
                Flight flight = new Flight();
                flight.setFlightNumber(flightNumberField.getText().trim().toUpperCase());
                flight.setAirlineId(((Airline) airlineCombo.getSelectedItem()).getAirlineId());
                flight.setOriginAirportId(((Airport) originCombo.getSelectedItem()).getAirportId());
                flight.setDestinationAirportId(((Airport) destCombo.getSelectedItem()).getAirportId());
                flight.setTotalSeats(Integer.parseInt(seatsField.getText().trim()));
                flight.setBasePrice(new java.math.BigDecimal(priceField.getText().trim()));
                flight.setStatus((Flight.FlightStatus) statusCombo.getSelectedItem());
                
                // Validate origin != destination
                if (flight.getOriginAirportId() == flight.getDestinationAirportId()) {
                    JOptionPane.showMessageDialog(dialog, "Origin and destination must be different");
                    return;
                }
                
                // Save to database
                boolean success = adminService.createFlight(flight);
                
                if (success) {
                    JOptionPane.showMessageDialog(dialog, "✅ Flight added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadFlights(model);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to add flight", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for seats and price", "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        
        JButton cancelButton = new JButton("✕ Cancel");
        styleButton(cancelButton, new Color(96, 125, 139), new Color(69, 90, 100));
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        field.setCaretColor(Color.BLACK);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }
    
    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
        combo.setForeground(Color.BLACK);
    }
    
    private void styleButton(JButton button, Color bgColor, Color hoverColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 38));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, Component field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(Color.BLACK);
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setOpaque(false);
        fieldPanel.add(field, BorderLayout.CENTER);
        panel.add(fieldPanel, gbc);
    }

    private void logout() {
        authService.logout();
        new LoginFrame(authService).setVisible(true);
        dispose();
    }
}
