package com.leshka_and_friends.lgvb.view.components.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.RoundedPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author vongiedyaguilar
 */
public class BalancePanel extends RoundedPanel {
    private int width = ThemeGlobalDefaults.getScaledInt("Dashboard.balancePanel.width");
    private int height = ThemeGlobalDefaults.getScaledInt("Dashboard.balancePanel.height");
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
        ThemeManager.putThemeAwareProperty(titleLabel, "foreground: $LGVB.foreground");
        titleLabel.setFont(FontLoader.getBaloo2Medium(
                ThemeGlobalDefaults.getScaledFloat("Dashboard.balancePanel.titleLabel.font.size"))
                .deriveFont(Font.PLAIN)
        );

        JLabel balanceValue = new JLabel("₱ " + balance);
        ThemeManager.putThemeAwareProperty(balanceValue, "foreground: $LGVB.foreground");
//        balanceValue.putClientProperty("FlatLaf.style", "font: bold +20; foreground: $LGVB.foreground");
        balanceValue.setFont(FontLoader.getBaloo2ExtraBold(
                ThemeGlobalDefaults.getScaledFloat("Dashboard.balancePanel.balanceValue.font.size"))
        );

        leftPanel.add(titleLabel);
        leftPanel.add(Box.createVerticalStrut(UIScale.scale(5)));
        leftPanel.add(balanceValue);

        add(leftPanel, BorderLayout.WEST);

        // Right panel: SVG icon
        JLabel addBalance = new JLabel();
        FlatSVGIcon icon = SVGUtils.loadIcon("icons/svg/addbalance.svg", ThemeGlobalDefaults.getScaledInt("Dashboard.balancePanel.addBalanceIcon.size"));
        icon.setColorFilter(SVGUtils.createColorFilter("LGVB.foreground"));
        addBalance.setIcon(icon);
        addBalance.setHorizontalAlignment(SwingConstants.CENTER);
        add(addBalance, BorderLayout.EAST);
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