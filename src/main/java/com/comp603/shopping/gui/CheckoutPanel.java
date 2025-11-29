package com.comp603.shopping.gui;

import com.comp603.shopping.dao.OrderDAO;
import com.comp603.shopping.dao.ProductDAO;
import com.comp603.shopping.models.Order;
import com.comp603.shopping.models.OrderItem;
import com.comp603.shopping.models.Product;
import com.comp603.shopping.services.CreditCardStrategy;
import com.comp603.shopping.services.PaymentStrategy;
import com.comp603.shopping.services.WalletStrategy;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CheckoutPanel extends JPanel {

    private MainFrame mainFrame;
    private JComboBox<String> paymentMethodCombo;
    private JPanel cardPanel;
    private JPanel walletPanel;
    private JPanel dynamicArea;

    // Card Fields
    private JTextField cardNumberField;
    private JTextField cvvField;
    private JTextField expiryField;

    // Wallet Fields
    private JLabel balanceLabel;

    public CheckoutPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(UIUtils.COLOR_BACKGROUND);

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);
        JButton backButton = new JButton("Back to Cart");
        UIUtils.styleButton(backButton, Color.GRAY);
        headerPanel.add(backButton);
        add(headerPanel, BorderLayout.NORTH);

        // Center Content
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(UIUtils.COLOR_BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Total Amount
        double total = mainFrame.getCart().getTotal();
        JLabel totalLabel = new JLabel(String.format("Total Amount: $%.2f", total));
        UIUtils.styleLabel(totalLabel, 18, true);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(totalLabel, gbc);

        // Payment Method Selection
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel methodLabel = new JLabel("Select Payment Method:");
        UIUtils.styleLabel(methodLabel, 14, false);
        centerPanel.add(methodLabel, gbc);

        String[] methods = { "Credit Card", "Wallet" };
        paymentMethodCombo = new JComboBox<>(methods);
        gbc.gridx = 1;
        centerPanel.add(paymentMethodCombo, gbc);

        // Card Details Panel
        cardPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        cardPanel.setBackground(UIUtils.COLOR_CARD_BG);
        cardPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UIUtils.COLOR_ACCENT),
                "Card Details", 0, 0, new Font("Monospaced", Font.BOLD, 12), UIUtils.COLOR_ACCENT));

        JLabel cardNumLabel = new JLabel("Card Number:");
        UIUtils.styleLabel(cardNumLabel, 12, false);
        cardPanel.add(cardNumLabel);

        cardNumberField = new JTextField();
        UIUtils.styleTextField(cardNumberField);
        cardPanel.add(cardNumberField);

        JLabel cvvLabel = new JLabel("CVV:");
        UIUtils.styleLabel(cvvLabel, 12, false);
        cardPanel.add(cvvLabel);

        cvvField = new JTextField();
        UIUtils.styleTextField(cvvField);
        cardPanel.add(cvvField);

        JLabel expiryLabel = new JLabel("Expiry (MM/YY):");
        UIUtils.styleLabel(expiryLabel, 12, false);
        cardPanel.add(expiryLabel);

        expiryField = new JTextField();
        UIUtils.styleTextField(expiryField);
        cardPanel.add(expiryField);

        // Wallet Details Panel
        walletPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        walletPanel.setBackground(UIUtils.COLOR_CARD_BG);
        walletPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UIUtils.COLOR_ACCENT),
                "Wallet Details", 0, 0, new Font("Monospaced", Font.BOLD, 12), UIUtils.COLOR_ACCENT));

        double balance = mainFrame.getAuthService().getCurrentUser().getBalance();
        balanceLabel = new JLabel(String.format("Current Balance: $%.2f", balance));
        UIUtils.styleLabel(balanceLabel, 14, true);
        walletPanel.add(balanceLabel);

        // Add Dynamic Panel Area
        dynamicArea = new JPanel(new CardLayout());
        dynamicArea.setOpaque(false);
        dynamicArea.add(cardPanel, "Credit Card");
        dynamicArea.add(walletPanel, "Wallet");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(dynamicArea, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        JButton payButton = new JButton("Confirm Payment");
        UIUtils.styleButton(payButton, new Color(60, 179, 113)); // MediumSeaGreen
        bottomPanel.add(payButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Listeners
        backButton.addActionListener(e -> mainFrame.showCart());

        paymentMethodCombo.addActionListener(e -> {
            CardLayout cl = (CardLayout) dynamicArea.getLayout();
            cl.show(dynamicArea, (String) paymentMethodCombo.getSelectedItem());
        });

        payButton.addActionListener(e -> processPayment());
    }

    private void processPayment() {
        String method = (String) paymentMethodCombo.getSelectedItem();
        double amount = mainFrame.getCart().getTotal();
        PaymentStrategy strategy = null;

        if ("Credit Card".equals(method)) {
            strategy = new CreditCardStrategy(
                    "User Name", // Simplified
                    cardNumberField.getText(),
                    cvvField.getText(),
                    expiryField.getText());
        } else {
            strategy = new WalletStrategy(mainFrame.getAuthService().getCurrentUser());
        }

        if (strategy.pay(amount)) {
            // Payment Successful -> Create Order
            if (createOrder()) {
                JOptionPane.showMessageDialog(this, "Payment Successful! Order Placed.");
                mainFrame.getCart().clear();
                mainFrame.showCard("PRODUCTS");
            } else {
                JOptionPane.showMessageDialog(this, "Payment processed but Order creation failed!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Payment Failed! Check details or balance.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean createOrder() {
        Order order = new Order();
        order.setUserId(mainFrame.getAuthService().getCurrentUser().getUserId());
        order.setTotalAmount(mainFrame.getCart().getTotal());
        order.setStatus("PAID");

        List<OrderItem> orderItems = new ArrayList<>();
        for (Product p : mainFrame.getCart().getItems()) {
            OrderItem item = new OrderItem();
            item.setProductId(p.getProductId());
            item.setQuantity(1); // Simplified: 1 unit per add
            item.setPriceAtPurchase(p.getPrice());
            orderItems.add(item);
        }
        order.setItems(orderItems);

        OrderDAO orderDAO = new OrderDAO();
        if (orderDAO.createOrder(order)) {
            // Update Stock
            ProductDAO productDAO = new ProductDAO();
            for (Product p : mainFrame.getCart().getItems()) {
                productDAO.updateStock(p.getProductId(), p.getStockQuantity() - 1);
            }
            return true;
        }
        return false;
    }
}
