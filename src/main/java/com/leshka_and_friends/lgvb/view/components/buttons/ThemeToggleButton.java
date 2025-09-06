/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.components.buttons;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.view.utils.SVGUtils;
import javax.swing.UIManager;

/**
 *
 * @author giann
 */
public class ThemeToggleButton extends SidebarButtonPanel {
    private boolean toggled;
    private final String svgPathOn, svgPathOff;
    private final String textOn, textOff;

    public ThemeToggleButton(String textOn, String textOff, String svgPathOn, String svgPathOff) {
        super(textOff, svgPathOff); // default
        this.textOn = textOn;
        this.textOff = textOff;
        this.svgPathOn = svgPathOn;
        this.svgPathOff = svgPathOff;
        this.toggled = false;
    }

    @Override
    public void applyCurrentStyle() {
        setBackground(hovered ? UIManager.getColor("MenuItem.hoverBackground")
                : UIManager.getColor("LGVB.primary"));
        repaint();
    }

    @Override
    protected void handleClick() {
        toggled = !toggled;
        label.setText(toggled ? textOn : textOff);
        FlatSVGIcon icon = new FlatSVGIcon(toggled ? svgPathOn : svgPathOff,
                (int) Math.floor(getHeight() * ICON_SCALE),
                (int) Math.floor(getHeight() * ICON_SCALE));
        icon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        label.setIcon(icon);
    }

    public boolean isToggled() {
        return toggled;
    }
}

