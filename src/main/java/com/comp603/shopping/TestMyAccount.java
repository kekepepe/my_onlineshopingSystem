package com.comp603.shopping;

import com.comp603.shopping.dao.*;
import com.comp603.shopping.models.*;
import com.comp603.shopping.config.DBManager;

public class TestMyAccount {
    public static void main(String[] args) {
        System.out.println("Starting Backend Verification...");

        // Initialize DB
        DBManager.getConnection();

        UserDAO userDAO = new UserDAO();
        WishlistDAO wishlistDAO = new WishlistDAO();
        PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();
        OrderDAO orderDAO = new OrderDAO();

        // 1. Create User
        String username = "testuser_" + System.currentTimeMillis();
        userDAO.registerUser(username, "password");
        User user = userDAO.getUserByUsername(username);
        System.out.println("User Created: " + (user != null));

        if (user != null) {
            // 2. Test Profile Update
            user.setEmail("newemail@example.com");
            boolean updateSuccess = userDAO.updateUser(user);
            System.out.println("Profile Update: " + updateSuccess);

            // 3. Test Wallet
            boolean balanceSuccess = userDAO.updateUserBalance(user.getUserId(), 500.0);
            System.out.println("Balance Update: " + balanceSuccess);

            boolean cardSuccess = paymentMethodDAO.addPaymentMethod(user.getUserId(), "1234567890123456", "12/25");
            System.out.println("Add Card: " + cardSuccess);
            System.out.println("Payment Methods: " + paymentMethodDAO.getPaymentMethods(user.getUserId()));

            // 4. Test Wishlist
            // Assuming product ID 1 exists from seed data
            boolean wishAdd = wishlistDAO.addToWishlist(user.getUserId(), 1);
            System.out.println("Add to Wishlist: " + wishAdd);
            System.out.println("Is in Wishlist: " + wishlistDAO.isInWishlist(user.getUserId(), 1));
            System.out.println("Wishlist Size: " + wishlistDAO.getWishlist(user.getUserId()).size());

            boolean wishRemove = wishlistDAO.removeFromWishlist(user.getUserId(), 1);
            System.out.println("Remove from Wishlist: " + wishRemove);

            // 5. Test Order History (Mocking an order)
            // Need to create an order first
            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setTotalAmount(100.0);
            order.setStatus("PAID");
            order.setItems(new java.util.ArrayList<>()); // Empty items for test

            boolean orderCreated = orderDAO.createOrder(order);
            System.out.println("Order Created: " + orderCreated);

            java.util.List<Order> orders = orderDAO.getOrdersByUserId(user.getUserId());
            System.out.println("Order History Size: " + orders.size());

            if (!orders.isEmpty()) {
                int orderId = orders.get(0).getOrderId();
                boolean cancelSuccess = orderDAO.cancelOrder(orderId);
                System.out.println("Cancel Order: " + cancelSuccess);
            }
        }

        System.out.println("Verification Complete.");
    }
}
