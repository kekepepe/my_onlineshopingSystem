package com.comp603.shopping.models;

public class DigitalProduct extends Product {
    private String downloadLink;

    public DigitalProduct(int productId, String name, String description, double price, int stockQuantity,
            String downloadLink) {
        super(productId, name, description, price, stockQuantity);
        this.downloadLink = downloadLink;
    }

    @Override
    public String getDisplayInfo() {
        return String.format("%s (Digital) - Instant Download - $%.2f", name, price);
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
}
