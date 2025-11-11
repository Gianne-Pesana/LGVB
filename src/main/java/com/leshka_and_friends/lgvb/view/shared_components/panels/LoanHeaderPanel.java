package com.leshka_and_friends.lgvb.view.shared_components.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.SVGUtils;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class LoanHeaderPanel extends JPanel{
    private JLabel titleLabel;

    public LoanHeaderPanel(String title) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));
        setMaximumSize(this.getPreferredSize());
        setOpaque(false);

        titleLabel = new JLabel(title);
        ThemeManager.putThemeAwareProperty(titleLabel, "foreground: $LGVB.header;");
        titleLabel.setFont(FontLoader.getBaloo2Medium(33f));
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);

        FlatSVGIcon icon = SVGUtils.loadIcon("icons/svg/back.svg", 50); // size 50px
        icon.setColorFilter(SVGUtils.createColorFilter("LGVB.header"));
        JLabel svgLabel = new JLabel(icon);
        svgLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        svgLabel.setVerticalAlignment(SwingConstants.CENTER);
        svgLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 30)); // top, left, bottom, right


        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        centerPanel.add(svgLabel, BorderLayout.WEST);
        centerPanel.add(titleLabel, BorderLayout.CENTER);

        add(centerPanel);
    }

}
