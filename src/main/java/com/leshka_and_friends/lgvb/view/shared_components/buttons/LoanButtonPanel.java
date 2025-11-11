package com.leshka_and_friends.lgvb.view.shared_components.buttons;

import com.leshka_and_friends.lgvb.view.shared_components.modified_swing.RoundedPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;

import javax.swing.*;
import java.awt.*;

public class LoanButtonPanel extends RoundedPanel {
    protected final JLabel textLbl;
    protected final boolean paintClick;
    private ClickListener clickListener;


    protected boolean hovered = false;
    protected boolean selected = false;

    public interface ClickListener {
        void onClick();
    }


    public LoanButtonPanel(String textLabel, boolean paintClick) {
        this.paintClick = paintClick;

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        textLbl = new JLabel(textLabel);
        textLbl.putClientProperty("FlatLaf.style", "foreground: $LoanDefault.ApplyNow.letter;");
        textLbl.setFont(FontLoader.getBaloo2SemiBold(13f));
        textLbl.setAlignmentX(Component.CENTER_ALIGNMENT);


        add(textLbl);
        initMouse();
    }

    public void applyCurrentStyle() {
        Color bg;

        if (selected && paintClick) {
            bg = UIManager.getColor("MenuItem.selectedBackground");
        } else if (!selected && hovered) {
            bg = UIManager.getColor("LoanDefault.ApplyNow.hovered");
        } else {
            bg = UIManager.getColor("LoanDefault.ApplyNow.background");
        }

        if (bg == null) {
            // fallback so you never get invisible buttons
            bg = getBackground();
        }

        if (selected) {
            putClientProperty("FlatLaf.style", "background: $LGVB.primary;");
        } else if (hovered) {
            putClientProperty("FlatLaf.style", "background: $LoanDefault.ApplyNow.hovered;");
        } else {
            putClientProperty("FlatLaf.style", "background: $LoanDefault.ApplyNow.background;");
        }
        repaint();
        setBackground(bg);
        repaint();
    }

    protected void initMouse() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                hovered = true;
                applyCurrentStyle();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hovered = false;
                applyCurrentStyle();
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                selected = true;
                handleClick();
            }
        });
    }

    protected void handleClick() {
        if (paintClick) {
            selected = true;
            hovered = false;
        }

        // Notify whoever is listening
        if (clickListener != null) {
            clickListener.onClick();
        }
    }


    public void setClickListener(ClickListener listener) {
        this.clickListener = listener;
    }


}
