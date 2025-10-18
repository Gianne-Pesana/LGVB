/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.leshka_and_friends.lgvb.view.components.RoundedButton;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Panel displayed after registration, showing QR code for linking to Google
 * Authenticator or another TOTP app.
 */
public class TwoFALinkPanel extends JPanel {

    private RoundedButton confirmButton;

    public TwoFALinkPanel(String otpAuthUrl) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.background");
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel header = new JLabel("Account Pending Approval");
        header.setFont(FontLoader.getInter(20f));
        ThemeManager.putThemeAwareProperty(header, "foreground: $LGVB.foreground");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message = new JLabel("<html><div style='text-align: center;'>"
                + "While your account is pending approval, please link it<br>"
                + "to an authenticator app by scanning this QR code."
                + "</div></html>");
        message.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(message, "foreground: $LGVB.foreground");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        message.setBorder(new EmptyBorder(20, 0, 20, 0));

        // QR Code
        JLabel qrLabel = new JLabel(new ImageIcon(generateQrImage(otpAuthUrl, 250, 250)));
        qrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        qrLabel.setBorder(new EmptyBorder(10, 0, 20, 0));

        // Button
        confirmButton = new RoundedButton("I have linked my account", 15);
        confirmButton.setFont(FontLoader.getInter(14f));
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setMaximumSize(new Dimension(250, 40));
        confirmButton.setBaseColor(new Color(44, 120, 101));
        confirmButton.setForeground(Color.WHITE);

        add(Box.createVerticalGlue());
        add(header);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(message);
        add(qrLabel);
        add(confirmButton);
        add(Box.createVerticalGlue());
    }

    /**
     * Generates a QR code as an Image object.
     */
    private Image generateQrImage(String otpAuthUrl, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix matrix = qrCodeWriter.encode(otpAuthUrl, BarcodeFormat.QR_CODE, width, height);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);
            return qrImage;
        } catch (WriterException e) {
            e.printStackTrace();
            BufferedImage fallback = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = fallback.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.RED);
            g.drawString("QR Generation Failed", 40, height / 2);
            g.dispose();
            return fallback;
        }
    }

    public RoundedButton getConfirmButton() {
        return confirmButton;
    }
}
