/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core.user;
import com.leshka_and_friends.lgvb.core.wallet.WalletDTO;

/**
 *
 * @author giann
 */
public class UserDTO {
    String firstName;
    String lastName;
    String profileIconPath;
    WalletDTO account;

    public UserDTO() {
    }
    
    public UserDTO(String firstName, String lastName, String profileIconPath, WalletDTO account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileIconPath = profileIconPath;
        this.account = account;
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

    public WalletDTO getAccount() {
        return account;
    }

    public void setAccount(WalletDTO accounts) {
        this.account = accounts;
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
