package com.leshka_and_friends.lgvb.view.components.panels;

import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import javax.swing.*;
import java.awt.*;

/**
 * Dumb base panel that paints a rounded rectangle. No hover or click behavior.
 */
public class RoundedPanel extends JPanel {

    protected int radius = ThemeGlobalDefaults.getInt("Button.arc");

    public RoundedPanel() {
        setOpaque(false); // we'll handle painting ourselves
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