package com.comp603.shopping.dao;

import com.comp603.shopping.config.DBManager;
import com.comp603.shopping.models.Order;
import com.comp603.shopping.models.OrderItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDAO {

    public boolean createOrder(Order order) {
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement itemStmt = null;
        ResultSet generatedKeys = null;

        String orderSql = "INSERT INTO ORDERS (USER_ID, TOTAL_AMOUNT, STATUS) VALUES (?, ?, ?)";
        String itemSql = "INSERT INTO ORDER_ITEMS (ORDER_ID, PRODUCT_ID, QUANTITY, PRICE_AT_PURCHASE) VALUES (?, ?, ?, ?)";

        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false); // Start Transaction

            // 1. Insert Order
            orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, order.getUserId());
            orderStmt.setDouble(2, order.getTotalAmount());
            orderStmt.setString(3, order.getStatus());

            int affectedRows = orderStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            generatedKeys = orderStmt.getGeneratedKeys();
            int orderId;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
                order.setOrderId(orderId);
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }

            // 2. Insert Order Items
            itemStmt = conn.prepareStatement(itemSql);
            for (OrderItem item : order.getItems()) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getProductId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setDouble(4, item.getPriceAtPurchase());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch();

            conn.commit(); // Commit Transaction
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            try {
                if (generatedKeys != null)
                    generatedKeys.close();
                if (orderStmt != null)
                    orderStmt.close();
                if (itemStmt != null)
                    itemStmt.close();
                if (conn != null)
                    conn.setAutoCommit(true); // Reset auto-commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
