package com.comp603.shopping.services;

/**
 * Strategy Interface for Payment processing.
 * This interface defines the contract for all payment methods.
 * By using this interface, the system can switch between different payment
 * implementations
 * (e.g., Credit Card, Wallet) at runtime without changing the client code.
 * This demonstrates the Strategy Design Pattern.
 */
public interface PaymentStrategy {
    /**
     * Process the payment of the given amount.
     * 
     * @param amount The total amount to pay.
     * @return true if payment is successful, false otherwise.
     */
    boolean pay(double amount);
}
