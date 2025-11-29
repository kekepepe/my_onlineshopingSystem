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
        getContentPane().setBackground(UIUtils.COLOR_BACKGROUND);

        // Cart Table
        String[] columnNames = { "Name", "Price", "Type" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable cartTable = new JTable(tableModel);
        cartTable.setBackground(UIUtils.COLOR_CARD_BG);
        cartTable.setForeground(UIUtils.COLOR_TEXT);
        cartTable.setGridColor(Color.DARK_GRAY);
        cartTable.setSelectionBackground(UIUtils.COLOR_ACCENT);
        cartTable.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.getViewport().setBackground(UIUtils.COLOR_BACKGROUND);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(UIUtils.COLOR_BACKGROUND);

        totalLabel = new JLabel("Total: $0.00");
        UIUtils.styleLabel(totalLabel, 16, true);

        JButton closeButton = new JButton("Close");
        UIUtils.styleButton(closeButton, Color.GRAY);

        JButton checkoutButton = new JButton("Checkout");
        UIUtils.styleButton(checkoutButton, new Color(60, 179, 113)); // MediumSeaGreen

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
