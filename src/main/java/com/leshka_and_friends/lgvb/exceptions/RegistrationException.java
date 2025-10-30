package com.leshka_and_friends.lgvb.exceptions;

public class RegistrationException extends AuthException {
    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
