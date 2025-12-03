package com.comp603.shopping.gui;

import com.comp603.shopping.dao.OrderDAO;
import com.comp603.shopping.dao.ProductDAO;
import com.comp603.shopping.models.Order;
import com.comp603.shopping.models.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JPanel {

    private MainFrame mainFrame;
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private ProductDAO productDAO;

    private JTable orderTable;
    private DefaultTableModel orderTableModel;
    private OrderDAO orderDAO;

    public AdminDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.productDAO = new ProductDAO();
        this.orderDAO = new OrderDAO();

        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        headerPanel.add(new JLabel("Admin Dashboard"));
        headerPanel.add(logoutButton);
        add(headerPanel, BorderLayout.NORTH);

        // Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Product Management", createProductPanel());
        tabbedPane.addTab("Order Management", createOrderPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Actions
        logoutButton.addActionListener(e -> mainFrame.logout());
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table
        String[] columnNames = { "ID", "Name", "Price", "Stock", "Type" };
        productTableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(productTableModel);
        loadProducts();

        panel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Product");
        JButton deleteButton = new JButton("Delete Product");
        JButton editButton = new JButton("Edit Product");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> {
            ProductDialog dialog = new ProductDialog(mainFrame, null);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                if (productDAO.addProduct(dialog.getProduct())) {
                    loadProducts();
                    JOptionPane.showMessageDialog(this, "Product added!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add product.");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                int productId = (int) productTableModel.getValueAt(selectedRow, 0);
                // Placeholder for delete logic - ideally ProductDAO should have deleteProduct
                JOptionPane.showMessageDialog(this,
                        "Delete Product ID: " + productId + " (Not implemented in DAO yet)");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product.");
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                int productId = (int) productTableModel.getValueAt(selectedRow, 0);
                // Find product object (inefficient but works for now)
                List<Product> products = productDAO.getAllProducts();
                Product selectedProduct = products.stream().filter(p -> p.getProductId() == productId).findFirst()
                        .orElse(null);

                if (selectedProduct != null) {
                    ProductDialog dialog = new ProductDialog(mainFrame, selectedProduct);
                    dialog.setVisible(true);
                    if (dialog.isConfirmed()) {
                        if (productDAO.updateProduct(dialog.getProduct())) {
                            loadProducts();
                            JOptionPane.showMessageDialog(this, "Product updated!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to update product.");
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product.");
            }
        });

        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table
        String[] columnNames = { "Order ID", "Customer", "Total Amount", "Status", "Date" };
        orderTableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(orderTableModel);
        loadOrders();

        panel.add(new JScrollPane(orderTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton shipButton = new JButton("Ship Order");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(shipButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        shipButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow >= 0) {
                int orderId = (int) orderTableModel.getValueAt(selectedRow, 0);
                if (orderDAO.updateOrderStatus(orderId, "SHIPPED")) {
                    loadOrders();
                    JOptionPane.showMessageDialog(this, "Order marked as Shipped!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update order.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an order.");
            }
        });

        refreshButton.addActionListener(e -> loadOrders());

        return panel;
    }

    private void loadProducts() {
        productTableModel.setRowCount(0);
        List<Product> products = productDAO.getAllProducts();
        for (Product p : products) {
            Object[] row = {
                    p.getProductId(),
                    p.getName(),
                    p.getPrice(),
                    p.getStockQuantity(),
                    p instanceof com.comp603.shopping.models.PhysicalProduct ? "Physical" : "Digital"
            };
            productTableModel.addRow(row);
        }
    }

    private void loadOrders() {
        orderTableModel.setRowCount(0);
        List<Order> orders = orderDAO.getAllOrders();
        for (Order o : orders) {
            Object[] row = {
                    o.getOrderId(),
                    o.getCustomerName(),
                    o.getTotalAmount(),
                    o.getStatus(),
                    o.getOrderDate()
            };
            orderTableModel.addRow(row);
        }
    }
}
