package com.leshka_and_friends.lgvb.view.shared_components.modified_swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RoundedComboBox<E> extends JComboBox<E> {

    private int radius = 15;
    private Color borderColor = new Color(200, 200, 200);
    private Color focusColor = new Color(120, 150, 255);
    private Color backgroundColor = new Color(245, 245, 245);
    private boolean focused = false;

    public RoundedComboBox() {
        super();
        setOpaque(false);
        setFont(new Font("Inter", Font.PLAIN, 14));
        setForeground(Color.BLACK);
        setBackground(backgroundColor);
        setBorder(new EmptyBorder(5, 10, 5, 10));

        // Stop FlatLaf from restyling on theme switch
        putClientProperty("JComponent.roundRect", true);
        putClientProperty("FlatLaf.ignoreDefaultBorder", true);
        putClientProperty("FlatLaf.styleClass", null);

        // Install stable UI
        setUI(new StableComboBoxUI());

        // Handle focus effect
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                focused = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                focused = false;
                repaint();
            }
        });

        // Popup cell renderer
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                list.setBackground(backgroundColor);
                list.setSelectionBackground(focusColor);
                list.setSelectionForeground(Color.WHITE);
                setBackground(isSelected ? focusColor : backgroundColor);
                setForeground(isSelected ? Color.WHITE : Color.BLACK);
                return this;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Border
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(focused ? focusColor : borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }

    // Custom arrow + no FlatLaf override
    private static class StableComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton arrow = new JButton("▾");
            arrow.setBorder(BorderFactory.createEmptyBorder());
            arrow.setContentAreaFilled(false);
            arrow.setFocusPainted(false);
            arrow.setOpaque(false);
            arrow.setForeground(Color.GRAY);
            return arrow;
        }
    }

    // Setters
    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public void setFocusColor(Color focusColor) {
        this.focusColor = focusColor;
        repaint();
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint();
    }
}
