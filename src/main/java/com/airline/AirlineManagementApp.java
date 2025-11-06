package com.airline;

import com.airline.config.DatabaseConfig;
import com.airline.service.AuthService;
import com.airline.ui.LoginFrame;
import com.airline.util.Logger;

import javax.swing.*;

public class AirlineManagementApp {
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Logger.warn("Could not set system look and feel");
        }

        // Test database connection
        Logger.info("Starting Airline Management System...");
        
        if (!DatabaseConfig.testConnection()) {
            JOptionPane.showMessageDialog(null,
                    "Failed to connect to database!\n" +
                    "Please ensure MySQL is running and database is set up.\n" +
                    "Check DatabaseConfig.java for connection settings.",
                    "Database Connection Error",
                    JOptionPane.ERROR_MESSAGE);
            Logger.error("Database connection failed. Exiting...");
            System.exit(1);
        }

        Logger.info("Database connected successfully!");

        // Start the application
        SwingUtilities.invokeLater(() -> {
            AuthService authService = new AuthService();
            LoginFrame loginFrame = new LoginFrame(authService);
            loginFrame.setVisible(true);
            Logger.info("Application started successfully!");
        });
    }
}
