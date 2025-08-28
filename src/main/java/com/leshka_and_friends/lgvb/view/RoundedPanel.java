package com.leshka_and_friends.lgvb.view;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private final int radius;
    private final Color selectedColor;
    private boolean selected = false;
    private boolean hovered = false;

    public RoundedPanel(int radius, Color selectedColor) {
        this.radius = radius;
        this.selectedColor = selectedColor;
        setOpaque(false);
    }

    public void setSelected(boolean sel) {
        this.selected = sel;
    }

    public void setHovered(boolean hov) {
        this.hovered = hov;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (selected) {
            g2.setColor(selectedColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        } else if (hovered) {
            g2.setColor(new Color(255, 99, 71, 180)); // reddish hover effect
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
