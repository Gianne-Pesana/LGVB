package com.leshka_and_friends.lgvb.view.components;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class AmountField extends RoundedTextField {

    private static final NumberFormat FORMATTER = NumberFormat.getNumberInstance(Locale.US);
    private boolean allowDecimals = true;

    public AmountField() {
        super();
        FORMATTER.setGroupingUsed(true); // enable commas
        setHorizontalAlignment(LEFT);

        ((AbstractDocument) this.getDocument()).setDocumentFilter(new AmountDocumentFilter());

        // Optional: format text when focus is lost
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                formatText();
            }
        });
    }

    private void formatText() {
        String text = getText().trim();
        if (text.isEmpty()) return;

        try {
            Number number = FORMATTER.parse(text.replaceAll(",", ""));
            setText(FORMATTER.format(number));
        } catch (ParseException e) {
            // fallback to previous value if parsing fails
        }
    }

    public double getAmountValue() {
        try {
            return FORMATTER.parse(getText().replaceAll(",", "")).doubleValue();
        } catch (ParseException e) {
            return 0.0;
        }
    }

    public void setAllowDecimals(boolean allowDecimals) {
        this.allowDecimals = allowDecimals;
    }

    private class AmountDocumentFilter extends DocumentFilter {
        private final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            replace(fb, offset, 0, string, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text == null) return;

            Document doc = fb.getDocument();
            String oldText = doc.getText(0, doc.getLength());
            String newText = oldText.substring(0, offset) + text + oldText.substring(offset + length);

            // Remove commas before validating
            String clean = newText.replaceAll(",", "");

            if (clean.isEmpty() || clean.equals(".")) {
                fb.replace(0, doc.getLength(), "", attrs);
                return;
            }

            if (!isValidNumber(clean)) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            try {
                Number parsed = decimalFormat.parse(clean);
                String formatted = allowDecimals
                        ? decimalFormat.format(parsed)
                        : decimalFormat.format(parsed.longValue());

                fb.replace(0, doc.getLength(), formatted, attrs);
            } catch (ParseException e) {
                // ignore if parse fails
            }
        }

        private boolean isValidNumber(String text) {
            if (allowDecimals) {
                return text.matches("\\d*(\\.\\d{0,2})?");
            } else {
                return text.matches("\\d*");
            }
        }
    }
}

