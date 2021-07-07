package com.epam.esm.jpa.exception;

public class CustomAccessDeniedException extends RuntimeException {
    public CustomAccessDeniedException() {
        super();
    }

    public CustomAccessDeniedException(String message) {
        super(message);
    }

    public CustomAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomAccessDeniedException(Throwable cause) {
        super(cause);
    }

    protected CustomAccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
