package com.airline.util;

import javax.swing.JOptionPane;

public class ErrorHandler {
    
    private static String lastError = "";
    
    public static void setLastError(String error) {
        lastError = error;
        System.err.println("ERROR: " + error);
    }
    
    public static String getLastError() {
        return lastError;
    }
    
    public static void showError(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showSuccess(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
