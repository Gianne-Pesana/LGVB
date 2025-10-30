package com.leshka_and_friends.lgvb.auth;

import com.leshka_and_friends.lgvb.core.wallet.Wallet;
import com.leshka_and_friends.lgvb.exceptions.AuthException;
import com.leshka_and_friends.lgvb.core.user.User;
import com.leshka_and_friends.lgvb.core.user.UserService;
import com.leshka_and_friends.lgvb.utils.PasswordUtils;

import org.apache.commons.codec.binary.Base32;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Clock;

public class AuthService {

    private final UserService userService;
    private static final Pattern EMAIL_RE = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Base32 BASE32 = new Base32();
    private static final String ISSUER = "LGVB";

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


    public boolean isStrong(char[] pwd) throws AuthException {
        if (pwd == null || pwd.length == 0) {
            throw new AuthException("Please enter a password.");
        }
        if (pwd.length < 10) {
            throw new AuthException("Password must be at least 10 characters long.");
        }

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

    public boolean passwordMatches(char[] password, char[] confirmPassword) throws AuthException {
        if (password == null || confirmPassword == null) {
            throw new AuthException("Password fields cannot be empty.");
        }

        if (!Arrays.equals(password, confirmPassword)) {
            throw new AuthException("Passwords do not match.");
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

    public boolean checkActive(Wallet wallet) throws AuthException {
        String accountStatus = wallet.getStatus().toLowerCase();
        if (accountStatus.equals(Wallet.ACTIVE)) {
            return true;
        } else if (accountStatus.equals(Wallet.PENDING)) {
            throw new AuthException("Your account is pending for approval by the admins.");
        } else {
            throw new AuthException("Your account is blocked or closed.");
        }
    }

    public boolean verifyTOTP(String secret, String code, int windowSteps) throws AuthException {
        if (code == null || code.isBlank()) {
            throw new AuthException("Please enter a code!");
        }

        long stepSeconds = 30L;
        long now = System.currentTimeMillis() / 1000L;
        Totp totpCurrent = new Totp(secret);
        if (totpCurrent.verify(code)) {
            return true;
        }

        for (int i = 1; i <= windowSteps; i++) {
            long past = now - (i * stepSeconds);

            Clock pastClock = new Clock(30) {
                @Override
                public long getCurrentInterval() {
                    return past / 30L;
                }
            };

            Totp tPast = new Totp(secret, pastClock);
            if (tPast.now().equals(code)) {
                return true;
            }
        }

        return false; // reject all old codes beyond allowed window
    }

    // Generate a Base32 secret (recommended length: 16 chars -> 80 bits)
    public String generateSecret() {
        byte[] bytes = new byte[10]; // 10 bytes = 80 bits -> Base32 -> about 16 chars
        RANDOM.nextBytes(bytes);
        String base32 = BASE32.encodeToString(bytes);
        // Commons Base32 may produce padding '='; remove and uppercase to be safe
        return base32.replace("=", "").toUpperCase();
    }

    // Build an otpauth URL (URL-encode label and issuer)
    public String getOtpAuthUrl(String accountName, String secret) {
        String label = ISSUER + ":" + accountName;               // "LGVB:account"
        String encodedLabel = URLEncoder.encode(label, StandardCharsets.UTF_8);
        String encodedIssuer = URLEncoder.encode(ISSUER, StandardCharsets.UTF_8);

        // include algorithm/digits/period explicitly
        String params = String.format("secret=%s&issuer=%s&algorithm=SHA1&digits=6&period=30",
                secret, encodedIssuer);

        return "otpauth://totp/" + encodedLabel + "?" + params;
    }


}
