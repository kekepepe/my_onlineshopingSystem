package com.comp603.shopping.models;

/**
 * Abstract base class for all products.
 * Demonstrates Abstraction and Inheritance.
 */
public abstract class Product {
    protected int productId;
    protected String name;
    protected String description;
    protected double price;
    protected int stockQuantity;
    protected String imagePath; // Added imagePath field

    // Added no-argument constructor
    public Product() {
    }

    public Product(int productId, String name, String description, double price, int stockQuantity, String imagePath) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imagePath = imagePath; // Initialize imagePath
    }

    // Abstract method to be implemented by subclasses (Polymorphism)
    public abstract String getDisplayInfo();

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    // Added getter for imagePath
    public String getImagePath() {
        return imagePath;
    }

    // Added setter for imagePath
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
