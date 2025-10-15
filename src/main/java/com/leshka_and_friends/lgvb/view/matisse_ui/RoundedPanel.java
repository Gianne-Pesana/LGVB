/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.matisse_ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.beans.BeanProperty;

/**
 *
 * @author giann
 */
public class RoundedPanel extends javax.swing.JPanel {

    /**
     * Creates new form Items
     */
    private int radius = 25;

    public RoundedPanel() {
        initComponents();
        setOpaque(false); // we handle our own background painting

        // Make sure you can see it in Matisse
        if (java.beans.Beans.isDesignTime()) {
            setBackground(new Color(200, 200, 200));
        }
    }


    @BeanProperty(preferred = true, description = "Corner radius for the panel")
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(204, 204, 204));
        setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            RoundRectangle2D round = new RoundRectangle2D.Float(0, 0, w, h, radius, radius);

            // paint background as rounded rect
            g2.setColor(getBackground());
            g2.fill(round);

            // clip so children respect rounded edges
            g2.setClip(round);

            // paint children inside
            super.paintComponent(g2);
        } finally {
            g2.dispose();
        }
    }

    @Override
    public boolean contains(int x, int y) {
        if (radius <= 0) {
            return super.contains(x, y);
        }
        return new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius)
                .contains(x, y);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
