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
        container.setOpaque(false);

// === Title (kept left-aligned, with nice breathing space) ===
        JLabel label = new JLabel("Complete the following information");
        label.setFont(FontLoader.getBaloo2SemiBold(20f));
        label.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        label.setBorder(BorderFactory.createEmptyBorder(15, 80, 10, 0)); // left space for aesthetics
        container.add(label, BorderLayout.NORTH);

// === Form region (centered) ===
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

// --- visual centering trick ---
// Instead of padding inside the formPanel, wrap it with a FlowLayout center panel.
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // top-bottom spacing
        centerWrapper.add(formPanel);

// Now the formPanel’s contents are centered horizontally:
        int fieldWidth = UIScale.scale(400);
        int fieldHeight = UIScale.scale(40);

        JLabel loanAmountLabel = new JLabel("Enter Loan Amount");
        loanAmountLabel.setFont(FontLoader.getBaloo2SemiBold(18f));
        loanAmountLabel.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        loanAmountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loanAmountTxt = createTextField();
        loanAmountTxt.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        loanAmountTxt.setMaximumSize(new Dimension(fieldWidth, fieldHeight));
        loanAmountTxt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel installmentLabel = new JLabel("Select Installment Plan");
        installmentLabel.setFont(FontLoader.getBaloo2SemiBold(18f));
        installmentLabel.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        installmentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        installmentComboBox = createComboBox(loanTerms);
        installmentComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel loanTypeLabel = new JLabel("Select Loan Type");
        loanTypeLabel.setFont(FontLoader.getBaloo2SemiBold(18f));
        loanTypeLabel.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        loanTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loanTypeComboBox = createComboBox(loanTypes);
        loanTypeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setOpaque(false);
        submitButton = new LoanButtonPanel("Submit", true);
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
        submitButton.setPreferredSize(new Dimension(UIScale.scale(100), UIScale.scale(35)));
        submitButton.setMaximumSize(new Dimension(UIScale.scale(100), UIScale.scale(35)));
        buttonPanel.add(submitButton);

// === Vertical layout with precise spacing ===
        int spacingSmall = 15;
        int spacingMedium = 25;
        int spacingLarge = 40;

        formPanel.add(Box.createRigidArea(new Dimension(0, spacingMedium)));
        formPanel.add(loanAmountLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, spacingSmall)));
        formPanel.add(loanAmountTxt);

        formPanel.add(Box.createRigidArea(new Dimension(0, spacingLarge)));
        formPanel.add(installmentLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, spacingSmall)));
        formPanel.add(installmentComboBox);

        formPanel.add(Box.createRigidArea(new Dimension(0, spacingLarge)));
        formPanel.add(loanTypeLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, spacingSmall)));
        formPanel.add(loanTypeComboBox);

        formPanel.add(Box.createRigidArea(new Dimension(0, spacingMedium)));
        formPanel.add(buttonPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, spacingMedium)));

// === add centered form to container ===
        container.add(centerWrapper, BorderLayout.CENTER);

        return container;

    }

    private RoundedComboBox<String> createComboBox(String[] options) {
        RoundedComboBox<String> comboBox = new RoundedComboBox<>();
        comboBox.setOpaque(false); // let FlatLaf handle background
        comboBox.updateUI();

        for (String option : options) {
            comboBox.addItem(option);
        }

        comboBox.setPreferredSize(new Dimension(UIScale.scale(400), UIScale.scale(40)));
        comboBox.setMaximumSize(new Dimension(UIScale.scale(400), UIScale.scale(40)));

        comboBox.putClientProperty("JComponent.roundRect", true);
        comboBox.setFont(FontLoader.getBaloo2SemiBold(15f));


        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally

        return comboBox;
    }

    private RoundedTextField createTextField() {
        RoundedTextField textField = new RoundedTextField(25);
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
