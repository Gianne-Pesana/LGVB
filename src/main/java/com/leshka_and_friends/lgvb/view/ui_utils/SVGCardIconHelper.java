package com.leshka_and_friends.lgvb.view.ui_utils;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Helper to load a cleaned SVG card from classpath, replace placeholders,
 * and return a FlatSVGIcon ready to use.
 */
public class SVGCardIconHelper {

    /**
     * Load cleaned SVG from resources, replace placeholders, and return FlatSVGIcon.
     *
     * @param resourcePath Classpath path to the cleaned SVG (e.g., "/cards/Card1_cleaned.svg")
     * @param holderName   Card holder name
     * @param cardNumber   Card number
     * @param expDate      Expiration date
     * @param cardType     Card type
     * @param width        Desired icon width
     * @return FlatSVGIcon ready to use
     * @throws IOException if the resource cannot be read
     */
    public static FlatSVGIcon loadCardIcon(String resourcePath,
                                           String holderName,
                                           String cardNumber,
                                           String expDate,
                                           String cardType,
                                           float width) throws IOException {

        InputStream is = SVGCardIconHelper.class.getResourceAsStream(resourcePath);
        if (is == null) {
            throw new FileNotFoundException("SVG resource not found: " + resourcePath);
        }

        // Read SVG content
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }

        String svgContent = sb.toString();

        // Remove XML declaration if present (FlatSVGIcon cannot parse it)
        if (svgContent.startsWith("<?xml")) {
            int index = svgContent.indexOf("?>");
            if (index != -1) {
                svgContent = svgContent.substring(index + 2).trim();
            }
        }

        // Replace placeholders
        svgContent = svgContent.replace("card_holder_field", holderName)
                               .replace("card_number_field", cardNumber)
                               .replace("exp_field", expDate)
                               .replace("c_type", cardType);

        // Create and return FlatSVGIcon
        return new FlatSVGIcon(svgContent, width, SVGCardIconHelper.class.getClassLoader());
    }
}