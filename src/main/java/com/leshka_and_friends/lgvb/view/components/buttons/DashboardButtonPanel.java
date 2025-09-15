/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.components.buttons;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.panels.RoundedPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vongiedyaguilar
 */
public abstract class DashboardButtonPanel extends JPanel {
    protected final String svgPath;
    protected final JLabel iconLabel;
    protected final JLabel textLabel;
    protected final double ICON_SCALE = 0.4;
    protected final Color transparent = new Color(0, 0, 0, 0);

    protected boolean hovered = false;
    protected boolean selected = false;

    protected final List<Runnable> actionListeners = new ArrayList<>();

    public DashboardButtonPanel(String svgPath, String text) {
    this.svgPath = svgPath;

    setOpaque(false);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    // Icon
    iconLabel = createIconLabel();
    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Text
    textLabel = new JLabel(text);
    textLabel.putClientProperty("FlatLaf.style", "foreground: $LGVB.header;");
    textLabel.setFont(FontLoader.getInter(10f));
    textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Add to panel
    add(iconLabel);
    add(Box.createVerticalStrut(2)); // small spacing between icon and text
    add(textLabel);

    applyCurrentStyle();
    initMouse();
}
    protected void initPanel() {
        setPreferredSize(new Dimension(60, 70)); // adjust height for icon + text
        setMinimumSize(getPreferredSize());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    protected JLabel createIconLabel() {
        // ** TODO: Von, change this to ThemeGlobalDefaults.getScaledInt("Dashboard.icon.size"); **
        int maxIconSize = UIScale.scale(28); // max pixel size
        FlatSVGIcon icon = SVGUtils.loadIcon(svgPath, maxIconSize);
        icon.setColorFilter(SVGUtils.createColorFilter("LGVB.header"));
        JLabel label = new JLabel(icon);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // optional dynamic resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int availableHeight = getHeight() - textLabel.getHeight() - 4; // reserve space for text + spacing
                int newSize = Math.min(maxIconSize, availableHeight);
                label.setIcon(icon.derive(newSize, newSize));
            }
        });

        return label;
    }

    protected abstract void applyCurrentStyle();

    protected abstract void handleClick();

    protected void initMouse() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!selected) {
                    hovered = true;
                    applyCurrentStyle();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hovered = false;
                applyCurrentStyle();
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                handleClick();
                fireActionListeners();
            }
        });
    }

    public void addActionListener(Runnable listener) {
        actionListeners.add(listener);
    }

    protected void fireActionListeners() {
        for (Runnable r : actionListeners) r.run();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        applyCurrentStyle();
    }

    public boolean isSelected() {
        return selected;
    }
}