package com.leshka_and_friends.lgvb.core.user;
import com.leshka_and_friends.lgvb.account.Account;

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
