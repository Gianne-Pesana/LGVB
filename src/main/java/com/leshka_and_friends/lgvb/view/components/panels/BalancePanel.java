package com.leshka_and_friends.lgvb.view.components.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.utils.FontLoader;
import com.leshka_and_friends.lgvb.view.utils.SVGUtils;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author vongiedyaguilar
 */
public class BalancePanel extends RoundedPanel {
    private int width = UIScale.scale(380);
    private int height = UIScale.scale(80); 
    private int balance;

    private final String curr = "Current Balance";

    public BalancePanel() {
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 25));
        putClientProperty("FlatLaf.style", "background: $LGVB.primary");

        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // Left panel: current balance text
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(curr);
        titleLabel.putClientProperty("FlatLaf.style", "foreground: $LGVB.foreground");
        titleLabel.setFont(FontLoader.getInter(10f).deriveFont(Font.BOLD));

        JLabel balanceValue = new JLabel("₱ " + balance);
        balanceValue.putClientProperty("FlatLaf.style", "font: bold +20; foreground: $LGVB.foreground");

        leftPanel.add(titleLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(balanceValue);

        add(leftPanel, BorderLayout.WEST);

        // Right panel: SVG icon
        JLabel iconLabel = new JLabel();
        FlatSVGIcon icon = SVGUtils.loadIcon("icons/svg/addbalance.svg", UIScale.scale(40), "LGVB.foreground");
        iconLabel.setIcon(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(iconLabel, BorderLayout.EAST);
    }

    public void setBalance(int balance) {
        this.balance = balance;
        // Update the leftPanel balance label
        if (getComponentCount() > 0 && getComponent(0) instanceof JPanel leftPanel
            && leftPanel.getComponentCount() > 2
            && leftPanel.getComponent(2) instanceof JLabel lbl) {
            lbl.setText("₱ " + balance);
        }
    }

    public int getBalance() {
        return this.balance;
    }
}