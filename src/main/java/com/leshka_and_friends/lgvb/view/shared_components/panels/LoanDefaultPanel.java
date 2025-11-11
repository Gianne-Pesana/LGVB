package com.leshka_and_friends.lgvb.view.shared_components.panels;

import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedPanel;
import com.leshka_and_friends.lgvb.view.shared_components.buttons.LoanButtonPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;

import javax.swing.*;
import java.awt.*;

public class LoanDefaultPanel extends RoundedPanel {
    protected LoanButtonPanel loanButtonPanel;
    private OnApplyListener listener;


    public interface OnApplyListener {
        void onApply();
    }

    public void setOnApplyListener(OnApplyListener listener) {
        this.listener = listener;
    }

    public LoanDefaultPanel() {
        setLayout(new BorderLayout());
        add(initComponent(), BorderLayout.CENTER);

    }

    private JPanel initComponent(){
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(createTopPanel());
        container.add(createMiddlePanel());

        return container;
    }

    private JLayeredPane createTopPanel() {
        int width = ThemeGlobalDefaults.getScaledInt("Loan.photo.width");
        int height = ThemeGlobalDefaults.getScaledInt("Loan.photo.height");

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));
        layeredPane.setLayout(null);

        JLabel backgroundLabel = new JLabel();
        Image scaledImage = new ImageIcon(getClass().getResource("/images/loan_panel_photo.png"))
                .getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, width, height);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // === Button ===
        loanButtonPanel = new LoanButtonPanel("Apply Now", true);

        // Here’s where we hook the click to the listener
        loanButtonPanel.setClickListener(() -> {
            if (listener != null) {
                listener.onApply(); // Triggers whatever you define in LoanDefault
            }
        });

        int btnWidth = ThemeGlobalDefaults.getScaledInt("Loan.button.size.width");
        int btnHeight = ThemeGlobalDefaults.getScaledInt("Loan.button.size.height");
        int leftMargin = 20;
        loanButtonPanel.setBounds(leftMargin, height - btnHeight - 20, btnWidth, btnHeight);
        layeredPane.add(loanButtonPanel, Integer.valueOf(1));

        layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int paneHeight = backgroundLabel.getHeight();
                loanButtonPanel.setBounds(leftMargin + 8, paneHeight - btnHeight - 51, btnWidth, btnHeight);
            }
        });



        return layeredPane;
    }

    private JPanel createMiddlePanel() {
        // Label for the section
        JLabel lgvbLabel = new JLabel("LGVB Offers");
        lgvbLabel.setFont(FontLoader.getBaloo2SemiBold(30f));
        lgvbLabel.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        lgvbLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally

        // Separator
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(ThemeGlobalDefaults.getColor("Separator.color"));
        separator.setMaximumSize(new Dimension(600, 2)); // width 600 px, height 2 px
        separator.setPreferredSize(new Dimension(600, 2));
        separator.setMinimumSize(new Dimension(600, 2));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally

        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setForeground(ThemeGlobalDefaults.getColor("Separator.color"));
        separator1.setMaximumSize(new Dimension(600, 2)); // width 600 px, height 2 px
        separator1.setPreferredSize(new Dimension(600, 2));
        separator1.setMinimumSize(new Dimension(600, 2));
        separator1.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally

        // Default width/height for images
        int width = 200;
        int height = 150;

        try {
            width = ThemeGlobalDefaults.getScaledInt("Loan.photo.type.width");
            height = ThemeGlobalDefaults.getScaledInt("Loan.photo.type.height");
        } catch (Exception e) {
            System.out.println("Warning: Loan photo size not defined, using defaults");
        }

        // Container for the images row
        JPanel imageContainer = new JPanel();
        imageContainer.setOpaque(false);
        imageContainer.setLayout(new BoxLayout(imageContainer, BoxLayout.X_AXIS));
        imageContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Image names
        String[] imageNames = {"car_loan.png", "hourse_loan.png", "personal_loan.png"};

        for (String name : imageNames) {
            JLabel imageLabel = new JLabel();
            java.net.URL imgUrl = getClass().getResource("/images/" + name);

            if (imgUrl != null) {
                Image scaledImage = new ImageIcon(imgUrl)
                        .getImage()
                        .getScaledInstance(width, height, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                System.out.println("Warning: Image not found: " + name);
                imageLabel.setText(name); // fallback text
            }

            imageLabel.setAlignmentY(Component.TOP_ALIGNMENT);
            imageContainer.add(imageLabel);
            imageContainer.add(Box.createRigidArea(new Dimension(20, 0))); // spacing
        }

        // Main vertical container (label + separator + images)
        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.add(Box.createRigidArea(new Dimension(0, 20))); // top spacing
        mainContainer.add(lgvbLabel);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 10))); // spacing between label and separator
        mainContainer.add(separator1);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 20))); // spacing between separator and images
        mainContainer.add(imageContainer);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        mainContainer.add(separator);// bottom spacing

        // Wrapper panel to center everything in parent
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(mainContainer);

        return wrapper;
    }



}

