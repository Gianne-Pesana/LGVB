/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.ui_utils.common;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class FontLoader {

    private static Font interFont;

    // Baloo2
    private static Font baloo2Regular;
    private static Font baloo2Medium;
    private static Font baloo2SemiBold;
    private static Font baloo2Bold;
    private static Font baloo2ExtraBold;

    // IBM Plex Mono
    private static Font ibmPlexMonoRegular;
    private static Font ibmPlexMonoMedium;
    private static Font ibmPlexMonoSemiBold;
    private static Font ibmPlexMonoBold;

    public static void loadFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // Load Inter
        interFont = loadAndRegister("/fonts/Inter-Regular.ttf", ge);

        // Load Baloo2
        baloo2Regular = loadAndRegister("/fonts/Baloo2/Baloo2-Regular.ttf", ge);
        baloo2Medium = loadAndRegister("/fonts/Baloo2/Baloo2-Medium.ttf", ge);
        baloo2SemiBold = loadAndRegister("/fonts/Baloo2/Baloo2-SemiBold.ttf", ge);
        baloo2Bold = loadAndRegister("/fonts/Baloo2/Baloo2-Bold.ttf", ge);
        baloo2ExtraBold = loadAndRegister("/fonts/Baloo2/Baloo2-ExtraBold.ttf", ge);

        // Load IBM Plex Mono
        ibmPlexMonoRegular = loadAndRegister("/fonts/IBMPlexMono/IBMPlexMono-Regular.ttf", ge);
        ibmPlexMonoMedium = loadAndRegister("/fonts/IBMPlexMono/IBMPlexMono-Medium.ttf", ge);
        ibmPlexMonoSemiBold = loadAndRegister("/fonts/IBMPlexMono/IBMPlexMono-SemiBold.ttf", ge);
        ibmPlexMonoBold = loadAndRegister("/fonts/IBMPlexMono/IBMPlexMono-Bold.ttf", ge);
    }

    private static Font loadAndRegister(String path, GraphicsEnvironment ge) {
        try (InputStream is = FontLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Font not found: " + path);
                return null;
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            ge.registerFont(font);
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==== Getters ====
    public static Font getInter(float size) {
        return interFont.deriveFont(size);
    }

    public static Font getBaloo2Regular(float size) {
        return baloo2Regular.deriveFont(size);
    }

    public static Font getBaloo2Medium(float size) {
        return baloo2Medium.deriveFont(size);
    }

    public static Font getBaloo2SemiBold(float size) {
        return baloo2SemiBold.deriveFont(size);
    }

    public static Font getBaloo2Bold(float size) {
        return baloo2Bold.deriveFont(size);
    }

    public static Font getBaloo2ExtraBold(float size) {
        return baloo2ExtraBold.deriveFont(size);
    }

    public static Font getIbmPlexMonoRegular(float size) {
        return ibmPlexMonoRegular.deriveFont(size);
    }

    public static Font getIbmPlexMonoMedium(float size) {
        return ibmPlexMonoMedium.deriveFont(size);
    }

    public static Font getIbmPlexMonoSemiBold(float size) {
        return ibmPlexMonoSemiBold.deriveFont(size);
    }

    public static Font getIbmPlexMonoBold(float size) {
        return ibmPlexMonoBold.deriveFont(size);
    }

    public static Font getFont(String name, float size) {
        return switch (name.toLowerCase()) {
            case "inter" ->
                interFont.deriveFont(size);

            case "baloo2-regular" ->
                baloo2Regular.deriveFont(size);
            case "baloo2-medium" ->
                baloo2Medium.deriveFont(size);
            case "baloo2-semibold" ->
                baloo2SemiBold.deriveFont(size);
            case "baloo2-bold" ->
                baloo2Bold.deriveFont(size);
            case "baloo2-extrabold" ->
                baloo2ExtraBold.deriveFont(size);

            case "ibmplexmono-regular" ->
                ibmPlexMonoRegular.deriveFont(size);
            case "ibmplexmono-medium" ->
                ibmPlexMonoMedium.deriveFont(size);
            case "ibmplexmono-semibold" ->
                ibmPlexMonoSemiBold.deriveFont(size);
            case "ibmplexmono-bold" ->
                ibmPlexMonoBold.deriveFont(size);

            default -> {
                System.err.println("Font not found: " + name);
                yield interFont != null ? interFont.deriveFont(size) : new Font("SansSerif", Font.PLAIN, (int) size);
            }
        };
    }

}
