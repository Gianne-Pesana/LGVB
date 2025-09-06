/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.components.buttons;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.panels.RoundedPanel;
import com.leshka_and_friends.lgvb.view.utils.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;

public abstract class SidebarButtonPanel extends RoundedPanel {

    protected final String text;
    protected final String svgPath;
    protected final JLabel label;
    protected final double ICON_SCALE = 0.40;
    protected final int ICON_TEXT_GAP = 20;

    protected boolean hovered = false;
    protected boolean selected = false;

    protected final List<Runnable> actionListeners = new ArrayList<>();

    public SidebarButtonPanel(String text, String svgPath) {
        super();
        this.text = text;
        this.svgPath = svgPath;

        initPanel();
        this.label = createLabel();
        add(label, BorderLayout.CENTER);

        applyCurrentStyle();
        initMouse();
    }

    protected void initPanel() {
        Dimension itemSize = UIScale.scale(new Dimension(198, 32));
        setPreferredSize(itemSize);
        setMinimumSize(itemSize);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, itemSize.height));
        setBorder(BorderFactory.createEmptyBorder(1, 35, 1, 1));
        setOpaque(false);
        setLayout(new BorderLayout());
    }

    protected JLabel createLabel() {
        JLabel label = new JLabel(text);
        int prefHeight = getPreferredSize().height;
        int iconSize = Math.max(1, (int) Math.floor(prefHeight * ICON_SCALE));

        FlatSVGIcon icon = createIconSafe(svgPath, iconSize);

        icon.setColorFilter(SVGUtils.createColorFilter("Label.foreground"));
        label.setIcon(icon);
        label.setFont(FontLoader.getInter(14f));
        label.putClientProperty("FlatLaf.style", "foreground: $Label.foreground;");
        label.setIconTextGap(ICON_TEXT_GAP);

        // resize icon dynamically
        addComponentListener(new ComponentAdapter() {
            private int lastSize = -1;

            @Override
            public void componentResized(ComponentEvent e) {
                int newSize = (int) Math.floor(getHeight() * ICON_SCALE);
                if (newSize > 0 && newSize != lastSize) {
                    // derive a new icon on the fly without reassigning 'icon'
                    label.setIcon(icon.derive(newSize, newSize));
                    lastSize = newSize;
                }
            }
        });

        return label;
    }

    private FlatSVGIcon createIconSafe(String path, int size) {
        String finalPath = path;

        // check if resource exists
        if (getClass().getResource(path) == null) {
            // fallback default
            finalPath = "icons/svg/dashboard.svg";
        }

        try {
            return new FlatSVGIcon(finalPath, size, size);
        } catch (Exception e) {
            // worst-case: return an empty icon
            return new FlatSVGIcon("", size, size);
        }
    }

    protected abstract void applyCurrentStyle();

    protected void initMouse() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!selected) {    // only apply hover if not selected
                    hovered = true;
                    applyCurrentStyle();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!selected) {    // only remove hover if not selected
                    hovered = false;
                    applyCurrentStyle();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                handleClick();
                applyCurrentStyle();
                fireActionListeners();
            }
        });
    }

    protected abstract void handleClick();

    public void addActionListener(Runnable listener) {
        actionListeners.add(listener);
    }

    protected void fireActionListeners() {
        for (Runnable r : actionListeners) {
            r.run();
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        applyCurrentStyle();
    }

    public boolean isSelected() {
        return selected;
    }
}
