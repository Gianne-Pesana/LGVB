/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.dto;

import com.leshka_and_friends.lgvb.model.CardType;
import java.time.YearMonth;

/**
 *
 * @author giann
 */
public class CardDTO {
    private CardType type;
    private String maskedNumber;
    private String holder;
    private YearMonth expiryDate;
}
