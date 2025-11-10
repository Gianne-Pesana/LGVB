/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.factories;

import com.leshka_and_friends.lgvb.view.shared_components.buttons.ThemeToggleButton;
import com.leshka_and_friends.lgvb.view.shared_components.buttons.MenuItemButton;
import com.leshka_and_friends.lgvb.view.shared_components.buttons.StaticItemButton;

/**
 *
 * @author giann
 */
public class SidebarButtonFactory {

    public static MenuItemButton createMenuItem(String text, String svgPath, boolean clickable) {
        return new MenuItemButton(text, svgPath, clickable);
    }

    public static StaticItemButton createStaticItem(String text, String svgPath) {
        return new StaticItemButton(text, svgPath);
    }

    public static ThemeToggleButton createThemeToggleButton(String textOn, String textOff,
            String svgPathOn, String svgPathOff) {
        return new ThemeToggleButton(textOn, textOff, svgPathOn, svgPathOff);
    }
}