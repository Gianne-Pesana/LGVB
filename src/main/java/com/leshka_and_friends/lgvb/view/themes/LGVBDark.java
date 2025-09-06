/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.view.themes;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatDarculaLaf;


public class LGVBDark extends FlatDarkLaf {
    public static final String NAME = "LGVB Dark";

    public static boolean setup() {
        return setup(new LGVBDark());
    }

    @Override
    public String getName() {
        return NAME;
    }
}

