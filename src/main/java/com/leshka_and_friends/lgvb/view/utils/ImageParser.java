/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.utils;
import com.formdev.flatlaf.util.UIScale;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ImageParser {

    // Loads an image from resources and scales it to the given width and height
    // Width and height are design pixels and will be automatically scaled for HiDPI screens
    public static ImageIcon loadScaled(String resourcePath, int width, int height) {
        try {
            // getResource returns a URL
            BufferedImage original = ImageIO.read(ImageParser.class.getResource(resourcePath));

            // Scale the width and height according to UIScale
            int scaledWidth = UIScale.scale(width);
            int scaledHeight = UIScale.scale(height);

            Image scaled = original.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Loads an image from resources without scaling
    public static ImageIcon loadRaw(String resourcePath) {
        try {
            BufferedImage original = ImageIO.read(ImageParser.class.getResource(resourcePath));
            return new ImageIcon(original);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Converts a generic Image to BufferedImage
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }
}
