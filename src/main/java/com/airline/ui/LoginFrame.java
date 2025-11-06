package com.airline.ui;

import com.airline.service.AuthService;
import com.airline.util.Logger;

import javax.swing.*;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final AuthService authService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public LoginFrame(AuthService authService) {
        this.authService = authService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Airline Management System");
        setSize(500, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Set modern Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main Panel with animated gradient background and floating particles
        JPanel mainPanel = new JPanel() {
            private float animationOffset = 0;
            private Timer animationTimer;
            private java.util.Random random = new java.util.Random();
            private java.util.List<Particle> particles = new java.util.ArrayList<>();
            
            class Particle {
                float x, y, speed, size;
                Color color;
                Particle() {
                    x = random.nextFloat() * getWidth();
                    y = random.nextFloat() * getHeight();
                    speed = 0.2f + random.nextFloat() * 0.5f;
                    size = 2 + random.nextFloat() * 3;
                    int alpha = 30 + random.nextInt(70);
                    color = new Color(100, 200, 255, alpha);
                }
                void update() {
                    y -= speed;
                    if (y < -10) {
                        y = getHeight() + 10;
                        x = random.nextFloat() * getWidth();
                    }
                }
            }
            
            {
                // Initialize particles
                for (int i = 0; i < 30; i++) {
                    particles.add(new Particle());
                }
                
                // Animation timer
                animationTimer = new Timer(50, e -> {
                    animationOffset += 0.5f;
                    for (Particle p : particles) {
                        p.update();
                    }
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
                
                // Animated gradient background
                float offset = (float)Math.sin(animationOffset * 0.02) * 20;
                GradientPaint gp = new GradientPaint(
                    0, offset, new Color(15, 32, 39), 
                    getWidth(), getHeight() + offset, new Color(44, 82, 130)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw floating particles
                for (Particle p : particles) {
                    g2d.setColor(p.color);
                    g2d.fillOval((int)p.x, (int)p.y, (int)p.size, (int)p.size);
                }
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        // Card Panel with glassmorphism effect and shadow
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Shadow effect
                g2d.setColor(new Color(0, 0, 0, 80));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
                
                // Glassmorphism background with subtle transparency
                g2d.setColor(new Color(30, 30, 30, 245));
                g2d.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 20, 20);
                
                // Glossy highlight on top
                GradientPaint highlight = new GradientPaint(
                    0, 0, new Color(255, 255, 255, 10),
                    0, getHeight() / 3, new Color(255, 255, 255, 0)
                );
                g2d.setPaint(highlight);
                g2d.fillRoundRect(0, 0, getWidth() - 10, getHeight() / 3, 20, 20);
                
                // Border glow
                g2d.setColor(new Color(100, 200, 255, 60));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 20, 20);
            }
        };
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(40, 45, 40, 45));
        
        cardPanel.setPreferredSize(new Dimension(400, 600));
        cardPanel.setMaximumSize(new Dimension(400, 600));
        
        // Animated airplane icon at the top
        JLabel iconLabel = new JLabel("✈") {
            private float bounce = 0;
            private Timer bounceTimer;
            {
                bounceTimer = new Timer(50, e -> {
                    bounce += 0.1f;
                    int offset = (int)(Math.sin(bounce) * 5);
                    setBorder(BorderFactory.createEmptyBorder(offset, 0, -offset, 0));
                });
                bounceTimer.start();
            }
        };
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 56));
        iconLabel.setForeground(new Color(100, 200, 255));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(iconLabel);
        
        // Add spacing below icon
        cardPanel.add(Box.createVerticalStrut(10));
        
        // Logo/Icon Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Airline Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(100, 200, 255));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("✨ Sign in to continue ✨");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(180, 180, 180));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        logoPanel.add(titleLabel);
        logoPanel.add(Box.createVerticalStrut(4));
        logoPanel.add(subtitleLabel);
        
        cardPanel.add(logoPanel);
        cardPanel.add(Box.createVerticalStrut(25));

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username Field with icon
        JLabel usernameLabel = new JLabel("👤 Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        usernameLabel.setForeground(new Color(200, 200, 200));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        usernameField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBackground(new Color(45, 45, 45));
        usernameField.setForeground(new Color(230, 230, 230));
        usernameField.setCaretColor(new Color(100, 200, 255));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
                BorderFactory.createEmptyBorder(11, 15, 11, 15)
        ));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setOpaque(false);
        
        // Add focus effect
        usernameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 200, 255), 2, true),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernameField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
                    BorderFactory.createEmptyBorder(11, 15, 11, 15)
                ));
            }
        });

        formPanel.add(usernameLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(12));

        // Password Field with icon
        JLabel passwordLabel = new JLabel("🔒 Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passwordLabel.setForeground(new Color(200, 200, 200));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        passwordField = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBackground(new Color(45, 45, 45));
        passwordField.setForeground(new Color(230, 230, 230));
        passwordField.setCaretColor(new Color(100, 200, 255));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
                BorderFactory.createEmptyBorder(11, 15, 11, 15)
        ));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setOpaque(false);
        
        // Add focus effect
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 200, 255), 2, true),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
                    BorderFactory.createEmptyBorder(11, 15, 11, 15)
                ));
            }
        });
        
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(12));

        // Role Selection with icon
        JLabel roleLabel = new JLabel("🎭 Login As");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        roleLabel.setForeground(new Color(200, 200, 200));
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        roleComboBox = new JComboBox<>(new String[]{"👤 Customer", "⚡ Admin"}) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        roleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        roleComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        roleComboBox.setOpaque(false);
        
        // Custom renderer with proper contrast - WHITE background with BLACK text
        roleComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label.setBorder(BorderFactory.createEmptyBorder(11, 15, 11, 15));
                
                // For the button (index -1) and dropdown items
                if (index == -1) {
                    // This is the button/selected item display - BLACK TEXT on WHITE background
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                    label.setOpaque(true);
                } else {
                    // This is dropdown list items - BLACK TEXT on WHITE
                    label.setBackground(isSelected ? new Color(0, 150, 220) : Color.WHITE);
                    label.setForeground(Color.BLACK);
                    label.setOpaque(true);
                }
                return label;
            }
        });
        
        // Style the combo box with white background and black text
        roleComboBox.setBackground(Color.WHITE);
        roleComboBox.setForeground(Color.BLACK);
        roleComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80), 1, true),
                BorderFactory.createEmptyBorder(0, 12, 0, 5)
        ));
        
        // Custom UI for dark themed dropdown with rounded corners
        roleComboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▼") {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2d.setColor(getModel().isPressed() ? new Color(50, 50, 50) : 
                                    getModel().isRollover() ? new Color(65, 65, 65) : new Color(60, 60, 60));
                        g2d.fillRect(0, 0, getWidth(), getHeight());
                        super.paintComponent(g);
                    }
                };
                button.setFont(new Font("Segoe UI", Font.PLAIN, 9));
                button.setForeground(new Color(200, 200, 200));
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
                button.setPreferredSize(new Dimension(30, 30));
                return button;
            }
            
            @Override
            protected ComboPopup createPopup() {
                return new javax.swing.plaf.basic.BasicComboPopup(comboBox) {
                    @Override
                    protected void configurePopup() {
                        super.configurePopup();
                        setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
                            BorderFactory.createEmptyBorder(2, 2, 2, 2)
                        ));
                        setBackground(Color.WHITE);
                    }
                    
                    @Override
                    protected JList<Object> createList() {
                        JList<Object> list = super.createList();
                        list.setBackground(Color.WHITE);
                        list.setForeground(Color.BLACK);
                        list.setSelectionBackground(new Color(0, 150, 220));
                        list.setSelectionForeground(Color.BLACK);
                        return list;
                    }
                    
                    @Override
                    protected JScrollPane createScroller() {
                        JScrollPane scroller = super.createScroller();
                        scroller.setBorder(BorderFactory.createEmptyBorder());
                        scroller.getViewport().setBackground(Color.WHITE);
                        return scroller;
                    }
                };
            }
        });
        
        formPanel.add(roleLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(roleComboBox);
        formPanel.add(Box.createVerticalStrut(20));

        cardPanel.add(formPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login Button - Modern, full width with gradient
        JButton loginButton = new JButton("🚀 Sign In") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    GradientPaint gp = new GradientPaint(0, 0, new Color(0, 100, 150), 0, getHeight(), new Color(0, 130, 190));
                    g2d.setPaint(gp);
                } else if (getModel().isRollover()) {
                    GradientPaint gp = new GradientPaint(0, 0, new Color(0, 130, 190), 0, getHeight(), new Color(0, 170, 240));
                    g2d.setPaint(gp);
                } else {
                    GradientPaint gp = new GradientPaint(0, 0, new Color(0, 150, 220), 0, getHeight(), new Color(0, 180, 255));
                    g2d.setPaint(gp);
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Add shine effect
                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 40), 0, getHeight() / 2, new Color(255, 255, 255, 0));
                g2d.setPaint(shine);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 12, 12);
                
                super.paintComponent(g);
            }
        };
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createEmptyBorder(11, 0, 11, 0));
        loginButton.addActionListener(e -> handleLogin());

        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createVerticalStrut(8));

        // Register Button - Outline style with hover animation
        JButton registerButton = new JButton("✨ Create New Account") {
            private boolean hovered = false;
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (hovered) {
                    g2d.setColor(new Color(0, 150, 220));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                } else {
                    g2d.setColor(new Color(40, 40, 40));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                }
                
                // Border
                g2d.setColor(new Color(100, 200, 255));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 12, 12);
                
                super.paintComponent(g);
            }
        };
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setForeground(new Color(100, 200, 255));
        registerButton.setFocusPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        registerButton.setPreferredSize(new Dimension(310, 45));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setBorder(BorderFactory.createEmptyBorder(11, 0, 11, 0));
        
        // Hover effect
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setForeground(Color.WHITE);
                registerButton.putClientProperty("hovered", true);
                registerButton.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setForeground(new Color(100, 200, 255));
                registerButton.putClientProperty("hovered", false);
                registerButton.repaint();
            }
        });
        registerButton.addActionListener(e -> openRegistrationForm());
        
        buttonPanel.add(registerButton);
        
        cardPanel.add(buttonPanel);

        // Add card to main panel
        mainPanel.add(cardPanel);

        add(mainPanel);

        // Enter key to login
        passwordField.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String selectedRole = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = authService.login(username, password);

        if (success) {
            boolean isAdmin = authService.isAdmin();
            boolean wantsAdmin = selectedRole.contains("Admin");
            
            // Admins can access both portals, but regular customers can only access customer portal
            if (!isAdmin && wantsAdmin) {
                JOptionPane.showMessageDialog(this,
                        "❌ Access Denied!\n\n" +
                        "You don't have Admin privileges.\n" +
                        "Please select 'Customer' to login.",
                        "Access Denied",
                        JOptionPane.ERROR_MESSAGE);
                authService.logout();
                return;
            }

            Logger.info("Login successful for user: " + username + " (Accessing: " + selectedRole + " Portal)");
            dispose();

            // Open dashboard based on selection
            if (wantsAdmin) {
                new AdminDashboardFrame(authService).setVisible(true);
            } else {
                new CustomerDashboardFrame(authService).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private void openRegistrationForm() {
        new RegisterFrame(authService).setVisible(true);
        dispose();
    }
}
