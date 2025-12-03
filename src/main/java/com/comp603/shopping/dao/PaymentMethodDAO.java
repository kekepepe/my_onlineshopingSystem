package com.comp603.shopping.dao;

import com.comp603.shopping.config.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDAO {

    public boolean addPaymentMethod(int userId, String cardNumber, String expiryDate) {
        String sql = "INSERT INTO PAYMENT_METHODS (USER_ID, CARD_NUMBER, EXPIRY_DATE) VALUES (?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, cardNumber);
            pstmt.setString(3, expiryDate);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getPaymentMethods(int userId) {
        List<String> methods = new ArrayList<>();
        String sql = "SELECT CARD_NUMBER FROM PAYMENT_METHODS WHERE USER_ID = ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String card = rs.getString("CARD_NUMBER");
                // Mask card number for display
                if (card.length() > 4) {
                    methods.add("**** **** **** " + card.substring(card.length() - 4));
                } else {
                    methods.add(card);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return methods;
    }
}
