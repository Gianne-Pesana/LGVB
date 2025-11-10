package com.leshka_and_friends.lgvb.view.shared_components.panels;

import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {

    private JLabel titleLabel;

    public TitlePanel(String title) {
        setOpaque(false);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        setMaximumSize(this.getPreferredSize());

        titleLabel = new JLabel(title);
        titleLabel.setFont(FontLoader.getInter(35f).deriveFont(Font.BOLD));
        ThemeManager.putThemeAwareProperty(titleLabel, "foreground: $LGVB.header");

        add(titleLabel, BorderLayout.CENTER);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public String getTitle() {
        return titleLabel.getText();
    }
}