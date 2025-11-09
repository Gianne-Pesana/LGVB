package com.leshka_and_friends.lgvb.view.forms;

import com.leshka_and_friends.lgvb.core.app.ServiceLocator;
import com.leshka_and_friends.lgvb.view.components.RoundedButton;
import com.leshka_and_friends.lgvb.view.components.TransparentScrollbar;
import com.leshka_and_friends.lgvb.view.components.notifications.NotificationCard;
import com.leshka_and_friends.lgvb.view.ui_utils.FontLoader;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class NotificationsPanel extends JPanel {

    private final JPanel feedPanel;
    private final Runnable onBack;

    public NotificationsPanel(Runnable onBack) {
        this.onBack = onBack;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        setLayout(new BorderLayout(0, 20));

        // Register with ServiceLocator so the InAppNotification observer can find it
        ServiceLocator.getInstance().registerService(NotificationsPanel.class, this);

        // Header
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Feed Panel in a Scroll Pane
        feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        feedPanel.setOpaque(false);

        JScrollPane scrollPane = createStyledScrollPane(feedPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Notifications");
        title.setFont(FontLoader.getBaloo2Bold(36));
        ThemeManager.putThemeAwareProperty(title, "foreground: $LGVB.header");

        RoundedButton backButton = new RoundedButton("Back to Dashboard");
        backButton.setFont(FontLoader.getInter(14));
        backButton.addActionListener(e -> {
            if (onBack != null) {
                onBack.run();
            }
        });

        header.add(title, BorderLayout.WEST);
        header.add(backButton, BorderLayout.EAST);
        return header;
    }

    public void addNotification(String message) {
        SwingUtilities.invokeLater(() -> {
            NotificationCard card = new NotificationCard(message);
            feedPanel.add(card);
            feedPanel.add(Box.createVerticalStrut(ThemeGlobalDefaults.getScaledInt("Transaction.card.spacing")));
            feedPanel.revalidate();
            feedPanel.repaint();
        });
    }

    private JScrollPane createStyledScrollPane(JComponent content) {
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        content.setOpaque(false);

        JScrollBar vBar = new JScrollBar(JScrollBar.VERTICAL);
        vBar.setOpaque(false);
        vBar.setUI(new TransparentScrollbar());
        vBar.putClientProperty("CustomScrollbarUI", Boolean.TRUE);
        vBar.setUnitIncrement(16);

        scrollPane.setVerticalScrollBar(vBar);
        scrollPane.getVerticalScrollBar().setOpaque(false);

        return scrollPane;
    }
}
