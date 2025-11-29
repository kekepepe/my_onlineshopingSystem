package com.comp603.shopping.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class UIUtils {

    // Cyberpunk Theme Colors
    public static final Color COLOR_BACKGROUND = new Color(18, 18, 18); // Deep Dark
    public static final Color COLOR_CARD_BG = new Color(30, 30, 30); // Lighter Dark
    public static final Color COLOR_ACCENT = new Color(0, 255, 255); // Electric Cyan
    public static final Color COLOR_TEXT = Color.WHITE;
    public static final Color COLOR_TEXT_HIGHLIGHT = new Color(0, 255, 255);
    public static final Color COLOR_INTERACTIVE_DARK = new Color(45, 45, 45);

    public static void styleButton(JButton btn, Color accentColor) {
        btn.setFocusPainted(false);
        btn.setBackground(Color.BLACK); // Dark background for contrast
        btn.setForeground(accentColor); // Neon text
        btn.setFont(new Font("Monospaced", Font.BOLD, 14));
        // Neon Glow Border Effect (Simulated with LineBorder)
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(accentColor, 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(accentColor);
                btn.setForeground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.BLACK);
                btn.setForeground(accentColor);
            }
        });
    }

    public static void styleTextField(JTextField field) {
        field.setBackground(COLOR_INTERACTIVE_DARK);
        field.setForeground(COLOR_TEXT);
        field.setCaretColor(COLOR_ACCENT);
        field.setFont(new Font("Monospaced", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        // Focus Effect
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(COLOR_ACCENT, 1),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(Color.GRAY, 1),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            }
        });
    }

    public static void styleLabel(JLabel label, int size, boolean isBold) {
        label.setForeground(COLOR_TEXT);
        label.setFont(new Font("Monospaced", isBold ? Font.BOLD : Font.PLAIN, size));
    }
}
