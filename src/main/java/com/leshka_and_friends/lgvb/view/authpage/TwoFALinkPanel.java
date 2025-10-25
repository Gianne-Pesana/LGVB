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
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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

        JLabel message = new JLabel("<html><div style='text-align: center;'>"
                + "<p>While your account is pending approval, "
                + "<br>please link it to an authenticator app "
                + "<br>by scanning this QR code."
                + "<br><b>You cannot log in without this</b></p>"
                + "</div></html>");
        message.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(message, "foreground: $LGVB.foreground");
        message.setBorder(new EmptyBorder(20, 0, 20, 0));

        JPanel messageWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        messageWrapper.setOpaque(false);
        messageWrapper.add(message);
        messageWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        // QR Code
        JLabel qrLabel = new JLabel(new ImageIcon(generateQrImage(otpAuthUrl, 250, 250)));
        qrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        qrLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Extract actual secret from URL
        String code = extractSecretFromOtpUrl(otpAuthUrl);

        // Description label
        JLabel codeLabel = new JLabel("Or enter this code manually:");
        codeLabel.setFont(FontLoader.getInter(13f).deriveFont(Font.PLAIN));
        ThemeManager.putThemeAwareProperty(codeLabel, "foreground: $LGVB.foreground");
        codeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        codeLabel.setBorder(new EmptyBorder(10, 0, 5, 0));

        // Copyable text field that looks like a label
        JTextField codeField = new JTextField(code);
        codeField.setForeground(ThemeGlobalDefaults.getColor("LGVB.foreground"));
        codeField.setEditable(false);
        codeField.setHorizontalAlignment(JTextField.CENTER);
        codeField.setFont(FontLoader.getInter(20f).deriveFont(Font.BOLD));
        ThemeManager.putThemeAwareProperty(codeField, "foreground: $LGVB.foreground");
        ThemeManager.putThemeAwareProperty(codeField, "background: $LGVB.background");
        codeField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        codeField.setMaximumSize(new Dimension(300, 40));
        codeField.setAlignmentX(Component.CENTER_ALIGNMENT);
        codeField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        codeField.setFocusable(true);
        codeField.setOpaque(false);


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
        add(codeLabel);
        add(codeField);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(confirmButton);
        add(Box.createVerticalGlue());
    }

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

    private String extractSecretFromOtpUrl(String otpAuthUrl) {
        try {
            String decoded = URLDecoder.decode(otpAuthUrl, StandardCharsets.UTF_8);
            int idx = decoded.indexOf("secret=");
            if (idx != -1) {
                String secretPart = decoded.substring(idx + 7);
                int ampIdx = secretPart.indexOf('&');
                return ampIdx == -1 ? secretPart : secretPart.substring(0, ampIdx);
            }
        } catch (Exception ignored) {
        }
        return "(invalid code)";
    }

    public RoundedButton getConfirmButton() {
        return confirmButton;
    }
}
