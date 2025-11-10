/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.authpage;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedButton;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedTextField;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.TextFieldUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author giann
 */
public class TOTPpanel extends JPanel {

    private JPanel totpPanelWrapper;
    private RoundedTextField totpField;
    private RoundedButton totpVerifyBtn;

    public TOTPpanel() {
        setLayout(new GridBagLayout());
        this.setOpaque(false);
        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.background");

        JPanel totpPanel = new JPanel();
        totpPanel.setOpaque(false);
        totpPanel.setLayout(new BoxLayout(totpPanel, BoxLayout.Y_AXIS));
        totpPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        ThemeManager.putThemeAwareProperty(totpPanel, "background: $LGVB.background");

        JLabel logoLabel = new JLabel();
        FlatSVGIcon logoIcon = SVGUtils.loadIcon(
                ThemeGlobalDefaults.getString("Logo.noText.path"),
                ThemeGlobalDefaults.getScaledInt("LoginPage.2fa.logo.height")
        );
        logoIcon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        logoLabel.setIcon(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel header = new JLabel("Two-Factor Authentication");
        header.setFont(FontLoader.getInter(24f).deriveFont(Font.BOLD));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel body = new JLabel("Enter the 6-digit code from your Authenticator app");
        body.setFont(FontLoader.getInter(14f));
        body.setAlignmentX(Component.CENTER_ALIGNMENT);

        totpField = new RoundedTextField(ThemeGlobalDefaults.getInt("LoginPage.textField.arc"));
        totpField.setMaximumSize(new Dimension(200, 45));
        totpField.setFont(FontLoader.getBaloo2SemiBold(18f));
        totpField.setHorizontalAlignment(JTextField.CENTER);
        totpField.setCaretColor(ThemeGlobalDefaults.getColor("Caret.color"));
        TextFieldUtils.restrictToDigits(totpField, 6);

        Dimension buttonSize = new Dimension(
                ThemeGlobalDefaults.getScaledInt("TOTP.button.width"),
                ThemeGlobalDefaults.getScaledInt("TOTP.button.height")
        );
        
        totpVerifyBtn = new RoundedButton("Verify", ThemeGlobalDefaults.getInt("LoginPage.2fa.button.arc"));
        totpVerifyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        totpVerifyBtn.setPreferredSize(buttonSize);
        totpVerifyBtn.setMaximumSize(buttonSize);
        totpVerifyBtn.setFont(FontLoader.getInter(14f));
        totpVerifyBtn.setForeground(ThemeGlobalDefaults.getColor("LoginPage.2fa.button.foreground"));
        ThemeManager.putThemeAwareProperty(totpVerifyBtn, "background: $LoginPage.2fa.button.background");

        totpPanel.add(logoLabel);
        totpPanel.add(Box.createVerticalStrut(30));
        totpPanel.add(header);
        totpPanel.add(Box.createVerticalStrut(5));
        totpPanel.add(body);
        totpPanel.add(Box.createVerticalStrut(30));
        totpPanel.add(totpField);
        totpPanel.add(Box.createVerticalStrut(20));
        totpPanel.add(totpVerifyBtn);

        this.add(totpPanel);
    }

    public RoundedTextField getTotpField() {
        return totpField;
    }

    public RoundedButton getTotpVerifyButton() {
        return totpVerifyBtn;
    }
}
