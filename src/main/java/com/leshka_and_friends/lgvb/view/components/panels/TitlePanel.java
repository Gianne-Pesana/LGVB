package com.leshka_and_friends.lgvb.view.components.panels;

import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;
import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    public TitlePanel(String title) {
        setLayout(new BorderLayout());
        setOpaque(true);
        ThemeManager.putThemeAwareProperty(this, "background: $Panel.background");
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        ThemeManager.putThemeAwareProperty(label, "foreground: $Label.foreground; font: $h1.font");
        add(label, BorderLayout.CENTER);
    }
}
