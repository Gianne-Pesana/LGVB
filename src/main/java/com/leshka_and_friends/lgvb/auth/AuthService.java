package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.user.User;
import com.leshka_and_friends.lgvb.user.UserService;
import com.leshka_and_friends.lgvb.core.PasswordUtils;
import java.util.regex.Pattern;

public class AuthService {

    private final UserService userService;
    private static final Pattern EMAIL_RE = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public boolean isValidEmail(String email) throws AuthException {
        if (email == null || !EMAIL_RE.matcher(email).matches()) {
            throw new AuthException("Invalid email format.");
        }
        if (userService.getUserByEmail(email) != null) {
            throw new AuthException("Email already exists, please log in.");
        }
        return true;
    }

    public User login(String email, char[] password) throws AuthException {
        User user = userService.getUserByEmail(email);
        if (user == null || !PasswordUtils.verifyPassword(password, user.getPasswordHash())) {
            throw new AuthException("Invalid credentials");
        }
        return user;
    }

    public boolean isStrong(char[] pwd) throws AuthException {
        if (pwd == null || pwd.length == 0) throw new AuthException("Please enter a password.");
        if (pwd.length < 10) throw new AuthException("Password must be at least 10 characters long.");

        String s = new String(pwd);
        boolean hasLower = s.chars().anyMatch(Character::isLowerCase);
        boolean hasUpper = s.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = s.chars().anyMatch(Character::isDigit);
        boolean hasSymbol = s.chars().anyMatch(c -> "!@#$%^&*()_+[]{}<>?/|".indexOf(c) >= 0);

        if (!(hasLower && hasUpper && hasDigit && hasSymbol)) {
            throw new AuthException("""
                Password must include:
                • at least one lowercase letter
                • at least one uppercase letter
                • at least one number
                • at least one special character
            """.trim());
        }

        return true;
    }
}
