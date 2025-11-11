package com.leshka_and_friends.lgvb.view.loansetup;

import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.shared_components.panels.LoanHeaderPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class LoanWaitingPanel extends JPanel {

    private LoanHeaderPanel header;

    public LoanWaitingPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        setOpaque(false);
        ThemeManager.putThemeAwareProperty(this, "background: #F0F4FF");

        initComponents();
    }

    private void initComponents() {
        // Header
        header = new LoanHeaderPanel("LGVB Loan");
        header.setOpaque(false);
        add(header, BorderLayout.NORTH);

        // Main message panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));

        JLabel waitingLabel = new JLabel("Hold tight, waiting for approval!", SwingConstants.CENTER);
        waitingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        waitingLabel.setFont(FontLoader.getBaloo2Bold(28f));
        waitingLabel.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.Waiting.text;");

        JLabel subLabel = new JLabel("We’re reviewing your loan application.", SwingConstants.CENTER);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subLabel.setFont(FontLoader.getBaloo2Regular(18f));
        subLabel.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.Waiting.subtext;");

        // Optional: Add a simple animated-like progress indicator (visual only)
        JProgressBar progress = new JProgressBar();
        progress.setIndeterminate(true);
        progress.setAlignmentX(Component.CENTER_ALIGNMENT);
        progress.setPreferredSize(new Dimension(UIScale.scale(200), UIScale.scale(10)));
        progress.setMaximumSize(new Dimension(UIScale.scale(200), UIScale.scale(10)));
        progress.putClientProperty("JProgressBar.arc", 10);
        progress.putClientProperty("JProgressBar.selectionBackground", ThemeGlobalDefaults.getColor("LoanDefault.Waiting.progress"));
        progress.putClientProperty("JProgressBar.selectionForeground", ThemeGlobalDefaults.getColor("LoanDefault.Waiting.progress"));

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(waitingLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(subLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        centerPanel.add(progress);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);
    }
}
