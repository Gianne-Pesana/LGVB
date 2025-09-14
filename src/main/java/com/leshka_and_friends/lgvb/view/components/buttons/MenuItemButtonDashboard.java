/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.components.buttons;

import java.awt.Color;
import javax.swing.UIManager;

/**
 *
 * @author vongiedyaguilar
 */public class MenuItemButtonDashboard extends DashboardButtonPanel {

    private final boolean paintClick;

    public MenuItemButtonDashboard(String svgPath, String text, boolean paintClick) {
        super(svgPath, text);
        this.paintClick = paintClick;
    }

    @Override
    public void applyCurrentStyle() {
        Color bg;
        if (selected && paintClick) {
            bg = UIManager.getColor("DashboardButton.selectedBackground");
        } else if (!selected && hovered) {
            bg = UIManager.getColor("DashboardButton.hoverBackground");
        } else {
            bg = UIManager.getColor("LGVB.primary");
        }

        setBackground(bg);
        putClientProperty("FlatLaf.style", "background: " + (selected ? "$LGVB.accent" : (hovered ? "$LGVB.hover" : "$LGVB.primary")) + ";");
        repaint();
    }

    @Override
    protected void handleClick() {
        if (paintClick) {
            selected = true;
            hovered = false;
        }
    }
}