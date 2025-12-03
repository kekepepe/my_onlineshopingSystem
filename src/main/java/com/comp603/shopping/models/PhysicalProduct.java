package com.comp603.shopping.models;

public class PhysicalProduct extends Product {
    private double weight;

    public PhysicalProduct(int productId, String name, String description, double price, int stockQuantity,
            double weight, String imagePath) {
        super(productId, name, description, price, stockQuantity, imagePath);
        this.weight = weight;
    }

    @Override
    public String getDisplayInfo() {
        return String.format("%s (Physical) - Weight: %.2fkg - $%.2f", name, weight, price);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
