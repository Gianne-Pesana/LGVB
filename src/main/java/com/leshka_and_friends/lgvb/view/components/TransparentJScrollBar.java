// com/leshka_and_friends/lgvb/view/components/TransparentJScrollBar.java
package com.leshka_and_friends.lgvb.view.components;

import javax.swing.*;
import java.awt.*;

/**
 * JScrollBar that forces a custom UI and prevents any background painting.
 */
public class TransparentJScrollBar extends JScrollBar {

    public TransparentJScrollBar(int orientation) {
        super(orientation);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        setBorder(BorderFactory.createEmptyBorder());
        // Make the preferred width a little larger so the thumb is visible and clickable
        setPreferredSize(new Dimension(12, 0));
        // Ensure our UI is applied initially
        setUI(new TransparentScrollbar()); // your FlatScrollBarUI subclass or Basic one
    }

    @Override
    public void updateUI() {
        // IMPORTANT: do NOT call super.updateUI() (which would reinstall FlatLaf's UI)
        // Instead, create and set our UI instance
        // If you want to be absolutely safe, create a fresh UI each update:
        setUI(new TransparentScrollbar());
        // keep opacity off
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Do nothing here — prevents FlatLaf or Swing from painting the track background.
        // We still allow our UI to paint the thumb via paintThumb().
    }
}
