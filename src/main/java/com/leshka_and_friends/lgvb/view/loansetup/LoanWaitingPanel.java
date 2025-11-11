package com.leshka_and_friends.lgvb.view.loansetup;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedTextField;
import com.leshka_and_friends.lgvb.view.shared_components.buttons.LoanButtonPanel;
import com.leshka_and_friends.lgvb.view.shared_components.panels.LoanHeaderPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class LoanWaitingPanel extends JPanel {

    private LoanHeaderPanel header;
    private LoanButtonPanel checkEligibilityButton;
    private java.util.function.Consumer<LoanState> stateChangeListener;

    private final int FIELD_WIDTH = 350;
    private final int FIELD_HEIGHT = 45;
    private final int H_GAP = 20;
    private final int V_GAP = 5;

    public LoanWaitingPanel() {
        ThemeManager.putThemeAwareProperty(this, "background: #F0F4FF");
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        setLayout(new BorderLayout());
        setOpaque(false);
        initComponents();
    }

    public void setStateChangeListener(java.util.function.Consumer<LoanState> listener) {
        this.stateChangeListener = listener;
    }

    private void initComponents() {
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(createHeader());
        content.add(Box.createRigidArea(new Dimension(0, 5)));

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(ThemeGlobalDefaults.getColor("Separator.color"));
        content.add(separator);

        add(content, BorderLayout.NORTH);
        add(createMiddlePanel(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel container = new JPanel(new BorderLayout());
        header = new LoanHeaderPanel("LGVB Loan");
        container.add(header, BorderLayout.CENTER);
        container.setOpaque(false);
        ThemeManager.putThemeAwareProperty(container, "background: #F0F4FF");
        return container;
    }

    private JPanel createMiddlePanel() {
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(15, 100, 5, 100));

        JLabel titleLabel = new JLabel("Confirm the following information");
        titleLabel.setFont(FontLoader.getBaloo2SemiBold(20f));
        titleLabel.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        container.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(V_GAP, H_GAP, V_GAP, H_GAP);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        row = addFullWidthField(formPanel, gbc, row, "Account Number");
        row = addTwoColumnFields(formPanel, gbc, row, "First Name", "Last Name");
        row = addFullWidthField(formPanel, gbc, row, "Birth Date");
        row = addTwoColumnFields(formPanel, gbc, row, "Contact Number", "Email");
        row = addFullWidthField(formPanel, gbc, row, "Loan Amount");
        row = addTwoColumnFields(formPanel, gbc, row, "Loan Purpose", "Installment Plan");

        container.add(formPanel, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonContainer.setOpaque(false);

        checkEligibilityButton = new LoanButtonPanel("Check Eligibility", true);
        checkEligibilityButton.putClientProperty("FlatLaf.style",
                "background: #B03060;" +
                        "foreground: #FFFFFF;" +
                        "hoverBackground: #C74878;" +
                        "pressedBackground: #991D4C;" +
                        "font: bold 16px;" +
                        "arc: 20;" +
                        "focusWidth: 0;");
        checkEligibilityButton.setPreferredSize(new Dimension(UIScale.scale(200), UIScale.scale(30)));

        // FIX: Now properly adds a click listener that triggers state change
        checkEligibilityButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (stateChangeListener != null) {
                    stateChangeListener.accept(LoanState.APPROVED);
                }
            }
        });

        buttonContainer.add(checkEligibilityButton);
        container.add(buttonContainer, BorderLayout.SOUTH);

        return container;
    }

    private int addFullWidthField(JPanel panel, GridBagConstraints gbc, int row, String labelText) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        JLabel label = createLabel(labelText);
        panel.add(label, gbc);

        gbc.gridy = ++row;
        RoundedTextField field = createTextField();
        panel.add(field, gbc);

        gbc.gridwidth = 1;
        return ++row;
    }

    private int addTwoColumnFields(JPanel panel, GridBagConstraints gbc, int row, String label1Text, String label2Text) {
        gbc.gridy = row;
        gbc.gridx = 0;
        panel.add(createLabel(label1Text), gbc);

        gbc.gridx = 1;
        panel.add(createLabel(label2Text), gbc);

        gbc.gridy = ++row;
        gbc.gridx = 0;
        panel.add(createTextField(), gbc);

        gbc.gridx = 1;
        panel.add(createTextField(), gbc);

        return ++row;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FontLoader.getBaloo2SemiBold(18f));
        label.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        return label;
    }

    private RoundedTextField createTextField() {
        RoundedTextField textField = new RoundedTextField(15);
        textField.setPreferredSize(new Dimension(UIScale.scale(FIELD_WIDTH), UIScale.scale(FIELD_HEIGHT)));
        textField.setMaximumSize(new Dimension(UIScale.scale(FIELD_WIDTH), UIScale.scale(FIELD_HEIGHT)));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.setBackgroundColor(new Color(230, 230, 235));
        textField.setBorderColor(new Color(230, 230, 235));
        textField.setRadius(10);
        textField.setEditable(false);
        return textField;
    }
}
