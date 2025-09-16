/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.user;

import com.leshka_and_friends.lgvb.account.AccountDTO;
import java.util.List;

/**
 *
 * @author giann
 */
public class UserDTO {
    String firstName;
    String lastName;
    String profileIconPath;
    List<AccountDTO> accounts;

    public UserDTO() {
    }
    
    public UserDTO(String firstName, String lastName, String profileIconPath, List<AccountDTO> accounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileIconPath = profileIconPath;
        this.accounts = accounts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public String getProfileIconPath() {
        return profileIconPath;
    }

    public void setProfileIconPath(String profileIconPath) {
        this.profileIconPath = profileIconPath;
    }
    
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
    
    
}
