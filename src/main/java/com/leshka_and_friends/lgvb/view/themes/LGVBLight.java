/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.themes;

import com.formdev.flatlaf.FlatLightLaf;

public class LGVBLight extends FlatLightLaf {
    public static final String NAME = "LGVB Light";

    public static boolean setup() {
        return setup(new LGVBLight());
    }

    @Override
    public String getName() {
        return NAME;
    }
}