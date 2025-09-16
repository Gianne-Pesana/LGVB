/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.components.panels;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class AvatarPanel extends JPanel {

    private BufferedImage image;
    private final int size;

    public AvatarPanel(BufferedImage image, int size) {
        this.image = image;
        this.size = UIScale.scale(size);
        init();
    }
    
    private void init() {
        setPreferredSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        setMinimumSize(new Dimension(size, size));
        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.primary");
        setOpaque(false);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (image != null) {
            Shape circle = new Ellipse2D.Double(0, 0, size, size);
            g2.setClip(circle);
            g2.drawImage(image, 0, 0, size, size, this);
        } else {
            // Draw placeholder gray circle
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillOval(0, 0, size, size);
        }

        g2.dispose();
    }
}