package com.leshka_and_friends.lgvb.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoundedButton extends JButton {

    private Color baseColor = new Color(50, 50, 50);
    private Color hoverColor = baseColor.brighter();
    private Color clickColor = baseColor.darker();
    private boolean isHovered = false;
    private boolean isPressed = false;
    private int radius = 20; // Default radius

    // Default constructor
    public RoundedButton(String text) {
        this(text, 20); // Calls the second constructor
    }

    // Constructor with custom radius
    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFont(new Font("Inter", Font.BOLD, 13));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBackground(baseColor);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                isPressed = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                System.out.println(text + " Button Clicked!");
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color currentColor = baseColor;
        if (isPressed) {
            currentColor = clickColor;
        } else if (isHovered) {
            currentColor = hoverColor;
        }

        g2.setColor(currentColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Optional subtle shadow
        g2.setColor(new Color(0, 0, 0, 60));
        g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, radius, radius);

        g2.dispose();

        super.paintComponent(g);
    }

    // Customization methods
    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        this.baseColor = bg;

        // Compute brightness (0=black, 1=white)
        float brightness = (0.299f * bg.getRed() + 0.587f * bg.getGreen() + 0.114f * bg.getBlue()) / 255f;

        if (brightness < 0.7f) {
            // Dark base → brighten hover
            this.hoverColor = brighten(bg, 1.2f); // 20% brighter
        } else {
            // Light base → darken hover
            this.hoverColor = darken(bg, 0.85f);  // 15% darker
        }

        this.clickColor = darken(bg, 0.7f); // always darker on click
        repaint();
    }

    private Color darken(Color color, float factor) {
        int r = Math.max((int) (color.getRed() * factor), 0);
        int g = Math.max((int) (color.getGreen() * factor), 0);
        int b = Math.max((int) (color.getBlue() * factor), 0);
        return new Color(r, g, b);
    }

    private Color brighten(Color color, float factor) {
        int r = Math.min((int) (color.getRed() * factor), 255);
        int g = Math.min((int) (color.getGreen() * factor), 255);
        int b = Math.min((int) (color.getBlue() * factor), 255);
        return new Color(r, g, b);
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setClickColor(Color clickColor) {
        this.clickColor = clickColor;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    public int getRadius() {
        return radius;
    }
}
