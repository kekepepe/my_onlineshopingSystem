package com.comp603.shopping.gui;

import com.comp603.shopping.dao.UserDAO;
import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {

    private JTextField userField;
    private JPasswordField passField;
    private JPasswordField confirmPassField;
    private UserDAO userDAO;

    public RegisterDialog(JFrame parent) {
        super(parent, "Register New Account", true);
        this.userDAO = new UserDAO();

        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(UIUtils.COLOR_BACKGROUND);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel userLabel = new JLabel("Username:");
        UIUtils.styleLabel(userLabel, 14, false);
        add(userLabel, gbc);

        userField = new JTextField(15);
        UIUtils.styleTextField(userField);
        gbc.gridx = 1;
        add(userField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passLabel = new JLabel("Password:");
        UIUtils.styleLabel(passLabel, 14, false);
        add(passLabel, gbc);

        passField = new JPasswordField(15);
        UIUtils.styleTextField(passField);
        gbc.gridx = 1;
        add(passField, gbc);

        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel confirmLabel = new JLabel("Confirm Password:");
        UIUtils.styleLabel(confirmLabel, 14, false);
        add(confirmLabel, gbc);

        confirmPassField = new JPasswordField(15);
        UIUtils.styleTextField(confirmPassField);
        gbc.gridx = 1;
        add(confirmPassField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton registerButton = new JButton("Register");
        UIUtils.styleButton(registerButton, new Color(30, 144, 255)); // Dodger Blue

        JButton cancelButton = new JButton("Cancel");
        UIUtils.styleButton(cancelButton, Color.GRAY);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // Actions
        cancelButton.addActionListener(e -> dispose());

        registerButton.addActionListener(e -> register());
    }

    private void register() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());
        String confirmPass = new String(confirmPassField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userDAO.getUserByUsername(username) != null) {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userDAO.registerUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Registration Successful! Please login.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
