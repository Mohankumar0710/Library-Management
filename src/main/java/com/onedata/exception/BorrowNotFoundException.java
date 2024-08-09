package com.onedata.exception;

public class BorrowNotFoundException extends BookNotFoundException {
    public BorrowNotFoundException(String message) {
        super(message);
    }

    public BorrowNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

