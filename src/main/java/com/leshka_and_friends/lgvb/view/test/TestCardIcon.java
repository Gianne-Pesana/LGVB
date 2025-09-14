/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.test;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGCardIconHelper;

import javax.swing.*;

public class TestCardIcon {

    public static void main(String[] args) throws Exception {
        FlatLightLaf.setup();

        JFrame frame = new JFrame("SVG Card Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 260);

        // Load card SVG dynamically
        FlatSVGIcon icon = new FlatSVGIcon("/cards/Card1.svg", 300, 200);

        JLabel label = new JLabel(icon);
        frame.add(label);

        frame.setVisible(true);
    }
}
