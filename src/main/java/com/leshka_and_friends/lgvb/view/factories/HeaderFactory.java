/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.factories;

import com.leshka_and_friends.lgvb.view.shared_components.panels.HeaderPanel;

/**
 * @author vongiedyaguilar
 */
public class HeaderFactory {


    public static HeaderPanel createDashboardHeader(String name) {
        return new HeaderPanel("Hi, " + name);
    }

    public static HeaderPanel createWalletHeader() {
        return new HeaderPanel("Wallet");
    }

    public static HeaderPanel createAccountHeader() {
        return new HeaderPanel("Account");
    }

    public static HeaderPanel createLoanRequestHeader() {
        return new HeaderPanel("Loan Request");
    }

    public static HeaderPanel createCardHeader() {
        return new HeaderPanel("Card");
    }
}