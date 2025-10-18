/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.ui_utils;

import javax.swing.*;
import javax.swing.text.*;

public class TextFieldUtils {

    public static void restrictToDigits(JTextField field, int maxLength) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string != null && string.matches("\\d+") && isWithinLength(fb, string, maxLength)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text != null && text.matches("\\d+") && isWithinLength(fb, text, maxLength)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isWithinLength(FilterBypass fb, String text, int maxLength) {
                return maxLength <= 0 || (fb.getDocument().getLength() + text.length()) <= maxLength;
            }
        });
    }
}
