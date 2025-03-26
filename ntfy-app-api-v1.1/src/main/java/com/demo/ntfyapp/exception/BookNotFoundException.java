package com.demo.ntfyapp.exception;

// BookNotFoundException
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
