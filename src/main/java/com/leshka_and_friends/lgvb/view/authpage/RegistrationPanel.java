package com.leshka_and_friends.lgvb.view.authpage;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.*;
import com.leshka_and_friends.lgvb.view.ui_utils.*;
import raven.datetime.DatePicker;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RegistrationPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    // --- Fields ---
    private RoundedTextField emailField;
    private RoundedPasswordField passwordField;
    private RoundedTextField firstNameField;
    private RoundedTextField lastNameField;
    private RoundedTextField phoneField;
    private RoundedFormattedTextField dobField;
    private JCheckBox termsCheckBox;

    // --- Buttons & labels ---
    private RoundedButton nextButton;
    private RoundedButton backButton;
    private RoundedButton registerButton;
    private JLabel switchToLoginLabel;
    private Runnable onSwitchToLogin;

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

    /** -------------------- PAGE 1: Credentials -------------------- **/
    private JPanel createCredentialsPage() {
        JPanel page = new JPanel(new GridBagLayout());
        page.setOpaque(false);

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new EmptyBorder(UIScale.scale(40), UIScale.scale(50), UIScale.scale(60), UIScale.scale(50)));
        container.setMaximumSize(new Dimension(350, Short.MAX_VALUE));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel header = new JLabel("Register");
        header.setFont(FontLoader.getInter(28f).deriveFont(Font.BOLD));
        ThemeManager.putThemeAwareProperty(header, "foreground: $LGVB.foreground");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        int textFieldArc = ThemeGlobalDefaults.getScaledInt("LoginPage.textField.arc");
        int textFieldHeight = ThemeGlobalDefaults.getScaledInt("RegistrationPage.textField.height");

        // --- Create Email Panel ---
        emailField = createLabeledField(textFieldArc, textFieldHeight);
        JPanel emailPanel = new JPanel();
        emailPanel.setOpaque(false);
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailPanel.setMaximumSize(new Dimension(350, Short.MAX_VALUE));
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(emailLabel, "foreground: $TextField.background");
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailPanel.add(emailLabel);
        emailPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        emailPanel.add(emailField);

        // --- Create Password Panel ---
        passwordField = createLabeledPassword(textFieldArc, textFieldHeight);
        JPanel passwordPanel = new JPanel();
        passwordPanel.setOpaque(false);
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setMaximumSize(new Dimension(350, textFieldHeight * 2));
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(passwordLabel, "foreground: $TextField.background");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        passwordPanel.add(passwordField);

        // --- Buttons ---
        Dimension buttonSize = new Dimension(
                ThemeGlobalDefaults.getScaledInt("RegistrationPage.button.width"),
                ThemeGlobalDefaults.getScaledInt("RegistrationPage.button.height")
        );

        nextButton = new RoundedButton("Next", ThemeGlobalDefaults.getScaledInt("LoginPage.buttonArc"));
        nextButton.setForeground(Color.WHITE);
        nextButton.setBackground(new Color(202, 28, 82));
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.setPreferredSize(buttonSize);
        nextButton.setMaximumSize(buttonSize);

        switchToLoginLabel = new JLabel("<html>Already have an account? <a href=''>Log in instead</a></html>");
        switchToLoginLabel.setFont(FontLoader.getInter(13f));
        ThemeManager.putThemeAwareProperty(switchToLoginLabel, "foreground: $LGVB.foreground");
        switchToLoginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        switchToLoginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchToLoginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (onSwitchToLogin != null) onSwitchToLogin.run();
            }
        });

        // --- Assemble container ---
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

    /** -------------------- PAGE 2: Personal Info -------------------- **/
    private JPanel createPersonalInfoPage() {
        JPanel page = new JPanel(new GridBagLayout());
        page.setOpaque(false);

        final int FORM_WIDTH = 350;
        int textFieldArc = ThemeGlobalDefaults.getScaledInt("LoginPage.textField.arc");
        int textFieldHeight = ThemeGlobalDefaults.getScaledInt("RegistrationPage.textField.height");

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(new EmptyBorder(UIScale.scale(40), UIScale.scale(50), UIScale.scale(60), UIScale.scale(50)));
        container.setMaximumSize(new Dimension(FORM_WIDTH, Short.MAX_VALUE));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel header = new JLabel("Personal Information");
        header.setFont(FontLoader.getInter(24f).deriveFont(Font.BOLD));
        ThemeManager.putThemeAwareProperty(header, "foreground: $LGVB.foreground");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- First Name ---
        firstNameField = createLabeledField(textFieldArc, textFieldHeight);
        JPanel firstNamePanel = new JPanel();
        firstNamePanel.setOpaque(false);
        firstNamePanel.setLayout(new BoxLayout(firstNamePanel, BoxLayout.Y_AXIS));
        firstNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        firstNamePanel.setMaximumSize(new Dimension(FORM_WIDTH, Short.MAX_VALUE));
        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(firstNameLabel, "foreground: $TextField.background");
        firstNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        firstNamePanel.add(firstNameLabel);
        firstNamePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        firstNamePanel.add(firstNameField);

        // --- Last Name ---
        lastNameField = createLabeledField(textFieldArc, textFieldHeight);
        JPanel lastNamePanel = new JPanel();
        lastNamePanel.setOpaque(false);
        lastNamePanel.setLayout(new BoxLayout(lastNamePanel, BoxLayout.Y_AXIS));
        lastNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lastNamePanel.setMaximumSize(new Dimension(FORM_WIDTH, Short.MAX_VALUE));
        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(lastNameLabel, "foreground: $TextField.background");
        lastNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        lastNamePanel.add(lastNameLabel);
        lastNamePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        lastNamePanel.add(lastNameField);

        // --- Phone ---
        phoneField = createLabeledField(textFieldArc, textFieldHeight);
        JPanel phonePanel = new JPanel();
        phonePanel.setOpaque(false);
        phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.Y_AXIS));
        phonePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        phonePanel.setMaximumSize(new Dimension(FORM_WIDTH, Short.MAX_VALUE));
        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(phoneLabel, "foreground: $TextField.background");
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        phonePanel.add(phoneLabel);
        phonePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        phonePanel.add(phoneField);

        // --- DOB ---
        dobField = new RoundedFormattedTextField();
        dobField.setFont(FontLoader.getBaloo2Regular(14f));
        dobField.setCaretColor(ThemeGlobalDefaults.getColor("TextField.caretColor"));
        dobField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textFieldHeight));
        dobField.setPreferredSize(new Dimension(FORM_WIDTH, textFieldHeight));
        dobField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel dobPanel = new JPanel();
        dobPanel.setOpaque(false);
        dobPanel.setLayout(new BoxLayout(dobPanel, BoxLayout.Y_AXIS));
        dobPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dobPanel.setMaximumSize(new Dimension(FORM_WIDTH, Short.MAX_VALUE));
        JLabel dobLabel = new JLabel("Date of Birth");
        dobLabel.setFont(FontLoader.getInter(14f));
        ThemeManager.putThemeAwareProperty(dobLabel, "foreground: $TextField.background");
        dobLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dobPanel.add(dobLabel);
        dobPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        dobPanel.add(dobField);

        DatePicker datePicker = new DatePicker();
        datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED);
        datePicker.setEditor(dobField);
        datePicker.setDateSelectionAble(date -> !date.isAfter(LocalDate.now()));

        // --- Terms ---
        termsCheckBox = new JCheckBox("I have read and agree to LGVB's");
        termsCheckBox.setForeground(Color.WHITE);
        termsCheckBox.setOpaque(false);
        JLabel termsLabel = new JLabel("<html><a href=''>Terms of Service</a> and <a href=''>Privacy Policy</a></html>");
        termsLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
        termsLabel.setForeground(Color.WHITE);
        termsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel termsPanel = new JPanel(new BorderLayout());
        termsPanel.setOpaque(false);
        termsPanel.add(termsCheckBox, BorderLayout.NORTH);
        termsPanel.add(termsLabel, BorderLayout.CENTER);

        // --- Buttons ---
        Dimension buttonSize = new Dimension(
                ThemeGlobalDefaults.getScaledInt("RegistrationPage.button.width"),
                ThemeGlobalDefaults.getScaledInt("RegistrationPage.button.height")
        );

        backButton = new RoundedButton("Back", ThemeGlobalDefaults.getInt("LoginPage.buttonArc"));
        backButton.setBackground(ThemeGlobalDefaults.getColor("AuthPage.buttonSub.background"));
        backButton.setForeground(ThemeGlobalDefaults.getColor("AuthPage.buttonSub.foreground"));
        backButton.setPreferredSize(buttonSize);
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "PAGE1"));

        registerButton = new RoundedButton("Register", ThemeGlobalDefaults.getInt("LoginPage.buttonArc"));
        registerButton.setBackground(ThemeGlobalDefaults.getColor("AuthPage.buttonMain.background"));
        registerButton.setForeground(ThemeGlobalDefaults.getColor("AuthPage.buttonMain.foreground"));
        registerButton.setPreferredSize(buttonSize);
        registerButton.setMaximumSize(buttonSize);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalGlue());

        // --- Assemble container ---
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

    /** -------------------- Helper methods -------------------- **/
    private RoundedTextField createLabeledField(int arc, int height) {
        RoundedTextField tf = new RoundedTextField(arc);
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        tf.setFont(FontLoader.getBaloo2Regular(14f));
        tf.setCaretColor(ThemeGlobalDefaults.getColor("TextField.caretColor"));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        return tf;
    }

    private RoundedPasswordField createLabeledPassword(int arc, int height) {
        RoundedPasswordField pf = new RoundedPasswordField(arc);
        pf.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        pf.setFont(FontLoader.getBaloo2Regular(14f));
        pf.setCaretColor(ThemeGlobalDefaults.getColor("TextField.caretColor"));
        pf.setAlignmentX(Component.LEFT_ALIGNMENT);
        return pf;
    }

    /** -------------------- Public API -------------------- **/
    public void setOnSwitchToLogin(Runnable callback) { this.onSwitchToLogin = callback; }
    public void goToNextPage() { cardLayout.show(cardPanel, "PAGE2"); }

    public RoundedButton getRegisterButton() { return registerButton; }
    public RoundedButton getNextButton() { return nextButton; }
    public RoundedButton getBackButton() { return backButton; }
    public JCheckBox getTermsCheckBox() { return termsCheckBox; }
    public JLabel getSwitchToLoginLabel() { return switchToLoginLabel; }

    // --------- Value getters --------------
    public String getEmail() { return emailField.getText().trim(); }
    public char[] getPassword() { return passwordField.getPassword(); }
    public String getFirstName() { return firstNameField.getText().trim(); }
    public String getLastName() { return lastNameField.getText().trim(); }
    public String getPhoneNumber() {
        // accepts input such as: +639765577846 and 09765577846
        // when the input starts with 0x, it will just replace it with +63, if it starts with +63 or just +, leave it as is
        return phoneField.getText().trim();
    }

    public boolean isTermsChecked() {
        return termsCheckBox.isSelected();
    }
    public LocalDate getDOB() {
        String dateStr = dobField.getText().trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    public void showInvalidColorToField(RoundedTextField field) { field.setInvalid(true); }
}
