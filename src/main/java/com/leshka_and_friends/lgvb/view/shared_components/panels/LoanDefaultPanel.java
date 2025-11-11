package com.leshka_and_friends.lgvb.view.shared_components.panels;

import com.formdev.flatlaf.util.UIScale;
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

    private JPanel initComponent() {
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
        layeredPane.setPreferredSize(new Dimension(UIScale.scale(width), UIScale.scale(height)));
        layeredPane.setLayout(null);

        JLabel backgroundLabel = new JLabel();
        Image scaledImage = new ImageIcon(getClass().getResource("/images/loan_panel_photo.png"))
                .getImage()
                .getScaledInstance(UIScale.scale(width), UIScale.scale(height), Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, UIScale.scale(width), UIScale.scale(height));
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        loanButtonPanel = new LoanButtonPanel("Apply Now", true);
        loanButtonPanel.setClickListener(() -> {
            if (listener != null) listener.onApply();
        });

        int btnWidth = ThemeGlobalDefaults.getScaledInt("Loan.button.size.width");
        int btnHeight = ThemeGlobalDefaults.getScaledInt("Loan.button.size.height");
        int leftMargin = UIScale.scale(20);
        int bottomMargin = UIScale.scale(20);

        loanButtonPanel.setBounds(leftMargin, UIScale.scale(height) - UIScale.scale(btnHeight) - bottomMargin,
                UIScale.scale(btnWidth), UIScale.scale(btnHeight));
        layeredPane.add(loanButtonPanel, Integer.valueOf(1));

        layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int paneHeight = backgroundLabel.getHeight();
                loanButtonPanel.setBounds(leftMargin + UIScale.scale(8),
                        paneHeight - UIScale.scale(btnHeight) - UIScale.scale(51),
                        UIScale.scale(btnWidth), UIScale.scale(btnHeight));
            }
        });

        return layeredPane;
    }

    private JPanel createMiddlePanel() {
        JLabel lgvbLabel = new JLabel("LGVB Offers");
        lgvbLabel.setFont(FontLoader.getBaloo2SemiBold(30f));
        lgvbLabel.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        lgvbLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        int sepWidth = UIScale.scale(600);
        int sepHeight = UIScale.scale(2);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(ThemeGlobalDefaults.getColor("Separator.color"));
        separator.setMaximumSize(new Dimension(sepWidth, sepHeight));
        separator.setPreferredSize(new Dimension(sepWidth, sepHeight));
        separator.setMinimumSize(new Dimension(sepWidth, sepHeight));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setForeground(ThemeGlobalDefaults.getColor("Separator.color"));
        separator1.setMaximumSize(new Dimension(sepWidth, sepHeight));
        separator1.setPreferredSize(new Dimension(sepWidth, sepHeight));
        separator1.setMinimumSize(new Dimension(sepWidth, sepHeight));
        separator1.setAlignmentX(Component.CENTER_ALIGNMENT);

        int width = 200;
        int height = 150;
        try {
            width = ThemeGlobalDefaults.getScaledInt("Loan.photo.type.width");
            height = ThemeGlobalDefaults.getScaledInt("Loan.photo.type.height");
        } catch (Exception e) {
            System.out.println("Warning: Loan photo size not defined, using defaults");
        }

        JPanel imageContainer = new JPanel();
        imageContainer.setOpaque(false);
        imageContainer.setLayout(new BoxLayout(imageContainer, BoxLayout.X_AXIS));
        imageContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] imageNames = {"car_loan.png", "hourse_loan.png", "personal_loan.png"};
        for (String name : imageNames) {
            JLabel imageLabel = new JLabel();
            java.net.URL imgUrl = getClass().getResource("/images/" + name);
            if (imgUrl != null) {
                Image scaledImage = new ImageIcon(imgUrl)
                        .getImage()
                        .getScaledInstance(UIScale.scale(width), UIScale.scale(height), Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                System.out.println("Warning: Image not found: " + name);
                imageLabel.setText(name);
            }
            imageLabel.setAlignmentY(Component.TOP_ALIGNMENT);
            imageContainer.add(imageLabel);
            imageContainer.add(Box.createRigidArea(new Dimension(UIScale.scale(20), 0)));
        }

        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.add(Box.createRigidArea(new Dimension(0, UIScale.scale(20))));
        mainContainer.add(lgvbLabel);
        mainContainer.add(Box.createRigidArea(new Dimension(0, UIScale.scale(10))));
        mainContainer.add(separator1);
        mainContainer.add(Box.createRigidArea(new Dimension(0, UIScale.scale(20))));
        mainContainer.add(imageContainer);
        mainContainer.add(Box.createRigidArea(new Dimension(0, UIScale.scale(20))));
        mainContainer.add(separator);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(mainContainer);

        return wrapper;
    }
}
