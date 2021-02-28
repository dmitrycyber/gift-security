package com.epam.esm.jpa.exception;

public class TagNameRegisteredException extends RuntimeException {
    public TagNameRegisteredException() {
        super();
    }

    public TagNameRegisteredException(String message) {
        super(message);
    }

    public TagNameRegisteredException(Throwable cause) {
        super(cause);
    }
}
