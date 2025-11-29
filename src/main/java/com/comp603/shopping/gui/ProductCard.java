package com.comp603.shopping.gui;

import com.comp603.shopping.models.Product;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProductCard extends JPanel {

    public ProductCard(Product product, ActionListener onAddToCart) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(UIUtils.COLOR_CARD_BG);
        setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
        setPreferredSize(new Dimension(200, 250)); // Fixed size for grid consistency

        // Icon Placeholder
        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(UIUtils.COLOR_BACKGROUND); // Darker background for icon
        iconPanel.setPreferredSize(new Dimension(100, 100));
        iconPanel.setMaximumSize(new Dimension(100, 100));

        JLabel iconLabel = new JLabel(String.valueOf(product.getName().charAt(0)).toUpperCase());
        iconLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        iconLabel.setForeground(UIUtils.COLOR_ACCENT);
        iconPanel.add(iconLabel);

        // Name
        JLabel nameLabel = new JLabel(product.getName());
        UIUtils.styleLabel(nameLabel, 14, true);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Price
        JLabel priceLabel = new JLabel(String.format("$%.2f", product.getPrice()));
        priceLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        priceLabel.setForeground(new Color(57, 255, 20)); // Neon Green
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Stock
        JLabel stockLabel = new JLabel("Stock: " + product.getStockQuantity());
        UIUtils.styleLabel(stockLabel, 12, false);
        stockLabel.setForeground(Color.GRAY);
        stockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add to Cart Button
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        UIUtils.styleButton(addToCartButton, UIUtils.COLOR_ACCENT);
        addToCartButton.addActionListener(onAddToCart);

        // Layout Spacing
        add(Box.createVerticalStrut(15));
        add(iconPanel);
        add(Box.createVerticalStrut(10));
        add(nameLabel);
        add(Box.createVerticalStrut(5));
        add(priceLabel);
        add(Box.createVerticalStrut(5));
        add(stockLabel);
        add(Box.createVerticalStrut(10));
        add(addToCartButton);
        add(Box.createVerticalStrut(15));

        // Hover Effect
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBorder(new LineBorder(UIUtils.COLOR_ACCENT, 2)); // Neon Glow
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
            }
        });
    }
}
