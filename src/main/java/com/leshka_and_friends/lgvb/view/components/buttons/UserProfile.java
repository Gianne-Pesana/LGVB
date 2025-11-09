package com.leshka_and_friends.lgvb.view.components.buttons;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.components.dialogs.LogoutConfirmationDialog;
import com.leshka_and_friends.lgvb.view.components.panels.AvatarPanel;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UserProfile extends JPanel {

    private final AvatarPanel avatarPanel;
    private final JLabel profileName;
    private Runnable onLogoutAction;
    private JPopupMenu popupMenu;
    private Timer hideTimer;

    private final int borderSpacing = UIScale.scale(10);
    private final int panelHeight = UIScale.scale(45);
    private final double profileScale = 0.80;

    public UserProfile() {
        profileName = new JLabel("User Profile");

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ThemeManager.putThemeAwareProperty(this, "background: $LGVB.primary");

        setBorder(BorderFactory.createEmptyBorder(UIScale.scale(borderSpacing), UIScale.scale(borderSpacing),
                UIScale.scale(borderSpacing), UIScale.scale(borderSpacing)));

        int usableHeight = panelHeight - 2 * borderSpacing;
        int avatarSize = (int) (usableHeight * profileScale);

        avatarPanel = new AvatarPanel(null, avatarSize);
        avatarPanel.setMaximumSize(new Dimension(avatarSize, avatarSize));
        avatarPanel.setMinimumSize(new Dimension(avatarSize, avatarSize));
        avatarPanel.setPreferredSize(new Dimension(avatarSize, avatarSize));

        avatarPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        profileName.setAlignmentY(Component.CENTER_ALIGNMENT);

        add(avatarPanel);
        add(Box.createRigidArea(new Dimension(UIScale.scale(20), 0)));

        ThemeManager.putThemeAwareProperty(profileName, "foreground: $LGVB.foreground");
        profileName.setFont(FontLoader.getInter(14f));
        add(profileName);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (popupMenu != null && popupMenu.isShowing()) {
                    popupMenu.setVisible(false);
                } else {
                    showLogoutMenu(e.getComponent());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(UIManager.getColor("Button.hover"));
                if (hideTimer != null && hideTimer.isRunning()) {
                    hideTimer.stop();
                }
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(UIManager.getColor("LGVB.primary"));
                if (popupMenu != null && popupMenu.isShowing()) {
                    hideTimer.restart();
                }
                repaint();
            }
        });

        setPreferredSize(new Dimension(UIScale.scale(228), panelHeight));
        setMaximumSize(new Dimension(UIScale.scale(228), panelHeight));
        setMinimumSize(new Dimension(UIScale.scale(228), panelHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color bg = getBackground() != null ? getBackground() : UIManager.getColor("Panel.background");
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), UIScale.scale(12), UIScale.scale(12));

        super.paintComponent(g2);
        g2.dispose();
    }

    public void addLogoutListener(Runnable action) {
        this.onLogoutAction = action;
    }

    private void showLogoutMenu(Component invoker) {
        popupMenu = new JPopupMenu();
        popupMenu.putClientProperty(FlatClientProperties.STYLE, "arc: 12");

        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.setFont(FontLoader.getInter(14f));
        logoutItem.setHorizontalAlignment(SwingConstants.CENTER);
        logoutItem.addActionListener(e -> {
            if (onLogoutAction != null) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                LogoutConfirmationDialog.showLogoutDialog(parentFrame, () -> onLogoutAction.run());
            }
        });

        popupMenu.add(logoutItem);

        hideTimer = new Timer(2000, e -> popupMenu.setVisible(false));
        hideTimer.setRepeats(false);

        popupMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hideTimer.stop();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                hideTimer.restart();
            }
        });

        int width = invoker.getWidth();
        int height = popupMenu.getPreferredSize().height;
        popupMenu.setPreferredSize(new Dimension(width, height));

        popupMenu.show(invoker, 0, -height - 5);
    }

    public void setUserProfile(String name, String resourcePath) {
        profileName.setText(name);

        java.net.URL imgUrl = getClass().getResource(resourcePath);
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            BufferedImage img = new BufferedImage(
                    icon.getIconWidth(),
                    icon.getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );
            Graphics2D g2d = img.createGraphics();
            icon.paintIcon(null, g2d, 0, 0);
            g2d.dispose();

            avatarPanel.setImage(img);
        } else {
            System.err.println("Image not found: " + resourcePath);
        }

        repaint();
    }
}
