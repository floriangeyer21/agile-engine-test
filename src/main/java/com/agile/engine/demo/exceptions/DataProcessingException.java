package com.agile.engine.demo.exceptions;

public class DataProcessingException extends RuntimeException {

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
