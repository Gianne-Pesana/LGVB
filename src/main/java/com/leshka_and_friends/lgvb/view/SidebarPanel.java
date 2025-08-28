package com.leshka_and_friends.lgvb.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SidebarPanel extends JPanel {

    // --- Tunables / Theme ---
    private static final int SIDEBAR_W = 250;
    private static final int SIDEBAR_H = 810;
    private static final Color SIDEBAR_BLUE = new Color(0, 0, 153);
    private static final Color SELECT_RED = new Color(220, 20, 60);
    private static final int ITEM_W = 200;
    private static final int ITEM_H = 45;
    private static final int ITEM_RADIUS = 15;
    private static final int ICON_SIZE = 20;

    // Track selected
    private RoundedPanel selectedPanel;
    // callback storage for nav items and profile
    private final Map<String, Runnable> navActions = new HashMap<>();
    private Runnable profileAction;

    public SidebarPanel() {
        setPreferredSize(new Dimension(SIDEBAR_W, SIDEBAR_H));
        setBackground(SIDEBAR_BLUE);
        setLayout(new BorderLayout());

        // ===== Top: Logo =====
        JLabel logo = new JLabel(loadIconKeepRatio("/Logo/logo-white.png", 140)); // keep aspect, max-width 140px
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 24));
        logoPanel.setOpaque(false);
        logoPanel.add(logo);

        // ===== Middle: Nav Wrapper =====
        JPanel navWrapper = new JPanel();
        navWrapper.setOpaque(false);
        navWrapper.setLayout(new BoxLayout(navWrapper, BoxLayout.Y_AXIS));
        navWrapper.setBorder(new EmptyBorder(12, 20, 12, 20)); // margins from edges

        // Main nav group
        navWrapper.add(createNavItem("Dashboard", "/icons/Dashboard.png", true));  // default selected
        navWrapper.add(Box.createVerticalStrut(12));
        navWrapper.add(createNavItem("Wallet", "/icons/wallet.png", false));
        navWrapper.add(Box.createVerticalStrut(12));
        navWrapper.add(createNavItem("Loan Request", "/icons/loanReq.png", false));
        navWrapper.add(Box.createVerticalStrut(12));
        navWrapper.add(createNavItem("Card", "/icons/card.png", false));

        // Separation before utility group
        navWrapper.add(Box.createVerticalStrut(24));
        navWrapper.add(createNavItem("Account", "/icons/profile.png", false));
        navWrapper.add(Box.createVerticalStrut(12));
        navWrapper.add(createNavItem("Settings", "/icons/settings.png", false));

        // Keep nav compact at top of center
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(navWrapper, BorderLayout.NORTH);

        // ===== Bottom: Profile Section (placeholder avatar) =====
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        profilePanel.setOpaque(false);
        JLabel profilePic = new JLabel(createCircleIcon(40, Color.GRAY)); // placeholder avatar
        JLabel username = new JLabel("Leshka Alcontin");
        username.setForeground(Color.WHITE);
        username.setFont(loadInterFont(13f)); // use Inter font
        profilePanel.add(profilePic);
        profilePanel.add(username);
        profilePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profilePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (profileAction != null) {
                    profileAction.run();
                }
            }
        });

        // Add to sidebar
        add(logoPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(profilePanel, BorderLayout.SOUTH);
    }

    /**
     * Creates a navigation item panel with rounded edges and selection
     * behavior. Selected item renders a rounded red background; others are
     * fully transparent.
     */
    private RoundedPanel createNavItem(String text, String iconPath, boolean initiallySelected) {
        RoundedPanel panel = new RoundedPanel(ITEM_RADIUS, SELECT_RED);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(ITEM_W, ITEM_H));
        panel.setPreferredSize(new Dimension(ITEM_W, ITEM_H));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(loadScaledIcon(iconPath, ICON_SIZE, ICON_SIZE));
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(loadInterFont(14f));

        panel.add(iconLabel);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(textLabel);

        if (initiallySelected) {
            panel.setSelected(true);
            selectedPanel = panel;
        }

        // --- Hover + Click logic ---
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedPanel != null && selectedPanel != panel) {
                    selectedPanel.setSelected(false);
                    selectedPanel.repaint();
                }
                panel.setSelected(true);
                panel.repaint();
                selectedPanel = panel;

                // invoke registered action (if any)
                Runnable action = navActions.get(text);
                if (action != null) {
                    try {
                        action.run();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (panel != selectedPanel) {
                    panel.setHovered(true);
                    panel.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setHovered(false);
                panel.repaint();
            }
        });

        return panel;
    }

    // ===== Helper: Load Inter font =====
    private Font loadInterFont(float size) {
        try (InputStream is = getClass().getResourceAsStream("/fonts/Inter-Regular.ttf")) {
            if (is == null) {
                System.err.println("⚠️ Could not find Inter-Regular.ttf, using fallback.");
                return new Font("SansSerif", Font.PLAIN, (int) size);
            }
            Font inter = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(inter);
            return inter.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }

    // ===== Helper: Load exact-size icon (used for menu icons) =====
    private ImageIcon loadScaledIcon(String path, int w, int h) {
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            return new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH));
        } catch (IOException | IllegalArgumentException ex) {
            System.err.println("Could not load: " + path);
            return new ImageIcon(new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB));
        }
    }

    // ===== Helper: Load icon preserving aspect ratio with a max width (used for logo) =====
    private ImageIcon loadIconKeepRatio(String path, int maxWidth) {
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            int w = img.getWidth(null);
            int h = img.getHeight(null);
            if (w > maxWidth) {
                double ratio = (double) maxWidth / w;
                w = maxWidth;
                h = (int) Math.round(h * ratio);
            }
            return new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH));
        } catch (IOException | IllegalArgumentException ex) {
            System.err.println("Could not load: " + path);
            BufferedImage ph = new BufferedImage(maxWidth, maxWidth, BufferedImage.TYPE_INT_ARGB);
            return new ImageIcon(ph);
        }
    }

    // ===== Helper: Placeholder circular avatar =====
    private ImageIcon createCircleIcon(int size, Color c) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(c);
        g2.fillOval(0, 0, size, size);
        g2.dispose();
        return new ImageIcon(img);
    }

    // In SidebarPanel.java (add these methods at the bottom)
    public void addMouseListenerToNav(String text, java.awt.event.MouseListener listener) {
        for (Component c : ((JPanel) ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER)).getComponents()) {
            if (c instanceof JPanel) {
                for (Component child : ((JPanel) c).getComponents()) {
                    if (child instanceof RoundedPanel) {
                        RoundedPanel rp = (RoundedPanel) child;
                        for (Component rc : rp.getComponents()) {
                            if (rc instanceof JLabel && ((JLabel) rc).getText().equals(text)) {
                                rp.addMouseListener(listener);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

// For profile panel at the bottom
    public void addProfileClickListener(java.awt.event.MouseListener listener) {
        JPanel profilePanel = (JPanel) ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.SOUTH);
        profilePanel.addMouseListener(listener);
    }

    /**
     * Register an action to run when a nav item with the given text is clicked.
     * Example: sidebar.setNavAction("Dashboard", () -> { ... });
     */
    public void setNavAction(String name, Runnable action) {
        navActions.put(name, action);
    }

    /**
     * Register an action for clicking the profile area at the bottom.
     */
    public void setProfileAction(Runnable action) {
        this.profileAction = action;
    }

}
