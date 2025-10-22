package com.leshka_and_friends.lgvb.view.authpage;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.*;
import com.leshka_and_friends.lgvb.view.ui_utils.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegistrationPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private RoundedTextField emailField;
    private RoundedPasswordField passwordField;
    private RoundedTextField firstNameField;
    private RoundedTextField lastNameField;
    private RoundedTextField phoneField;
    private RoundedTextField dobField;
    private JCheckBox termsCheckBox;
    private RoundedButton nextButton;
    private RoundedButton registerButton;
    private JLabel switchToLoginLabel;

    public RegistrationPanel() {
        setLayout(new BorderLayout());
        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.background");

        // left content (the actual form)
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        cardPanel.add(createCredentialsPage(), "PAGE1");
        cardPanel.add(createPersonalInfoPage(), "PAGE2");

        // right-side image
        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createCredentialsPage() {
        JPanel page = new JPanel();
        page.setOpaque(false);
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setBorder(new EmptyBorder(UIScale.scale(50), UIScale.scale(80), UIScale.scale(50), UIScale.scale(80)));

        JLabel header = new JLabel("Register");
        header.setFont(FontLoader.getInter(28f).deriveFont(Font.BOLD));
        ThemeManager.putThemeAwareProperty(header, "foreground: $LGVB.foreground");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        int textFieldArc = ThemeGlobalDefaults.getScaledInt("LoginPage.textField.arc");
        int textFieldHeight = ThemeGlobalDefaults.getScaledInt("LoginPage.textField.height");

        JLabel emailLabel = new JLabel("Email");
        ThemeManager.putThemeAwareProperty(emailLabel, "foreground: $TextField.background");
        emailLabel.setAlignmentX(LEFT_ALIGNMENT);

        emailField = new RoundedTextField(textFieldArc);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textFieldHeight));
        emailField.setCaretColor(ThemeGlobalDefaults.getColor("TextField.caretColor"));
        emailField.setFont(FontLoader.getBaloo2Regular(14f));

        JLabel passwordLabel = new JLabel("Password");
        ThemeManager.putThemeAwareProperty(passwordLabel, "foreground: $TextField.background");
        passwordLabel.setAlignmentX(LEFT_ALIGNMENT);

        passwordField = new RoundedPasswordField(textFieldArc);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textFieldHeight));
        passwordField.setCaretColor(ThemeGlobalDefaults.getColor("TextField.caretColor"));
        passwordField.setFont(FontLoader.getBaloo2Regular(14f));

        nextButton = new RoundedButton("Next", ThemeGlobalDefaults.getScaledInt("LoginPage.buttonArc"));
        nextButton.setForeground(Color.WHITE);
        nextButton.setBaseColor(new Color(202, 28, 82));
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.addActionListener(e -> cardLayout.show(cardPanel, "PAGE2"));

        switchToLoginLabel = new JLabel("Already have an account? Log in instead");
        switchToLoginLabel.setFont(FontLoader.getInter(13f));
        ThemeManager.putThemeAwareProperty(switchToLoginLabel, "foreground: $LGVB.foreground");
        switchToLoginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        page.add(header);
        page.add(Box.createRigidArea(new Dimension(0, 40)));
        page.add(emailLabel);
        page.add(Box.createRigidArea(new Dimension(0, 5)));
        page.add(emailField);
        page.add(Box.createRigidArea(new Dimension(0, 15)));
        page.add(passwordLabel);
        page.add(Box.createRigidArea(new Dimension(0, 5)));
        page.add(passwordField);
        page.add(Box.createRigidArea(new Dimension(0, 25)));
        page.add(nextButton);
        page.add(Box.createRigidArea(new Dimension(0, 20)));
        page.add(switchToLoginLabel);

        return page;
    }

    // ------------------ Replace createPersonalInfoPage() ------------------
    private JPanel createPersonalInfoPage() {
        JPanel page = new JPanel();
        page.setOpaque(false);
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setBorder(new EmptyBorder(UIScale.scale(50), UIScale.scale(80), UIScale.scale(50), UIScale.scale(80)));

        int textFieldArc = ThemeGlobalDefaults.getScaledInt("LoginPage.textField.arc");
        int textFieldHeight = ThemeGlobalDefaults.getScaledInt("LoginPage.textField.height");

        // createTextField returns a JPanel; it also assigns the instance fields internally
        page.add(createTextField("First Name", textFieldArc, textFieldHeight));
        page.add(Box.createRigidArea(new Dimension(0, 10)));
        page.add(createTextField("Last Name", textFieldArc, textFieldHeight));
        page.add(Box.createRigidArea(new Dimension(0, 10)));
        page.add(createTextField("Phone", textFieldArc, textFieldHeight));
        page.add(Box.createRigidArea(new Dimension(0, 10)));
        page.add(createTextField("Date of Birth", textFieldArc, textFieldHeight));
        page.add(Box.createRigidArea(new Dimension(0, 40)));

        termsCheckBox = new JCheckBox("I agree to the Terms of Service and Privacy Policy");
        termsCheckBox.setOpaque(false);
        ThemeManager.putThemeAwareProperty(termsCheckBox, "foreground: $LGVB.foreground");
        termsCheckBox.setFont(FontLoader.getInter(13f));
        termsCheckBox.setAlignmentX(LEFT_ALIGNMENT);

        registerButton = new RoundedButton("Register", ThemeGlobalDefaults.getScaledInt("LoginPage.buttonArc"));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBaseColor(new Color(0x238636));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        page.add(termsCheckBox);
        page.add(Box.createRigidArea(new Dimension(0, 30)));
        page.add(registerButton);

        return page;
    }

// ------------------ Replace createTextField(...) ------------------
    private JPanel createTextField(String label, int arc, int height) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(lbl, "foreground: $TextField.background");
        lbl.setAlignmentX(LEFT_ALIGNMENT);

        RoundedTextField tf = new RoundedTextField(arc);
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        tf.setFont(FontLoader.getBaloo2Regular(14f));
        tf.setCaretColor(ThemeGlobalDefaults.getColor("TextField.caretColor"));
        tf.setAlignmentX(LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(tf);

        // assign to the instance field so callers can reference tf later
        switch (label) {
            case "First Name":
                firstNameField = tf;
                break;
            case "Last Name":
                lastNameField = tf;
                break;
            case "Phone":
                phoneField = tf;
                break;
            case "Date of Birth":
                dobField = tf;
                break;
            default:
            // nothing
        }

        return panel;
    }

    private JPanel createRightSide() {
        try {
            JPanel imageContainer = new JPanel(new BorderLayout());
            JLabel imageLabel = new JLabel();
            Image scaledImage = new ImageIcon(
                    getClass().getResource("/images/log_in_focal.png"))
                    .getImage()
                    .getScaledInstance(
                            ThemeGlobalDefaults.getScaledInt("LoginPage.focalPhoto.width"),
                            ThemeGlobalDefaults.getScaledInt("LoginPage.focalPhoto.height"),
                            Image.SCALE_SMOOTH
                    );

            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageContainer.add(imageLabel);
            return imageContainer;  
        } catch (Exception ex) {
            System.out.println("[RegistrationPanel] Error in creating right side: " + ex.getMessage());
        }
        
        return null;
    }

    // getters for later use
    public RoundedButton getRegisterButton() {
        return registerButton;
    }

    public RoundedButton getNextButton() {
        return nextButton;
    }

    public JCheckBox getTermsCheckBox() {
        return termsCheckBox;
    }

    public JLabel getSwitchToLoginLabel() {
        return switchToLoginLabel;
    }
}
