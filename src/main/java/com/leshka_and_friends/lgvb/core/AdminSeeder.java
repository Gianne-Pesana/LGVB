/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.leshka_and_friends.lgvb.core;

import com.leshka_and_friends.lgvb.user.Role;
import com.leshka_and_friends.lgvb.user.User;
import com.leshka_and_friends.lgvb.user.UserDAO;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class AdminSeeder {

    public static void main(String[] args) {
        String email = "leshka@lgvb.com";
        String firstName = "Leshka";
        String lastName = "Alcontin";
        String phone = "09385984444";
        
        
        char[] rawPassword = "admin123".toCharArray();
        String hashed = PasswordUtils.hashPassword(rawPassword);

        User admin = new User();
        admin.setEmail(email);
        admin.setPasswordHash(hashed);
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setPhoneNumber(phone);
        admin.setDateOfBirth(Date.valueOf(LocalDate.now()));
        admin.setRole(Role.ADMIN);
        admin.setImagePath("/profile/default.jpg");
        
        UserDAO dao = new UserDAO();
        dao.addUser(admin);
    }
}
