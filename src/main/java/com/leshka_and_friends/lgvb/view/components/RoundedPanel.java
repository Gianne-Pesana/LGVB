package com.leshka_and_friends.lgvb.view.components;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    protected int radius;

    public RoundedPanel() {
        this(20);
        setOpaque(false); // we'll handle painting ourselves
    }

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();

        // Call AFTER painting rounded background,
        // so child components (like JLabel) render on top
        super.paintComponent(g);
    }
}