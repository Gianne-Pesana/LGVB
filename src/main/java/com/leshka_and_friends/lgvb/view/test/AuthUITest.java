/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.test;

import com.leshka_and_friends.lgvb.auth.AuthException;
import com.leshka_and_friends.lgvb.auth.AuthService;
import com.leshka_and_friends.lgvb.user.UserDAO;
import com.leshka_and_friends.lgvb.user.User;
import com.leshka_and_friends.lgvb.auth.SessionService;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Very small demo class to show register/login using JOptionPane. In real app,
 * use a proper UI and do not block threads on long tasks.
 */
public class AuthUITest {

    private final AuthService auth;

    public AuthUITest() {
        // Pass UserSQL into the AuthService
        UserDAO userDAO = new UserDAO();
        this.auth = new AuthService(userDAO);
    }

    public void showRegisterDialog() {
        String username = JOptionPane.showInputDialog("Username:");
        String email = JOptionPane.showInputDialog("Email:");
        JPasswordField pf = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(null, pf, "Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok != JOptionPane.OK_OPTION) {
            return;
        }
        char[] pwd = pf.getPassword();

        String firstName = JOptionPane.showInputDialog("First name:");
        String lastName = JOptionPane.showInputDialog("Last name:");
        String phone = JOptionPane.showInputDialog("Phone (optional):");
        String dobStr = JOptionPane.showInputDialog("Date of birth (YYYY-MM-DD):");

//        try {
//            LocalDate dob = LocalDate.parse(dobStr);
////            auth.register(email, pwd, firstName, lastName, phone, dob);
//            JOptionPane.showMessageDialog(null, "Registered successfully.");
//        } catch (DateTimeParseException ex) {
//            JOptionPane.showMessageDialog(null, "Invalid date format.");
//        } catch (AuthException e) {
//            JOptionPane.showMessageDialog(null, "Registration error: " + e.getMessage());
//        } finally {
//            java.util.Arrays.fill(pwd, '\0');
//        }
    }

    public void showLoginDialog() {
        String email = JOptionPane.showInputDialog("Email:");
        JPasswordField pf = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(null, pf, "Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok != JOptionPane.OK_OPTION) {
            return;
        }
        char[] pwd = pf.getPassword();

        try {
            User user = auth.login(email, pwd);
            SessionService.getInstance().login(user);
            JOptionPane.showMessageDialog(null, "Welcome, " + user.getFullName());
        } catch (AuthException e) {
            JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage());
        } finally {
            java.util.Arrays.fill(pwd, '\0');
        }
    }

    // For quick demo
    public static void main(String[] args) {
        AuthUITest ui = new AuthUITest();
        String[] opts = {"Register", "Login", "Exit"};
        while (true) {
            int c = JOptionPane.showOptionDialog(null, "Choose", "Auth Demo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opts, opts[0]);
            if (c == 0) {
                ui.showRegisterDialog();
            } else if (c == 1) {
                ui.showLoginDialog();
            } else {
                break;
            }
        }
    }
}