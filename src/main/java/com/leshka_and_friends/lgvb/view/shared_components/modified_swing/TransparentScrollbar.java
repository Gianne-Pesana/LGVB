package com.leshka_and_friends.lgvb.view.shared_components.modified_swing;

import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.formdev.flatlaf.ui.FlatScrollBarUI;

import javax.swing.*;
import java.awt.*;

public class TransparentScrollbar extends FlatScrollBarUI {

    private Color thumbColor;
    private int thumbWidth;
    private int arc = ThemeGlobalDefaults.getInt("scrollbar.thumb.arc");

    public TransparentScrollbar() {
        this.thumbColor = ThemeGlobalDefaults.getColor("scrollbar.thumb.color");
        this.thumbWidth = ThemeGlobalDefaults.getInt("scrollbar.thumb.width");
    }

    public TransparentScrollbar(Color thumbColor, int thumbWidth) {
        this.thumbColor = thumbColor;
        this.thumbWidth = thumbWidth;
    }

    public TransparentScrollbar(Color thumbColor, int thumbWidth, int arc) {
        this.thumbColor = thumbColor;
        this.thumbWidth = thumbWidth;
        this.arc = arc;
    }
    
    

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        // Skip painting the track to make it fully transparent
    }

    @Override
protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
    if (!scrollbar.isEnabled() || thumbBounds.isEmpty()) return;

    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Fetch theme color dynamically at paint time
    Color currentThumb = UIManager.getColor("ScrollBar.thumb") != null 
        ? UIManager.getColor("ScrollBar.thumb") 
        : thumbColor;

    g2.setColor(ThemeGlobalDefaults.getColor("scrollbar.thumb.color"));
    g2.fillRoundRect(
        thumbBounds.x + (thumbBounds.width - thumbWidth) / 2,
        thumbBounds.y,
        thumbWidth,
        thumbBounds.height,
        arc, arc
    );
    g2.dispose();
}

}
