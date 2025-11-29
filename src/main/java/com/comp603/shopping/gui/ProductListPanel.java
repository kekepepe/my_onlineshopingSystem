package com.comp603.shopping.gui;

import com.comp603.shopping.dao.ProductDAO;
import com.comp603.shopping.models.Product;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductListPanel extends JPanel {

    private MainFrame mainFrame;
    private JPanel productContainer;
    private ProductDAO productDAO;

    public ProductListPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.productDAO = new ProductDAO();

        setLayout(new BorderLayout());
        setBackground(UIUtils.COLOR_BACKGROUND);

        // Product Container (Grid Layout)
        productContainer = new JPanel(new GridLayout(0, 3, 20, 20)); // 3 columns, gaps
        productContainer.setBackground(UIUtils.COLOR_BACKGROUND);
        productContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        loadProducts();

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(productContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(UIUtils.COLOR_BACKGROUND);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateProductList(List<Product> products) {
        productContainer.removeAll();

        if (products.isEmpty()) {
            JLabel notFoundLabel = new JLabel("No products found.");
            notFoundLabel.setFont(new Font("Arial", Font.BOLD, 18));
            notFoundLabel.setHorizontalAlignment(SwingConstants.CENTER);
            productContainer.add(notFoundLabel);
        } else {
            for (Product p : products) {
                ProductCard card = new ProductCard(p, e -> {
                    if (p.getStockQuantity() > 0) {
                        mainFrame.getCart().addProduct(p);
                        JOptionPane.showMessageDialog(mainFrame, "Added to cart: " + p.getName());
                        mainFrame.updateCartCount();
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Out of stock!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                productContainer.add(card);
            }
        }
        productContainer.revalidate();
        productContainer.repaint();
    }

    private void loadProducts() {
        List<Product> productList = productDAO.getAllProducts();
        updateProductList(productList);
    }
}
