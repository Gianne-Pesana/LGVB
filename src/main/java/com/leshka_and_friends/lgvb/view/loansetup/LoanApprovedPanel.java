package com.leshka_and_friends.lgvb.view.loansetup;

import com.leshka_and_friends.lgvb.view.shared_components.panels.LoanHeaderPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class LoanApprovedPanel extends JPanel {

    private LoanHeaderPanel header;

    public LoanApprovedPanel() {
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
        content.add(Box.createRigidArea(new Dimension(0, 0)));

        add(content, BorderLayout.NORTH);
    }

    private JPanel createHeader(){
        JPanel container = new JPanel(new BorderLayout());
        header = new LoanHeaderPanel("LGVB Approved");
        container.add(header, BorderLayout.CENTER);
        container.setOpaque(false);
        ThemeManager.putThemeAwareProperty(container, "background: $LGVB.primary");
        return container;
    }
}


