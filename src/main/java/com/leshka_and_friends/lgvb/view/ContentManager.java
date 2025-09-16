/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author vongiedyaguilar
 */
public class ContentManager {
    private JPanel container;
    private CardLayout layout;

    public ContentManager(JPanel container) {
        this.container = container;
        this.layout = (CardLayout) container.getLayout();
    }

    public void show(String name) {
        layout.show(container, name);
    }
}