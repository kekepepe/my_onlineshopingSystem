package com.comp603.shopping.models;

/**
 * Represents a registered user in the system.
 * Encapsulates user details and account balance.
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private String email;
    private double balance;

    private String role;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Parameterized constructor for creating a user object.
     * 
     * @param userId   Unique ID of the user
     * @param username User's login name
     * @param password User's password
     * @param email    User's email address
     * @param role     User's role (ADMIN or CUSTOMER)
     * @param balance  Current wallet balance
     */
    public User(int userId, String username, String password, String email, String role, double balance) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
