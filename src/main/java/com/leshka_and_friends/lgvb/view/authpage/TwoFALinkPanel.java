/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.authpage;

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
        header.setFont(FontLoader.getInter(24f).deriveFont(Font.BOLD));
        ThemeManager.putThemeAwareProperty(header, "foreground: $LGVB.foreground");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Message JLabel
        JLabel message = new JLabel("<html><div style='text-align: center;'>"
                + "<p>While your account is pending approval, "
                + "<br>please link it to an authenticator app "
                + "<br>by scanning this QR code."
                + "<br><b>You cannot log in without this</b></p>"
                + "</div></html>");
        message.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(message, "foreground: $LGVB.foreground");
        message.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Wrapper panel to center the message
        JPanel messageWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        messageWrapper.setOpaque(false);
        messageWrapper.add(message);
        messageWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        // QR Code
        JLabel qrLabel = new JLabel(new ImageIcon(generateQrImage(otpAuthUrl, 250, 250)));
        qrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        qrLabel.setBorder(new EmptyBorder(10, 0, 20, 0));

        // Button
        Dimension buttonSize = new Dimension(
                ThemeGlobalDefaults.getScaledInt("TwoFALink.button.width"),
                ThemeGlobalDefaults.getScaledInt("TwoFALink.button.height")
        );

        confirmButton = new RoundedButton("I have linked my account", ThemeGlobalDefaults.getInt("Button.arc"));
        confirmButton.setFont(FontLoader.getInter(14f));
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setPreferredSize(buttonSize);
        confirmButton.setMaximumSize(buttonSize);
        confirmButton.setBackground(ThemeGlobalDefaults.getColor("TwoFALink.button.background"));
        confirmButton.setForeground(ThemeGlobalDefaults.getColor("TwoFALink.button.foreground"));

        add(Box.createVerticalGlue());
        add(header);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(messageWrapper);
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
