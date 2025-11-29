package com.comp603.shopping.models;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product) {
        items.add(product);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public List<Product> getItems() {
        return items;
    }

    public double getTotal() {
        return items.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clear() {
        items.clear();
    }
}
