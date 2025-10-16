package com.leshka_and_friends.lgvb.user;

import com.leshka_and_friends.lgvb.account.Account;
import java.sql.Date;
import java.sql.Timestamp;

public class User {

    private int userId;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date dateOfBirth;
    private Role role;
    private Timestamp createdAt;
    private String imagePath;
    
    private final String defaultProfile = "/profile/default.jpg";

    public User() {
    }

    public User(int userId, String email, String passwordHash, String firstName, String lastName, String phoneNumber, Date dateOfBirth, Role role, Timestamp createdAt, String imagePath) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.createdAt = createdAt;
        this.imagePath = imagePath;
    }
    

    // Getters & setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        if (imagePath == null) {
            this.imagePath = defaultProfile;
        } else {
            this.imagePath = imagePath;
        }
    }
    
    

    // Convenience method
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
