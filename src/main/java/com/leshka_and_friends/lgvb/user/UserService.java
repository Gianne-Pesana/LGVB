package com.leshka_and_friends.lgvb.user;

public class UserService {

    private final UserDAO userRepo;

    public UserService(UserDAO userRepo) {
        this.userRepo = userRepo;
    }

    public User addUser(User user) {
        return userRepo.addUser(user);
    }

    public User getUserByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }

    public User getUserById(int id) {
        return userRepo.getUserById(id);
    }
}
