/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view;

import com.leshka_and_friends.lgvb.view.components.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * @author giann
 */
public class LoginPage extends JFrame {
    
    public RoundedTextField usernameField;
    public RoundedPasswordField passwordField;
    public RoundedButton loginBtn;
    public RoundedButton registerBtn;

    public LoginPage() {
        setSize(800, 540);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));
        setResizable(false);

        Image image = new ImageIcon(getClass().getResource("/icons/app_icon.png")).getImage();
        setIconImage(image);
        setTitle("LGVB Digital Banking");

        add(createLeftSide());
        add(createRightSide());

        setLocationRelativeTo(null);
    }

    private JPanel createLeftSide() {
        JPanel loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(400, 540));
        loginPanel.setBackground(new Color(17, 17, 51));
        loginPanel.setLayout(new BorderLayout());

        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        logoPanel.setOpaque(false);
        logoPanel.setBorder(new EmptyBorder(0, 20, 0, 0));

        JLabel logoLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Logo/logo-white.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaledImage));

        logoPanel.add(logoLabel, BorderLayout.CENTER);

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new GridBagLayout());
        container.setBorder(new EmptyBorder(0, 50, 0, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;  // fill horizontally if needed
        gbc.weighty = 1;  // space to push vertically
        gbc.fill = GridBagConstraints.HORIZONTAL; // FILL HORIZONTALLY
        gbc.anchor = GridBagConstraints.CENTER; // center in the available space
        // add some top margin to move it up
        gbc.insets = new Insets(-50, 0, 0, 0);

        container.add(createLoginContainer(), gbc);

        loginPanel.add(logoPanel, BorderLayout.NORTH);
        loginPanel.add(container, BorderLayout.CENTER);
        return loginPanel;
    }

    private JPanel createLoginContainer() {
        JPanel loginContainer = new JPanel();
        loginContainer.setOpaque(false);
        loginContainer.setLayout(new BoxLayout(loginContainer, BoxLayout.Y_AXIS));
        loginContainer.setBorder(new EmptyBorder(10, 10, 10, 10));
//        loginContainer.setPreferredSize(new Dimension(280, 300));
//        loginContainer.setMaximumSize(new Dimension(280, Integer.MAX_VALUE));

        JLabel header = new JLabel("Login");
        header.setFont(new Font("Inter", Font.PLAIN, 30));
        header.setForeground(Color.WHITE);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ---- text fields ----
        JPanel textFields = new JPanel();
        textFields.setLayout(new BoxLayout(textFields, BoxLayout.Y_AXIS));
        textFields.setOpaque(false);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Inter", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(LEFT_ALIGNMENT);

        int textFieldHeight = 30;
        usernameField = new RoundedTextField(25);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textFieldHeight));
        usernameField.setAlignmentX(LEFT_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Inter", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setAlignmentX(LEFT_ALIGNMENT);

        passwordField = new RoundedPasswordField(25);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textFieldHeight));
        passwordField.setAlignmentX(LEFT_ALIGNMENT);

        textFields.add(usernameLabel);
        textFields.add(Box.createRigidArea(new Dimension(0, 5)));
        textFields.add(usernameField);
        textFields.add(Box.createRigidArea(new Dimension(0, 15)));
        textFields.add(passwordLabel);
        textFields.add(Box.createRigidArea(new Dimension(0, 5)));
        textFields.add(passwordField);
        textFields.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ---- buttons ----
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(new EmptyBorder(0, 30, 0, 30));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(Integer.MAX_VALUE, 30);

        loginBtn = new RoundedButton("Login", 30);
        
        loginBtn.setPreferredSize(buttonSize);
        loginBtn.setMaximumSize(buttonSize);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBaseColor(new Color(202, 28, 82));
        loginBtn.setFont(new Font("Inter", Font.PLAIN, 14));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerBtn = new RoundedButton("Register", 30);
        
        registerBtn.setPreferredSize(buttonSize);
        registerBtn.setMaximumSize(buttonSize);
        registerBtn.setForeground(new Color(23, 80, 110));
        registerBtn.setBaseColor(new Color(245, 247, 250));
        registerBtn.setFont(new Font("Inter", Font.PLAIN, 14));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonsPanel.add(loginBtn);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonsPanel.add(registerBtn);

        // ---- add all to loginContainer ----
        loginContainer.add(header);
        loginContainer.add(Box.createRigidArea(new Dimension(0, 30))); // space between header and fields
        loginContainer.add(textFields);
        loginContainer.add(Box.createRigidArea(new Dimension(0, 30))); // space between fields and buttons
        loginContainer.add(buttonsPanel);

        return loginContainer;
    }

    private JPanel createRightSide() {
        JPanel imageContainer = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel();
        Image scaledImage = new ImageIcon(
                getClass().getResource("/images/log_in_focal.png"))
                .getImage()
                .getScaledInstance(420, 567, Image.SCALE_SMOOTH);

        imageLabel.setIcon(new ImageIcon(scaledImage));

        imageContainer.add(imageLabel);
        return imageContainer;
    }
    
    public String getInputUsername() {
        return usernameField.getText().trim();
    }
    
    public char[] getInputPassword() {
        return passwordField.getPassword();
    }
}
