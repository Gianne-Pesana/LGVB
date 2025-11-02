package com.leshka_and_friends.lgvb.exceptions;

// Define this class once in your application
public class PersistenceException extends RuntimeException {
    // You can pass the SQLException into the constructor

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}