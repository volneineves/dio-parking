package com.volnei.parking.exception;

public class PatternError {

    private final Integer error;
    private final String message;
    private final String path;

    public PatternError(Integer error, String message, String path) {
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public Integer getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
