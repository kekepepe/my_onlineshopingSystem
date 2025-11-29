package com.comp603.shopping.dao;

import com.comp603.shopping.config.DBManager;
import com.comp603.shopping.models.DigitalProduct;
import com.comp603.shopping.models.PhysicalProduct;
import com.comp603.shopping.models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTS";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("TYPE");
                Product product;

                if ("PHYSICAL".equalsIgnoreCase(type)) {
                    product = new PhysicalProduct(
                            rs.getInt("PRODUCT_ID"),
                            rs.getString("NAME"),
                            rs.getString("DESCRIPTION"),
                            rs.getDouble("PRICE"),
                            rs.getInt("STOCK_QUANTITY"),
                            rs.getDouble("WEIGHT"));
                } else {
                    product = new DigitalProduct(
                            rs.getInt("PRODUCT_ID"),
                            rs.getString("NAME"),
                            rs.getString("DESCRIPTION"),
                            rs.getDouble("PRICE"),
                            rs.getInt("STOCK_QUANTITY"),
                            rs.getString("DOWNLOAD_LINK"));
                }
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean updateStock(int productId, int newQuantity) {
        String sql = "UPDATE PRODUCTS SET STOCK_QUANTITY = ? WHERE PRODUCT_ID = ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, productId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTS WHERE LOWER(NAME) LIKE ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String type = rs.getString("TYPE");
                Product product;

                if ("PHYSICAL".equalsIgnoreCase(type)) {
                    product = new PhysicalProduct(
                            rs.getInt("PRODUCT_ID"),
                            rs.getString("NAME"),
                            rs.getString("DESCRIPTION"),
                            rs.getDouble("PRICE"),
                            rs.getInt("STOCK_QUANTITY"),
                            rs.getDouble("WEIGHT"));
                } else {
                    product = new DigitalProduct(
                            rs.getInt("PRODUCT_ID"),
                            rs.getString("NAME"),
                            rs.getString("DESCRIPTION"),
                            rs.getDouble("PRICE"),
                            rs.getInt("STOCK_QUANTITY"),
                            rs.getString("DOWNLOAD_LINK"));
                }
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
