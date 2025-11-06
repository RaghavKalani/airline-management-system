package com.airline.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Log info message
     */
    public static void info(String message) {
        System.out.println("[INFO] " + getCurrentTime() + " - " + message);
    }

    /**
     * Log error message
     */
    public static void error(String message) {
        System.err.println("[ERROR] " + getCurrentTime() + " - " + message);
    }

    /**
     * Log error with exception
     */
    public static void error(String message, Exception e) {
        System.err.println("[ERROR] " + getCurrentTime() + " - " + message);
        e.printStackTrace();
    }

    /**
     * Log warning message
     */
    public static void warn(String message) {
        System.out.println("[WARN] " + getCurrentTime() + " - " + message);
    }

    /**
     * Log debug message
     */
    public static void debug(String message) {
        System.out.println("[DEBUG] " + getCurrentTime() + " - " + message);
    }

    private static String getCurrentTime() {
        return LocalDateTime.now().format(formatter);
    }
}
