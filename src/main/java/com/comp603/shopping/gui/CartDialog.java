package com.comp603.shopping.gui;

import com.comp603.shopping.models.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CartDialog extends JDialog {

    private MainFrame mainFrame;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    public CartDialog(MainFrame mainFrame) {
        super(mainFrame, "Your Cart", true); // Modal
        this.mainFrame = mainFrame;

        setSize(500, 400);
        setLocationRelativeTo(mainFrame);
        setLayout(new BorderLayout());

        // Cart Table
        String[] columnNames = { "Name", "Price", "Type" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable cartTable = new JTable(tableModel);
        add(new JScrollPane(cartTable), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: $0.00");
        JButton closeButton = new JButton("Close");
        JButton checkoutButton = new JButton("Checkout");

        bottomPanel.add(totalLabel);
        bottomPanel.add(closeButton);
        bottomPanel.add(checkoutButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Actions
        closeButton.addActionListener(e -> dispose());

        checkoutButton.addActionListener(e -> {
            if (mainFrame.getCart().getItems().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cart is empty!");
                return;
            }
            dispose(); // Close dialog
            // Trigger checkout in MainFrame
            mainFrame.startCheckout();
        });

        refreshCart();
    }

    private void refreshCart() {
        tableModel.setRowCount(0);
        List<Product> items = mainFrame.getCart().getItems();
        for (Product p : items) {
            Object[] row = {
                    p.getName(),
                    String.format("$%.2f", p.getPrice()),
                    p instanceof com.comp603.shopping.models.PhysicalProduct ? "Physical" : "Digital"
            };
            tableModel.addRow(row);
        }
        totalLabel.setText(String.format("Total: $%.2f", mainFrame.getCart().getTotal()));
    }
}
