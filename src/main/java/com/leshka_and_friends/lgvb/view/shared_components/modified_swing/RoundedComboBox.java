package com.leshka_and_friends.lgvb.view.shared_components.modified_swing;

import javax.swing.*;
import java.awt.*;

public class RoundedComboBox<E> extends JComboBox<E> {

    public RoundedComboBox() {
        super();
        setOpaque(false); // let FlatLaf handle background
        setFont(new Font("Inter", Font.PLAIN, 14));
        setForeground(null); // use FlatLaf foreground
        setBackground(null); // use FlatLaf background

        // Enable FlatLaf rounded style
        putClientProperty("JComponent.roundRect", true);
        putClientProperty("FlatLaf.styleClass", null);
        putClientProperty("FlatLaf.ignoreDefaultBorder", true);

        // Minimal arrow button for FlatLaf
        setUI(new MinimalArrowComboBoxUI());
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // Center the text
                label.setHorizontalAlignment(SwingConstants.CENTER);

                // Use theme colors
                label.setBackground(isSelected
                        ? UIManager.getColor("ComboBox.selectionBackground")
                        : UIManager.getColor("ComboBox.background"));
                label.setForeground(isSelected
                        ? UIManager.getColor("ComboBox.selectionForeground")
                        : UIManager.getColor("ComboBox.foreground"));

                return label;
            }
        });


        // ✅ Force UI to pick up theme immediately
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();

        // Pick up theme colors dynamically from UIManager
        Color bg = UIManager.getColor("ComboBox.background");
        Color fg = UIManager.getColor("ComboBox.foreground");
        setBackground(bg != null ? bg : getBackground());
        setForeground(fg != null ? fg : getForeground());

        // Refresh arrow button too
        if (getUI() instanceof MinimalArrowComboBoxUI) {
            JButton arrow = ((MinimalArrowComboBoxUI) getUI()).arrowButton;
            if (arrow != null) {
                arrow.setForeground(getForeground());
                arrow.setBackground(getBackground());
            }
        }

        repaint();
    }


    // Minimal arrow button for FlatLaf
    private static class MinimalArrowComboBoxUI extends javax.swing.plaf.basic.BasicComboBoxUI {
        JButton arrowButton;

        @Override
        protected JButton createArrowButton() {
            arrowButton = new JButton("▾");
            arrowButton.setBorder(null);
            arrowButton.setContentAreaFilled(false);
            arrowButton.setFocusPainted(false);
            arrowButton.setOpaque(false);
            arrowButton.setForeground(null); // follow theme
            return arrowButton;
        }
    }
}
