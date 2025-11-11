package com.leshka_and_friends.lgvb.view.loansetup;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedComboBox;
import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedTextField;
import com.leshka_and_friends.lgvb.view.shared_components.buttons.LoanButtonPanel;
import com.leshka_and_friends.lgvb.view.shared_components.panels.LoanHeaderPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class LoanAppliedPanel extends JPanel {

    private LoanHeaderPanel header;
    private RoundedTextField loanAmountTxt;
    private RoundedComboBox installmentComboBox;
    private RoundedComboBox loanTypeComboBox;
    private LoanButtonPanel submitButton;

    private String[] loanTerms = { "3 months", "12 months", "24 months" };
    private String[] loanTypes = { "Personal Loan", "Housing Loan", "Car Loan" };

    private OnStateChangeListener stateListener;

    public interface OnStateChangeListener {
        void onStateChange(LoanState newState);
    }

    public void setOnStateChangeListener(OnStateChangeListener listener) {
        this.stateListener = listener;
    }

    public LoanAppliedPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        setLayout(new BorderLayout());
        initComponents();
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
        ThemeManager.putThemeAwareProperty(container, "background: $LGVB.primary");
        return container;
    }

    private JPanel createMiddlePanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(15, 70, 5, 0));
        container.setOpaque(false);

        JLabel label = new JLabel("Complete the following information");
        label.setFont(FontLoader.getBaloo2SemiBold(20f));
        label.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        container.add(label, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 25, 50));

        JLabel loanAmountLabel = new JLabel("Enter Loan Amount");
        loanAmountLabel.setFont(FontLoader.getBaloo2SemiBold(18f));
        loanAmountLabel.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        loanAmountTxt = createTextField();

        JLabel installmentLabel = new JLabel("Select Installment Plan");
        installmentLabel.setFont(FontLoader.getBaloo2SemiBold(18f));
        installmentLabel.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        installmentComboBox = createComboBox(loanTerms);

        JLabel loanTypeLabel = new JLabel("Select Loan Type");
        loanTypeLabel.setFont(FontLoader.getBaloo2SemiBold(18f));
        loanTypeLabel.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        loanTypeComboBox = createComboBox(loanTypes);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // transparent background
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // center horizontally
        submitButton = new LoanButtonPanel("SUBMIT", true);

// Override button colors
        submitButton = new LoanButtonPanel("Submit", true);

// FlatLaf-aware styling using your properties
        submitButton.putClientProperty("FlatLaf.style",
                "background: $LoanApplied.submit.background;" +
                        "foreground: $LoanApplied.submit.foreground;" +
                        "hoverBackground: $LoanApplied.submit.hover;" +
                        "pressedBackground: $LoanApplied.submit.pressed;" +
                        "font: bold 16px;" +
                        "focusWidth: 0;");


        submitButton.setClickListener(() -> {
            if (stateListener != null) {
                stateListener.onStateChange(LoanState.WAITING_APPROVAL);
            }
        });
        submitButton.setClickListener(() -> {
            if (stateListener != null) {
                stateListener.onStateChange(LoanState.WAITING_APPROVAL);
            }
        });
        submitButton.setPreferredSize(new Dimension(UIScale.scale(100), UIScale.scale(30)));
        submitButton.setMaximumSize(new Dimension(UIScale.scale(100), UIScale.scale(30)));




        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        formPanel.add(loanAmountLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(loanAmountTxt);

        formPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        formPanel.add(installmentLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(installmentComboBox);

        formPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        formPanel.add(loanTypeLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(loanTypeComboBox);

        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        buttonPanel.add(submitButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        formPanel.add(buttonPanel);

        container.add(formPanel, BorderLayout.CENTER);

        return container;
    }

    private RoundedComboBox createComboBox(String[] options) {
        RoundedComboBox comboBox = new RoundedComboBox();
        for (String option : options) {
            comboBox.addItem(option);
        }
        comboBox.setPreferredSize(new Dimension(UIScale.scale(400), UIScale.scale(40)));
        comboBox.setMaximumSize(new Dimension(UIScale.scale(400), UIScale.scale(40)));
        comboBox.putClientProperty("JComponent.roundRect", true);
        comboBox.setFont(FontLoader.getBaloo2SemiBold(15f));
        comboBox.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        return comboBox;
    }

    private RoundedTextField createTextField() {
        RoundedTextField textField = new RoundedTextField(15);
        textField.setMaximumSize(new Dimension(UIScale.scale(400), UIScale.scale(70)));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        return textField;
    }

    // Optional getters
    public String getLoanAmount() {
        return loanAmountTxt.getText();
    }

    public String getInstallmentPlan() {
        return (String) installmentComboBox.getSelectedItem();
    }

    public String getLoanType() {
        return (String) loanTypeComboBox.getSelectedItem();
    }
}
