/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.dashboard;

import com.leshka_and_friends.lgvb.user.UserDTO;
import java.util.List;

/**
 *
 * @author giann
 */
public class DashboardDTO {
    private UserDTO userDTO;

    public DashboardDTO() {
    }

    public DashboardDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
