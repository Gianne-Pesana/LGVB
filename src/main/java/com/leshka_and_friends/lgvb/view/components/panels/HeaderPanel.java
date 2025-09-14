/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.components.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.leshka_and_friends.lgvb.view.utils.FontLoader;
import com.leshka_and_friends.lgvb.view.utils.SVGUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author vongiedyaguilar
 */
public class HeaderPanel extends JPanel {

    private JLabel titleLabel, dateLabel;

    public HeaderPanel(String title) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        setMaximumSize(this.getPreferredSize());
        setOpaque(false);

        // ---- Center panel with BorderLayout ----
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        // ---- Left: Labels ----
        JPanel labelContainer = new JPanel();
        labelContainer.setLayout(new BoxLayout(labelContainer, BoxLayout.Y_AXIS));
        labelContainer.setOpaque(false);

        titleLabel = new JLabel(title);
        titleLabel.putClientProperty("FlatLaf.style", "foreground: $LGVB.header;");
        titleLabel.setFont(FontLoader.getInter(35f).deriveFont(Font.BOLD));
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);

        dateLabel = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
        dateLabel.putClientProperty("FlatLaf.style", "foreground: $LGVB.header;");
        dateLabel.setFont(FontLoader.getInter(12f));
        dateLabel.setAlignmentX(LEFT_ALIGNMENT);

        labelContainer.add(titleLabel);
        labelContainer.add(Box.createVerticalStrut(2));
        labelContainer.add(dateLabel);

        centerPanel.add(labelContainer, BorderLayout.WEST);

        // ---- Right: SVG Icon ----
        FlatSVGIcon icon = SVGUtils.loadIcon("icons/svg/notif.svg", 25, "LGVB.header"); // size 50px
        JLabel svgLabel = new JLabel(icon);
        svgLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        svgLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel eastContainerPanel = new JPanel();
        eastContainerPanel.setLayout(new BorderLayout());
        eastContainerPanel.add(svgLabel, BorderLayout.NORTH);
        eastContainerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));
        
        centerPanel.add(eastContainerPanel, BorderLayout.EAST);

        add(centerPanel);
    }

    // ====== METHODS ======
    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public String getTitle() {
        return titleLabel.getText();
    }
}