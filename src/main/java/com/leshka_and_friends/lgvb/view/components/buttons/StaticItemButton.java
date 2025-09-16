/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.components.buttons;

import javax.swing.UIManager;

/**
 *
 * @author giann
 */
public class StaticItemButton extends SidebarButtonPanel {

    public StaticItemButton(String text, String svgPath) {
        super(text, svgPath);
    }

    @Override
    public void applyCurrentStyle() {
        setBackground(UIManager.getColor("LGVB.primary"));
        repaint();
    }

    @Override
    protected void handleClick() {
        // Do nothing
    }
}