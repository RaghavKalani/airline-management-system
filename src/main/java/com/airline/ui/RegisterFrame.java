package com.airline.ui;

import com.airline.model.User;
import com.airline.service.AuthService;
import com.airline.util.Logger;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private final AuthService authService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JComboBox<String> roleComboBox;

    public RegisterFrame(AuthService authService) {
        this.authService = authService;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Register New User");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel with dark gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 32, 39), 0, getHeight(), new Color(32, 58, 67));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(230, 230, 230));
        titlePanel.add(titleLabel);

        // Form Panel - Dark theme
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(30, 30, 30));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username
        addFormField(formPanel, gbc, 0, "Username:", usernameField = new JTextField(20));

        // Password
        addFormField(formPanel, gbc, 1, "Password:", passwordField = new JPasswordField(20));

        // Confirm Password
        addFormField(formPanel, gbc, 2, "Confirm Password:", confirmPasswordField = new JPasswordField(20));

        // Full Name
        addFormField(formPanel, gbc, 3, "Full Name:", fullNameField = new JTextField(20));

        // Email
        addFormField(formPanel, gbc, 4, "Email:", emailField = new JTextField(20));

        // Phone
        addFormField(formPanel, gbc, 5, "Phone:", phoneField = new JTextField(20));

        // Role
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        roleLabel.setForeground(new Color(200, 200, 200));
        formPanel.add(roleLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        roleComboBox = new JComboBox<>(new String[]{"CUSTOMER", "ADMIN"});
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        roleComboBox.setBackground(new Color(45, 45, 45));
        roleComboBox.setForeground(new Color(230, 230, 230));
        formPanel.add(roleComboBox, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(0, 150, 220));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(new Dimension(120, 35));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(0, 120, 180));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(0, 150, 220));
            }
        });
        registerButton.addActionListener(e -> handleRegistration());

        JButton backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(new Color(30, 30, 30));
        backButton.setForeground(new Color(0, 150, 220));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 150, 220), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        backButton.setPreferredSize(new Dimension(140, 35));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(0, 150, 220));
                backButton.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(30, 30, 30));
                backButton.setForeground(new Color(0, 150, 220));
            }
        });
        backButton.addActionListener(e -> backToLogin());

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        // Add panels
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(new Color(200, 200, 200));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(new Color(45, 45, 45));
        field.setForeground(new Color(230, 230, 230));
        field.setCaretColor(new Color(100, 200, 255));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        panel.add(field, gbc);
    }

    private void handleRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String roleStr = (String) roleComboBox.getSelectedItem();

        System.out.println("\n=== REGISTRATION ATTEMPT ===");
        System.out.println("Username: " + username);
        System.out.println("Role: " + roleStr);
        System.out.println("Email: " + email);

        // Validate
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all required fields",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check for specific validation errors
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 6 characters long",
                    "Weak Password",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        User.UserRole role = User.UserRole.valueOf(roleStr);

        boolean success = authService.register(username, password, fullName, email, phone, role);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Registration successful! Please login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            Logger.info("New user registered: " + username);
            backToLogin();
        } else {
            // Show more detailed error message
            String errorMsg = "Registration failed!\n\n";
            errorMsg += "Possible reasons:\n";
            errorMsg += "• Username '" + username + "' might already exist\n";
            errorMsg += "• Email '" + email + "' might already exist\n";
            errorMsg += "• Username must be 3-20 characters (letters, numbers, underscore only)\n";
            errorMsg += "• Email format must be valid (example@email.com)\n";
            errorMsg += "• Password must be at least 6 characters\n\n";
            errorMsg += "Check the console output for specific errors.";
            
            JOptionPane.showMessageDialog(this,
                    errorMsg,
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void backToLogin() {
        new LoginFrame(authService).setVisible(true);
        dispose();
    }
}
