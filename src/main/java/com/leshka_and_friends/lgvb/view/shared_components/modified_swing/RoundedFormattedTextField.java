package com.leshka_and_friends.lgvb.view.shared_components.modified_swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RoundedFormattedTextField extends JFormattedTextField {

    private int radius = 15;
    private Color borderColor = new Color(200, 200, 200);
    private Color focusColor = new Color(120, 150, 255);
    private Color backgroundColor = new Color(240, 240, 240);
    private boolean focused = false;

    public RoundedFormattedTextField() {
        this(15, 20);
    }

    public RoundedFormattedTextField(int radius) {
        this(15, radius);
    }

    public RoundedFormattedTextField(int columns, int radius) {
        super();
        setColumns(columns);
        this.radius = radius;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // padding
        setBackground(backgroundColor);
        setForeground(Color.BLACK);
        setFont(new Font("Inter", Font.PLAIN, 13));

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
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Draw border
        g2.setStroke(new BasicStroke(1.6f));
        g2.setColor(focused ? focusColor : borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }

    // Setters for customization
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

