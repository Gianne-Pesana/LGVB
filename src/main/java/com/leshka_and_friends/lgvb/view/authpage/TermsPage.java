package com.leshka_and_friends.lgvb.view.authpage;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import com.leshka_and_friends.lgvb.view.ui_utils.MarkdownRenderer;
import com.leshka_and_friends.lgvb.view.ui_utils.ThemeGlobalDefaults;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TermsPage extends JFrame {

    public TermsPage() {
        setTitle("Terms and Conditions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(UIScale.scale(620), UIScale.scale(580));
        setIconImage(new FlatSVGIcon(ThemeGlobalDefaults.getString("App.icon"), 32, 32).getImage());
        setMinimumSize(new Dimension(520, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // adjust path as needed
        File mdFile = new File("terms_and_conditions.md");

        JPanel mdPanel = MarkdownRenderer.renderMarkdownPanel(mdFile);
        add(mdPanel, BorderLayout.CENTER);
        SwingUtilities.invokeLater(() -> {
            // find the scroll pane inside mdPanel
            JScrollPane scroll = findScrollPane(mdPanel);
            if (scroll != null) {
                scroll.getVerticalScrollBar().setValue(0); // scroll to top
            }
        });

    }

    public static void main(String[] args) {
        ThemeGlobalDefaults.apply();
        SwingUtilities.invokeLater(() -> {
            TermsPage t = new TermsPage();
            t.setVisible(true);
        });
    }

    private JScrollPane findScrollPane(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JScrollPane s) {
                return s;
            } else if (c instanceof Container sub) {
                JScrollPane result = findScrollPane(sub);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

}
