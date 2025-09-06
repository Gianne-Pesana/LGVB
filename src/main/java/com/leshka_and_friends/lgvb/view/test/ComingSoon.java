/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.test;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class ComingSoon extends JPanel {

    public ComingSoon() {
        setPreferredSize(new Dimension(250, 810));

        setLayout(new GridBagLayout()); // Centers everything

        JLabel label = new JLabel("COMING SOON...");
        label.setFont(loadInterFont(24f)); // set Inter with size 24px
        add(label);
    }

    private Font loadInterFont(float size) {
        try (InputStream is = getClass().getResourceAsStream("/fonts/Inter-Regular.ttf")) {
            if (is == null) {
                System.err.println("⚠️ Could not find Inter-Regular.ttf, using fallback.");
                return new Font("SansSerif", Font.PLAIN, (int) size);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }
}


