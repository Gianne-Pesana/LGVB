package com.leshka_and_friends.lgvb.view.test;

import javax.swing.*;
import java.awt.*;

public class LandingPage extends JFrame {

    private final JSplitPane splitPane;
    private final SidebarPanel sidebar;

    public LandingPage() {
        setTitle("Landing Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 810);
        setLocationRelativeTo(null);

        splitPane = new JSplitPane();
        splitPane.setDividerLocation(250);
        splitPane.setDividerSize(0);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(null);

        sidebar = new SidebarPanel();
        splitPane.setLeftComponent(sidebar);

        // default right content
        splitPane.setRightComponent(new DashboardPanel());

        setupSidebarActions();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(splitPane, BorderLayout.CENTER);
        
        setVisible(true);
    }

    private void setupSidebarActions() {
        // Dashboard -> show DashboardPanel
        sidebar.setNavAction("Dashboard", () -> SwingUtilities.invokeLater(() -> {
            splitPane.setRightComponent(new DashboardPanel());
            splitPane.revalidate();
            splitPane.repaint();
        }));

        // These show ComingSoon
        String[] soon = {"Wallet", "Loan Request", "Card", "Account"};
        for (String name : soon) {
            sidebar.setNavAction(name, () -> SwingUtilities.invokeLater(() -> {
                splitPane.setRightComponent(new ComingSoon());
                splitPane.revalidate();
                splitPane.repaint();
            }));
        }

        // Settings -> show JOptionPane
        sidebar.setNavAction("Settings", () -> SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Coming soon", "Settings", JOptionPane.INFORMATION_MESSAGE);
        }));

        // Profile (bottom) -> show JOptionPane
        sidebar.setProfileAction(() -> SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Coming soon", "Profile", JOptionPane.INFORMATION_MESSAGE);
        }));
    }

}
