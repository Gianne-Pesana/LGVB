package com.leshka_and_friends.lgvb.view.test;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.panels.RoundedPanel;
import com.leshka_and_friends.lgvb.view.utils.FontLoader;
import com.leshka_and_friends.lgvb.view.utils.SVGUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A rounded panel representing a menu item. - Text + SVG icon - Hover/selected
 * background via theme .properties - Click support (action listeners)
 */
public class MenuItemPanel extends RoundedPanel {

    private final String text;
    private final String svgPath;
    private final boolean paintClick;

    private final double ICON_SCALE = 0.40;
    private final int ICON_TEXT_GAP = 20;

    private boolean hovered = false;
    private boolean selected = false;

    private final List<Runnable> actionListeners = new ArrayList<>();
    private final JLabel label;

    public MenuItemPanel(String text, String svgPath, boolean paintClick) {
        super();
        this.text = text;
        this.svgPath = svgPath;
        this.paintClick = paintClick;

        Dimension itemSize = UIScale.scale(new Dimension(198, 32));
        setPreferredSize(itemSize);
        setMinimumSize(itemSize);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, itemSize.height));
        setBorder(BorderFactory.createEmptyBorder(1, 35, 1, 1));

        setOpaque(false);
        setLayout(new BorderLayout());

        this.label = createLabel();
        add(label, BorderLayout.CENTER);

        applyCurrentStyle();
        initMouse();
    }

    // ---------------- Mouse handling ----------------
    private void initMouse() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                applyCurrentStyle();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                applyCurrentStyle();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (paintClick) {
                    selected = true;
                }
                applyCurrentStyle();
                fireActionListeners();
            }
        });
    }

    // ---------------- Style handling ----------------
    private void applyCurrentStyle() {
        Color bg;

        if (selected && paintClick) {
            bg = UIManager.getColor("MenuItem.selectedBackground");
        } else if (!selected && hovered) {
            bg = UIManager.getColor("MenuItem.hoverBackground");
        } else {
            bg = UIManager.getColor("LGVB.primary");
        }

        setBackground(bg);   // let RoundedPanel paint with this color
        repaint();
    }

    private JLabel createLabel() {
        JLabel label = new JLabel(text);

        int prefHeight = getPreferredSize().height;
        int iconSize = Math.max(1, (int) Math.floor(prefHeight * ICON_SCALE));
        FlatSVGIcon icon = new FlatSVGIcon(svgPath, iconSize, iconSize);
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
                    label.setIcon(icon.derive(newSize, newSize));
                    lastSize = newSize;
                }
            }
        });

        return label;
    }

    // ---------------- ActionListener support ----------------
    public void addActionListener(Runnable listener) {
        actionListeners.add(listener);
    }

    private void fireActionListeners() {
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
