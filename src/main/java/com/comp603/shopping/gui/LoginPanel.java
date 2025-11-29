package com.comp603.shopping.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel {

    private MainFrame mainFrame;
    private JTextField userField;
    private JPasswordField passField;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setBackground(UIUtils.COLOR_BACKGROUND);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Welcome to CyberShop");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        titleLabel.setForeground(UIUtils.COLOR_ACCENT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel userLabel = new JLabel("Username:");
        UIUtils.styleLabel(userLabel, 14, false);
        add(userLabel, gbc);

        userField = new JTextField(15);
        UIUtils.styleTextField(userField);
        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel passLabel = new JLabel("Password:");
        UIUtils.styleLabel(passLabel, 14, false);
        add(passLabel, gbc);

        passField = new JPasswordField(15);
        UIUtils.styleTextField(passField);
        gbc.gridx = 1;
        add(passField, gbc);

        JButton loginButton = new JButton("Login");
        UIUtils.styleButton(loginButton, new Color(30, 144, 255)); // Dodger Blue

        JButton registerButton = new JButton("Register");
        UIUtils.styleButton(registerButton, new Color(60, 179, 113)); // MediumSeaGreen

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        registerButton.addActionListener(e -> new RegisterDialog(mainFrame).setVisible(true));

        loginButton.addActionListener((ActionEvent e) -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (mainFrame.getAuthService().login(user, pass)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                mainFrame.onLoginSuccess();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
