package com.resume_tailor.backend.utils;

public class ResponseWrapper<T> {
    private final boolean success;
    private final String message;
    private final T data;

    public ResponseWrapper(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}