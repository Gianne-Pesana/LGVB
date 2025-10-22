package com.leshka_and_friends.lgvb.view.authpage;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.*;
import com.leshka_and_friends.lgvb.view.ui_utils.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private RoundedButton backButton;
    private RoundedButton registerButton;
    private JLabel switchToLoginLabel;

    public RegistrationPanel() {
        setLayout(new BorderLayout());
        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.background");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        cardPanel.add(createCredentialsPage(), "PAGE1");
        cardPanel.add(createPersonalInfoPage(), "PAGE2");

        add(cardPanel, BorderLayout.CENTER);
    }

    /**
     * -------------------- PAGE 1: Credentials -------------------- *
     */
    private JPanel createCredentialsPage() {
        JPanel page = new JPanel(new GridBagLayout());
        page.setOpaque(false);

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new EmptyBorder(UIScale.scale(40), UIScale.scale(50), UIScale.scale(60), UIScale.scale(50)));
        container.setMaximumSize(new Dimension(350, Short.MAX_VALUE)); // Keep consistent width with login page
        container.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel header = new JLabel("Register");
        header.setFont(FontLoader.getInter(28f).deriveFont(Font.BOLD));
        ThemeManager.putThemeAwareProperty(header, "foreground: $LGVB.foreground");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        int textFieldArc = ThemeGlobalDefaults.getScaledInt("LoginPage.textField.arc");
        int textFieldHeight = ThemeGlobalDefaults.getScaledInt("RegistrationPage.textField.height");

        // Email
        JPanel emailPanel = createLabeledField("Email", textFieldArc, textFieldHeight);
        emailField = (RoundedTextField) emailPanel.getComponent(2);

        // Password
        JPanel passwordPanel = createLabeledPassword("Password", textFieldArc, textFieldHeight);
        passwordField = (RoundedPasswordField) passwordPanel.getComponent(2);

        nextButton = new RoundedButton("Next", ThemeGlobalDefaults.getScaledInt("LoginPage.buttonArc"));
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(new Color(202, 28, 82));
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.addActionListener(e -> cardLayout.show(cardPanel, "PAGE2"));

        switchToLoginLabel = new JLabel(
                "<html>Already have an account? <a href=''>Log in instead</a></html>"
        );
        switchToLoginLabel.setFont(FontLoader.getInter(13f));
        ThemeManager.putThemeAwareProperty(switchToLoginLabel, "foreground: $LGVB.foreground");
        switchToLoginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        switchToLoginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        switchToLoginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Trigger your login switch action here
                System.out.println("Switch to login clicked!");
            }
        });

        container.add(header);
        container.add(Box.createRigidArea(new Dimension(0, 40)));
        container.add(emailPanel);
        container.add(Box.createRigidArea(new Dimension(0, 15)));
        container.add(passwordPanel);
        container.add(Box.createRigidArea(new Dimension(0, 25)));
        container.add(nextButton);
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        container.add(switchToLoginLabel);

        page.add(container);
        return page;
    }

// Helper: Label + TextField
    private JPanel createLabeledField(String label, int arc, int height) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(350, Short.MAX_VALUE)); // Keep consistent width

        JLabel lbl = new JLabel(label);
        lbl.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(lbl, "foreground: $TextField.background");
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedTextField tf = new RoundedTextField(arc);
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        tf.setFont(FontLoader.getBaloo2Regular(14f));
        tf.setCaretColor(ThemeGlobalDefaults.getColor("TextField.caretColor"));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(tf);
        return panel;
    }

// Helper: Label + PasswordField
    private JPanel createLabeledPassword(String label, int arc, int height) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(350, height * 2));

        JLabel lbl = new JLabel(label);
        lbl.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(lbl, "foreground: $TextField.background");
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedPasswordField pf = new RoundedPasswordField(arc);
        pf.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        pf.setFont(FontLoader.getBaloo2Regular(14f));
        pf.setCaretColor(ThemeGlobalDefaults.getColor("TextField.caretColor"));
        pf.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(pf);
        return panel;
    }

    /**
     * -------------------- PAGE 2: Personal Info -------------------- *
     */
    private JPanel createPersonalInfoPage() {
        JPanel page = new JPanel(new GridBagLayout());
        page.setOpaque(false);

        final int FORM_WIDTH = 350;

        // --- Container ---
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new EmptyBorder(UIScale.scale(40), UIScale.scale(50), UIScale.scale(60), UIScale.scale(50)));
        container.setMaximumSize(new Dimension(FORM_WIDTH, Short.MAX_VALUE));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);

        int textFieldArc = ThemeGlobalDefaults.getScaledInt("LoginPage.textField.arc");
        int textFieldHeight = ThemeGlobalDefaults.getScaledInt("RegistrationPage.textField.height");

        // --- Header ---
        JLabel header = new JLabel("Personal Information");
        header.setFont(FontLoader.getInter(24f).deriveFont(Font.BOLD));
        ThemeManager.putThemeAwareProperty(header, "foreground: $LGVB.foreground");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Input Panels ---
        JPanel firstNamePanel = createLabeledField("First Name", textFieldArc, textFieldHeight);
        firstNameField = (RoundedTextField) firstNamePanel.getComponent(2);

        JPanel lastNamePanel = createLabeledField("Last Name", textFieldArc, textFieldHeight);
        lastNameField = (RoundedTextField) lastNamePanel.getComponent(2);

        JPanel phonePanel = createLabeledField("Phone", textFieldArc, textFieldHeight);
        phoneField = (RoundedTextField) phonePanel.getComponent(2);

        JPanel dobPanel = createLabeledField("Date of Birth", textFieldArc, textFieldHeight);
        dobField = (RoundedTextField) dobPanel.getComponent(2);

        // Terms panel
        JPanel termsPanel = new JPanel(new BorderLayout());
        termsPanel.setOpaque(false);

        JCheckBox agreeCheckBox = new JCheckBox("I have read and agree to LGVB's");
        agreeCheckBox.setForeground(Color.WHITE);
        agreeCheckBox.setOpaque(false);

// Add some space between checkbox and label
        agreeCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel termsLabel = new JLabel(
                "<html><a href=''>Terms of Service</a> and <a href=''>Privacy Policy</a></html>"
        );
        termsLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0)); // top, left, bottom, right
        termsLabel.setForeground(Color.WHITE);
        termsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        termsLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // align left

        termsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Hyperlink clicked!");
            }
        });

// Add components vertically
        termsPanel.add(agreeCheckBox, BorderLayout.NORTH);
        termsPanel.add(termsLabel, BorderLayout.CENTER);

// Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);

        Dimension buttonSize = new Dimension(
                ThemeGlobalDefaults.getScaledInt("RegistrationPage.button.width"),
                ThemeGlobalDefaults.getScaledInt("RegistrationPage.button.height")
        );

        backButton = new RoundedButton("Back", ThemeGlobalDefaults.getInt("LoginPage.buttonArc"));
        backButton.setBackground(ThemeGlobalDefaults.getColor("AuthPage.buttonSub.background"));
        backButton.setForeground(ThemeGlobalDefaults.getColor("AuthPage.buttonSub.foreground"));
        backButton.setPreferredSize(buttonSize);
//        backButton.setMaximumSize(buttonSize);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "PAGE1"));

        RoundedButton registerButton = new RoundedButton("Register", ThemeGlobalDefaults.getInt("LoginPage.buttonArc"));
        registerButton.setBackground(ThemeGlobalDefaults.getColor("AuthPage.buttonMain.background"));
        registerButton.setForeground(ThemeGlobalDefaults.getColor("AuthPage.buttonMain.foreground"));
        registerButton.setPreferredSize(buttonSize);
        registerButton.setMaximumSize(buttonSize);

// Optional: Add a small horizontal space between them
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalGlue());

// Bottom panel to contain terms + buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);

        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(termsPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(buttonPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // --- Assemble ---
        container.add(header);
        container.add(Box.createRigidArea(new Dimension(0, 30)));
        container.add(firstNamePanel);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(lastNamePanel);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(phonePanel);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(dobPanel);
        container.add(Box.createRigidArea(new Dimension(0, 25)));
        container.add(termsPanel);
        container.add(Box.createRigidArea(new Dimension(0, 30)));
        container.add(buttonPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        page.add(container, gbc);

        return page;
    }

    /**
     * Updated helper that accepts a target width so the panel never expands
     * past it.
     */
    private JPanel createTextField(String label, int arc, int height, int targetWidth) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // IMPORTANT: fix the panel width to the form width so tf fills that width.
        panel.setMaximumSize(new Dimension(targetWidth, height * 2));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(lbl, "foreground: $TextField.background");
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedTextField tf = new RoundedTextField(arc);
        // allow tf to fill the panel width, but the panel width is bounded by targetWidth
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        tf.setFont(FontLoader.getBaloo2Regular(14f));
        tf.setCaretColor(ThemeGlobalDefaults.getColor("TextField.caretColor"));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(tf);

        // keep references if needed
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
        }
        return panel;
    }

    /**
     * -------------------- Getters -------------------- *
     */
    public RoundedButton getRegisterButton() {
        return registerButton;
    }

    public RoundedButton getNextButton() {
        return nextButton;
    }

    public RoundedButton getBackButton() {
        return backButton;
    }

    public JCheckBox getTermsCheckBox() {
        return termsCheckBox;
    }

    public JLabel getSwitchToLoginLabel() {
        return switchToLoginLabel;
    }
}
