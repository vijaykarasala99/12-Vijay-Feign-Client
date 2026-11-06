package com.vijaystack.feign.exception;

public class DownstreamResourceNotFoundException extends RuntimeException {

    public DownstreamResourceNotFoundException(String message) {
        super(message);
    }
}
