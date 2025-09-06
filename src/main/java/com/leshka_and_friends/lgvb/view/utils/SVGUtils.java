/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.utils;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

public class SVGUtils {

    private SVGUtils() {
    } // utility class → no instantiation

    public static FlatSVGIcon loadIcon(String path, int size, String colorKey) {
        FlatSVGIcon icon = new FlatSVGIcon(path, size, size);
        icon.setColorFilter(createColorFilter(colorKey));
        return icon;
    }

    public static FlatSVGIcon.ColorFilter createColorFilter(String uiKey) {
        return new FlatSVGIcon.ColorFilter(c -> UIManager.getColor(uiKey));
    }
}
