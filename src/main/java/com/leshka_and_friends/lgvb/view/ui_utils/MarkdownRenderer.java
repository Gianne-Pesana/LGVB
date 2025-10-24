package com.leshka_and_friends.lgvb.view.ui_utils;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MarkdownRenderer {

    // tunables
    private static final int MAX_CONTENT_WIDTH = 900;
    private static final int MIN_WRAP_WIDTH = 420;
    private static final int H_GAP = 20;
    private static final int V_GAP = 8;

    /**
     * Render the provided markdown file into a JPanel which contains a scroll
     * pane. The returned panel has white background and consistent layout.
     */
    public static JPanel renderMarkdownPanel(File mdFile) {
        String markdown;
        try {
            markdown = Files.readString(mdFile.toPath());
        } catch (IOException e) {
            markdown = "# Error\nUnable to read markdown file: " + e.getMessage();
        }

        // Content panel that holds rendered components (inside scrollpane)
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Keep track of all JEditorPanes that need rewrapping
        List<JEditorPane> htmlPanes = new ArrayList<>();

        String[] lines = markdown.split("\n", -1);
        boolean inCodeBlock = false;
        String codeFenceLang = null;
        StringBuilder codeBuffer = new StringBuilder();
        int row = 0;

        for (int i = 0; i < lines.length; i++) {
            String raw = lines[i];
            String line = raw.replaceAll("\\r$", ""); // strip CR if present

            // Fenced code block handling
            if (line.startsWith("```")) {
                if (!inCodeBlock) {
                    inCodeBlock = true;
                    codeFenceLang = line.length() > 3 ? line.substring(3).trim() : null;
                    codeBuffer.setLength(0);
                } else {
                    // close block
                    JEditorPane codePane = createCodeBlock(codeBuffer.toString());
                    htmlPanes.add(codePane);
                    gbc.gridy = row++;
                    gbc.insets = new java.awt.Insets(4, 0, V_GAP, 0);
                    contentPanel.add(wrapLeft(codePane), gbc);
                    inCodeBlock = false;
                    codeFenceLang = null;
                }
                continue;
            }
            if (inCodeBlock) {
                codeBuffer.append(line).append("\n");
                continue;
            }

            // blank -> vertical gap
            if (line.trim().isEmpty()) {
                gbc.gridy = row++;
                contentPanel.add(Box.createVerticalStrut(V_GAP), gbc);
                continue;
            }

            // Heading (#..)
            if (line.matches("^#{1,6}\\s+.*")) {
                int level = (int) line.chars().takeWhile(ch -> ch == '#').count();
                String text = line.replaceFirst("^#{1,6}\\s*", "").trim();
                JEditorPane h = createHeading(text, level);
                htmlPanes.add(h);
                gbc.gridy = row++;
                gbc.insets = new java.awt.Insets(level == 1 ? 12 : 8, 0, V_GAP, 0);
                contentPanel.add(wrapLeft(h), gbc);
                continue;
            }

            // Blockquote
            if (line.trim().startsWith(">")) {
                String q = line.trim().replaceFirst("^>\\s?", "").trim();
                JEditorPane qp = createBlockQuote(q);
                htmlPanes.add(qp);
                gbc.gridy = row++;
                gbc.insets = new java.awt.Insets(4, 10, V_GAP, 0);
                contentPanel.add(wrapLeft(qp), gbc);
                continue;
            }

            // Unordered list (including nested with leading spaces)
            if (line.matches("^\\s*[-+*]\\s+.*")) {
                // handle simple single-line list item
                int indent = leadingSpaces(line);
                String text = line.replaceFirst("^\\s*[-+*]\\s+", "").trim();
                JEditorPane li = createListItem(text, false);
                htmlPanes.add(li);
                gbc.gridy = row++;
                gbc.insets = new java.awt.Insets(2, 20 + indent * 6, 2, 0);
                contentPanel.add(wrapLeft(li), gbc);
                continue;
            }

            // Ordered list
            if (line.matches("^\\s*\\d+\\.\\s+.*")) {
                int indent = leadingSpaces(line);
                String text = line.replaceFirst("^\\s*\\d+\\.\\s+", "").trim();
                String marker = line.trim().split("\\s+")[0]; // "1." or "2."
                JEditorPane li = createListItemWithMarker(marker, text);
                htmlPanes.add(li);
                gbc.gridy = row++;
                gbc.insets = new java.awt.Insets(2, 20 + indent * 6, 2, 0);
                contentPanel.add(wrapLeft(li), gbc);
                continue;
            }

            // Numbered-like section (e.g., "2.1 Some text")
            if (line.matches("^\\d+(\\.\\d+)+\\s+.*")) {
                String[] parts = line.trim().split("\\s+", 2);
                String num = parts[0];
                String rest = parts.length > 1 ? parts[1] : "";
                JEditorPane ns = createNumberedSection(num, rest);
                htmlPanes.add(ns);
                gbc.gridy = row++;
                gbc.insets = new java.awt.Insets(4, 10, V_GAP, 0);
                contentPanel.add(wrapLeft(ns), gbc);
                continue;
            }

            // Horizontal rule (--- or ***)
            if (line.matches("^\\s*([-*_]){3,}\\s*$")) {
                JComponent hr = createHR();
                gbc.gridy = row++;
                gbc.insets = new java.awt.Insets(V_GAP, 0, V_GAP, 0);
                contentPanel.add(hr, gbc);
                continue;
            }

            // Fallback: regular paragraph (may contain inline markdown)
            JEditorPane p = createParagraph(line);
            htmlPanes.add(p);
            gbc.gridy = row++;
            gbc.insets = new java.awt.Insets(2, 10, V_GAP, 0);
            contentPanel.add(wrapLeft(p), gbc);
        }

        // push to top
        gbc.gridy = row++;
        gbc.weighty = 1;
        contentPanel.add(Box.createVerticalGlue(), gbc);

        // put contentPanel into a scrollpane
        JScrollPane scroll = new JScrollPane(contentPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // Ensure background of the wrapper panel is white
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(scroll, BorderLayout.CENTER);

        // Rewrap logic: when viewport resizes, recompute widths for all html panes.
        scroll.getViewport().addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                rewrapHtmlPanes(htmlPanes, scroll, contentPanel);
            }
        });

        // initial wrap after layout ready
        SwingUtilities.invokeLater(() -> rewrapHtmlPanes(htmlPanes, scroll, contentPanel));

        return wrapper;
    }

    // ------------------ Helpers & builders ------------------ //
    private static int leadingSpaces(String s) {
        int c = 0;
        for (char ch : s.toCharArray()) {
            if (ch == ' ') {
                c++;
            } else {
                break;
            }
        }
        return c;
    }

    private static JEditorPane createHeading(String text, int level) {
        String html = "<div style='width:100%; text-align:left;'>" + parseInline(text) + "</div>";
        int size = switch (level) {
            case 1 ->
                26;
            case 2 ->
                20;
            case 3 ->
                18;
            case 4 ->
                16;
            default ->
                14;
        };
        int weight = (level == 1) ? 700 : 600;
        return baseHtmlPane(html, size, weight);
    }

    private static JEditorPane createParagraph(String text) {
        String html = "<div style='width:100%; text-align:left;'>" + parseInline(text) + "</div>";
        return baseHtmlPane(html, 14, 400);
    }

    private static JEditorPane createListItem(String text, boolean ordered) {
        String inner = parseInline(text);
        String html = "<div style='width:100%; text-align:left;'>" + inner + "</div>";
        return baseHtmlPane(html, 14, 400);
    }

    private static JEditorPane createListItemWithMarker(String marker, String text) {
        String inner = parseInline(text);
        String html = "<div style='width:100%; text-align:left;'>" + inner + "</div>";
        return baseHtmlPane(html, 14, 400);
    }

    private static JEditorPane createNumberedSection(String num, String text) {
        String html = "<div style='width:100%; text-align:left;'><b>" + escape(num) + "</b> " + parseInline(text) + "</div>";
        return baseHtmlPane(html, 14, 500);
    }

    private static JEditorPane createBlockQuote(String text) {
        String html = "<div style='width:100%; text-align:left; color:#333333;'><i>" + parseInline(text) + "</i></div>";
        JEditorPane p = baseHtmlPane(html, 14, 400);
        // we'll visually indent using the layout in caller
        return p;
    }

    private static JEditorPane createCodeBlock(String code) {
        String html = "<pre style='white-space:pre-wrap; font-family:monospace;'>" + escape(code) + "</pre>";
        return baseHtmlPane(html, 12, 400);
    }

    private static JComponent createHR() {
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setForeground(new Color(200, 200, 200));
        return sep;
    }

    /**
     * Create a left-aligned wrapper so GridBagLayout doesn't center components.
     */
    private static JPanel wrapLeft(JComponent comp) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(comp, BorderLayout.WEST);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }

    /**
     * Core HTML pane creator with consistent defaults.
     */
    private static JEditorPane baseHtmlPane(String innerHtml, int fontSizePx, int fontWeight) {
        JEditorPane pane = new JEditorPane();
        pane.setContentType("text/html");
        pane.setEditable(false);
        pane.setOpaque(false);
        pane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

        // Use inline CSS to ensure left alignment and Inter-like family; width controlled externally.
        String html = "<html><body style='margin:0; padding:0; font-family:Inter, sans-serif; "
                + "font-size:" + fontSizePx + "px; font-weight:" + fontWeight + "; line-height:1.5; text-align:left; color:black;'>"
                + innerHtml + "</body></html>";
        pane.setText(html);
        pane.setBorder(null);
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);
        return pane;
    }

    // Recompute preferred sizes to force wrapping
    private static void rewrapHtmlPanes(List<JEditorPane> panes, JScrollPane scroll, JPanel contentPanel) {
        int viewportWidth = scroll.getViewport().getWidth();
        if (viewportWidth <= 0) {
            return;
        }

        Insets insets = contentPanel.getBorder() instanceof EmptyBorder
                ? ((EmptyBorder) contentPanel.getBorder()).getBorderInsets()
                : new Insets(0, 0, 0, 0);

        int available = viewportWidth - (insets.left + insets.right) - H_GAP;
        available = Math.max(available, MIN_WRAP_WIDTH);
        available = Math.min(available, MAX_CONTENT_WIDTH);

        for (JEditorPane pane : panes) {
            // set width then let the component compute preferred height based on HTML wrapping
            pane.setSize(available, Short.MAX_VALUE);
            Dimension pref = pane.getPreferredSize();
            pane.setPreferredSize(new Dimension(available, pref.height));
            pane.revalidate();
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ---------- Inline markdown parsing (basic but robust) ---------- //
    private static String parseInline(String s) {
        if (s == null) {
            return "";
        }
        String t = escape(s);

        // handle code spans first
        t = t.replaceAll("`([^`]+?)`", "<code style='background:#eee; padding:2px 4px; border-radius:4px;'>$1</code>");

        // bold & italic (strong then em) — non-greedy
        t = t.replaceAll("\\*\\*(.+?)\\*\\*", "<strong>$1</strong>");
        t = t.replaceAll("__(.+?)__", "<strong>$1</strong>");
        t = t.replaceAll("\\*(.+?)\\*", "<em>$1</em>");
        t = t.replaceAll("_(.+?)_", "<em>$1</em>");

        // strikethrough
        t = t.replaceAll("~~(.+?)~~", "<s>$1</s>");

        // links: [text](url)
        t = t.replaceAll("\\[(.+?)\\]\\((.+?)\\)", "<a href='$2' style='color:#1a0dab;'>$1</a>");

        // images: ![alt](url) -> render alt as text if remote not available
        t = t.replaceAll("!\\[(.*?)\\]\\((.+?)\\)", "<img alt='$1' src='$2' style='max-width:100%;'/>");

        // soft-breaks: two spaces + newline replaced earlier while splitting; but keep BR support
        t = t.replaceAll(" {2}\\n", "<br>");

        return t;
    }

    private static String escape(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
