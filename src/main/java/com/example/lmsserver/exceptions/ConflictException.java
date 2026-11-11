package com.example.lmsserver.exceptions;


public class ConflictException extends RuntimeException
{
    public ConflictException(String message) {
        super(message);
    }
}
