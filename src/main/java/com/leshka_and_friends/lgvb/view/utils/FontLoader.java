/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.utils;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class FontLoader {
    private static Font interFont;

    public static void loadFonts() {
        try (InputStream is = FontLoader.class.getResourceAsStream("/fonts/Inter-Regular.ttf")) {
            interFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(interFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Font getInter(float size) {
        return interFont.deriveFont(size);
    }
}

