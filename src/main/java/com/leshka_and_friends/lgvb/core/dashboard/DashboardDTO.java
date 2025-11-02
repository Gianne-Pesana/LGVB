/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core.dashboard;

import com.leshka_and_friends.lgvb.core.user.UserDTO;

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
