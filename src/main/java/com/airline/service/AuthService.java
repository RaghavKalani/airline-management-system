package com.airline.service;

import com.airline.dao.UserDAO;
import com.airline.model.User;
import com.airline.util.Logger;
import com.airline.util.PasswordUtil;
import com.airline.util.ValidationUtil;

public class AuthService {
    private final UserDAO userDAO;
    private User currentUser;

    public AuthService() {
        this.userDAO = new UserDAO();
        this.currentUser = null;
    }

    /**
     * Register a new user
     */
    public boolean register(String username, String password, String fullName, 
                           String email, String phone, User.UserRole role) {
        System.out.println("\n🔍 Starting registration validation...");
        System.out.println("  Username: '" + username + "'");
        System.out.println("  Email: '" + email + "'");
        System.out.println("  Role: " + role);
        
        // Validate inputs
        if (!ValidationUtil.isValidUsername(username)) {
            Logger.warn("Invalid username format: " + username);
            System.out.println("❌ FAILED: Invalid username format");
            System.out.println("   Username must be 3-20 characters (letters, numbers, underscore only)");
            return false;
        }
        System.out.println("✓ Username format valid");

        if (!PasswordUtil.isStrongPassword(password)) {
            Logger.warn("Password is too weak (must be at least 6 characters)");
            System.out.println("❌ FAILED: Password too weak (must be at least 6 characters)");
            return false;
        }
        System.out.println("✓ Password strength valid");

        if (!ValidationUtil.isValidEmail(email)) {
            Logger.warn("Invalid email format: " + email);
            System.out.println("❌ FAILED: Invalid email format");
            return false;
        }
        System.out.println("✓ Email format valid");

        if (!ValidationUtil.isNotEmpty(fullName)) {
            Logger.warn("Full name is required");
            System.out.println("❌ FAILED: Full name is required");
            return false;
        }
        System.out.println("✓ Full name provided");

        // Check if username already exists
        System.out.println("🔎 Checking if username exists...");
        if (userDAO.usernameExists(username)) {
            Logger.warn("Username already exists: " + username);
            System.out.println("❌ FAILED: Username '" + username + "' already exists");
            return false;
        }
        System.out.println("✓ Username available");

        // Check if email already exists
        System.out.println("🔎 Checking if email exists...");
        if (userDAO.emailExists(email)) {
            Logger.warn("Email already exists: " + email);
            System.out.println("❌ FAILED: Email '" + email + "' already exists");
            return false;
        }
        System.out.println("✓ Email available");

        // Create user
        System.out.println("💾 Creating user in database...");
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(PasswordUtil.hashPassword(password));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role);

        boolean created = userDAO.createUser(user);
        if (created) {
            Logger.info("User registered successfully: " + username);
            System.out.println("✅ SUCCESS! User registered: " + username + " (Role: " + role + ")");
        } else {
            System.out.println("❌ FAILED: Database error while creating user");
        }
        return created;
    }

    /**
     * Login user
     */
    public boolean login(String username, String password) {
        User user = userDAO.findByUsername(username);

        if (user == null) {
            Logger.warn("User not found: " + username);
            return false;
        }

        if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            Logger.warn("Invalid password for user: " + username);
            return false;
        }

        this.currentUser = user;
        Logger.info("User logged in: " + username);
        return true;
    }

    /**
     * Logout current user
     */
    public void logout() {
        if (currentUser != null) {
            Logger.info("User logged out: " + currentUser.getUsername());
            this.currentUser = null;
        }
    }

    /**
     * Get current logged-in user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Check if user is logged in
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Check if current user is admin
     */
    public boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == User.UserRole.ADMIN;
    }

    /**
     * Check if current user is customer
     */
    public boolean isCustomer() {
        return currentUser != null && currentUser.getRole() == User.UserRole.CUSTOMER;
    }
}
