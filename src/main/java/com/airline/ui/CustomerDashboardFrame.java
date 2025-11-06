package com.airline.ui;

import com.airline.service.*;
import com.airline.model.Booking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerDashboardFrame extends JFrame {
    private final AuthService authService;
    private final BookingService bookingService;

    public CustomerDashboardFrame(AuthService authService) {
        this.authService = authService;
        this.bookingService = new BookingService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Customer Dashboard - " + authService.getCurrentUser().getFullName());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));

        // Top Panel - Modern animated Header with gradient
        JPanel topPanel = new JPanel(new BorderLayout()) {
            private float animationOffset = 0;
            private Timer animationTimer;
            {
                animationTimer = new Timer(100, e -> {
                    animationOffset += 0.5f;
                    repaint();
                });
                animationTimer.start();
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Animated gradient
                float offset = (float)Math.sin(animationOffset * 0.05) * 30;
                GradientPaint gp = new GradientPaint(offset, 0, new Color(0, 150, 220), getWidth() + offset, 0, new Color(44, 82, 130));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add subtle pattern overlay
                g2d.setColor(new Color(255, 255, 255, 5));
                for (int i = 0; i < getWidth(); i += 20) {
                    g2d.drawLine(i, 0, i, getHeight());
                }
            }
        };
        topPanel.setPreferredSize(new Dimension(getWidth(), 90));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        topPanel.setLayout(null); // Use absolute positioning for full control

        // Animated User icon with pulse effect
        JLabel userIcon = new JLabel("👤") {
            private float pulse = 0;
            private Timer pulseTimer;
            {
                pulseTimer = new Timer(100, e -> {
                    pulse += 0.15f;
                    float scale = 1.0f + (float)Math.sin(pulse) * 0.05f;
                    setFont(getFont().deriveFont(32f * scale));
                });
                pulseTimer.start();
            }
        };
        userIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        userIcon.setBounds(25, 22, 50, 40);
        topPanel.add(userIcon);
        
        // Welcome back label with shadow
        JLabel welcomeLabel = new JLabel("✨ Welcome back,");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(new Color(240, 240, 240));
        welcomeLabel.setBounds(85, 22, 300, 18);
        topPanel.add(welcomeLabel);
        
        // Name label with plenty of width and glow effect
        JLabel nameLabel = new JLabel(authService.getCurrentUser().getFullName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(85, 42, 700, 28);
        topPanel.add(nameLabel);

        // Logout button with gradient and animation
        JButton logoutButton = new JButton("🚪 LOGOUT") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(new Color(200, 50, 0));
                } else if (getModel().isRollover()) {
                    GradientPaint gp = new GradientPaint(0, 0, new Color(255, 100, 50), 0, getHeight(), new Color(255, 70, 20));
                    g2d.setPaint(gp);
                } else {
                    GradientPaint gp = new GradientPaint(0, 0, new Color(255, 87, 34), 0, getHeight(), new Color(230, 74, 25));
                    g2d.setPaint(gp);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Add shine
                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 50), 0, getHeight() / 2, new Color(255, 255, 255, 0));
                g2d.setPaint(shine);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 10, 10);
                
                super.paintComponent(g);
            }
        };
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBounds(850, 25, 120, 40);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> logout());
        topPanel.add(logoutButton);

        // Center Panel - Tabbed Pane with dark theme
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));
        tabbedPane.setBackground(new Color(25, 25, 25));
        tabbedPane.setForeground(Color.BLACK);
        tabbedPane.setOpaque(true);

        // Tab 1: Search Flights
        JPanel searchPanel = createSearchFlightsPanel();
        tabbedPane.addTab("Search Flights", searchPanel);

        // Tab 2: My Bookings - Create once but will refresh when focused
        JPanel bookingsPanel = createMyBookingsPanel();
        tabbedPane.addTab("My Bookings", bookingsPanel);
        
        // Auto-refresh bookings when tab is selected
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex == 1) { // My Bookings tab
                System.out.println("🔄 My Bookings tab selected - auto-refreshing...");
                refreshBookingsPanel(bookingsPanel);
            }
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createSearchFlightsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(25, 25, 25));

        // Info section with icon and text
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        infoPanel.setOpaque(false);
        
        JLabel airplaneIcon = new JLabel("✈️");
        airplaneIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        infoPanel.add(airplaneIcon);
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Find Your Perfect Flight");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(230, 230, 230));
        textPanel.add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("Search and book flights to destinations worldwide");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(160, 160, 160));
        textPanel.add(subtitleLabel);
        
        infoPanel.add(textPanel);
        
        // Search button with ripple effect and glow
        JButton searchButton = new JButton("🔍 SEARCH FLIGHTS") {
            private java.util.List<Ripple> ripples = new java.util.ArrayList<>();
            private float glowPhase = 0;
            private Timer glowTimer;
            
            class Ripple {
                int x, y;
                float radius;
                float alpha;
                Ripple(int x, int y) { this.x = x; this.y = y; radius = 0; alpha = 1.0f; }
            }
            
            {
                glowTimer = new Timer(30, e -> {
                    glowPhase += 0.05f;
                    ripples.removeIf(r -> r.alpha <= 0);
                    ripples.forEach(r -> {
                        r.radius += 5;
                        r.alpha -= 0.05f;
                    });
                    repaint();
                });
                glowTimer.start();
                
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        ripples.add(new Ripple(evt.getX(), evt.getY()));
                    }
                });
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Animated glow
                float intensity = (float)(Math.sin(glowPhase) * 0.3 + 0.7);
                g2d.setColor(new Color(0, 150, 220, (int)(60 * intensity)));
                g2d.fillRoundRect(-2, -2, getWidth() + 4, getHeight() + 4, 12, 12);
                
                super.paintComponent(g);
                
                // Ripple effect
                for (Ripple ripple : ripples) {
                    g2d.setColor(new Color(255, 255, 255, (int)(ripple.alpha * 100)));
                    g2d.fillOval((int)(ripple.x - ripple.radius), (int)(ripple.y - ripple.radius), 
                               (int)(ripple.radius * 2), (int)(ripple.radius * 2));
                }
            }
        };
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        searchButton.setBackground(new Color(0, 150, 220));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setPreferredSize(new Dimension(250, 50));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(0, 120, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(0, 150, 220));
            }
        });
        searchButton.addActionListener(e -> openFlightSearch());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(searchButton);

        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMyBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(25, 25, 25));

        // Header with icon
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        headerPanel.setOpaque(false);
        
        JLabel ticketIcon = new JLabel("🎫");
        ticketIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        headerPanel.add(ticketIcon);
        
        JLabel titleLabel = new JLabel("My Bookings");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(230, 230, 230));
        headerPanel.add(titleLabel);

        // Table with modern styling
        String[] columns = {"PNR", "Flight", "Route", "Departure", "Status", "Amount"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setBackground(new Color(35, 35, 35));
        table.setForeground(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(0, 120, 180));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(45, 45, 45));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setGridColor(new Color(60, 60, 60));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        scrollPane.getViewport().setBackground(new Color(35, 35, 35));

        // Load bookings
        loadBookings(model);

        // Button panel with modern styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setOpaque(false);
        
        // Refresh button
        JButton refreshButton = new JButton("🔄 REFRESH");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshButton.setBackground(new Color(0, 150, 220));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setPreferredSize(new Dimension(120, 35));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0, 120, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0, 150, 220));
            }
        });
        refreshButton.addActionListener(e -> {
            model.setRowCount(0);
            loadBookings(model);
            JOptionPane.showMessageDialog(this, "Bookings refreshed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        
        // Cancel button
        JButton cancelButton = new JButton("❌ CANCEL BOOKING");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cancelButton.setBackground(new Color(233, 30, 99));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setPreferredSize(new Dimension(160, 35));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelButton.setBackground(new Color(194, 24, 91));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancelButton.setBackground(new Color(233, 30, 99));
            }
        });
        cancelButton.addActionListener(e -> cancelSelectedBooking(table, model));
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(cancelButton);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadBookings(DefaultTableModel model) {
        model.setRowCount(0);
        int userId = authService.getCurrentUser().getUserId();
        System.out.println("📋 Loading bookings for user ID: " + userId);
        
        List<Booking> bookings = bookingService.getUserBookings(userId);
        System.out.println("✓ Found " + bookings.size() + " booking(s)");

        for (Booking booking : bookings) {
            model.addRow(new Object[]{
                    booking.getPnr(),
                    booking.getFlightNumber(),
                    booking.getOriginCity() + " → " + booking.getDestinationCity(),
                    booking.getDepartureTime(),
                    booking.getStatus(),
                    "₹" + booking.getTotalAmount()
            });
            System.out.println("  - " + booking.getPnr() + " | " + booking.getFlightNumber() + " | " + booking.getStatus());
        }
    }

    private void refreshBookingsPanel(JPanel bookingsPanel) {
        // Find the table in the panel and refresh it
        for (Component comp : bookingsPanel.getComponents()) {
            if (comp instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) comp;
                JTable table = (JTable) scrollPane.getViewport().getView();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                loadBookings(model);
                break;
            }
        }
    }

    private void cancelSelectedBooking(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel");
            return;
        }

        String pnr = (String) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel booking " + pnr + "?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Booking booking = bookingService.getBookingByPNR(pnr);
            if (booking != null && bookingService.cancelBooking(booking.getBookingId())) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully");
                loadBookings(model);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking");
            }
        }
    }

    private void openFlightSearch() {
        new FlightSearchFrame(authService, bookingService).setVisible(true);
    }

    private void logout() {
        authService.logout();
        new LoginFrame(authService).setVisible(true);
        dispose();
    }
}
