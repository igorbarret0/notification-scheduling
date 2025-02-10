package com.igor.notification_scheduler_api.infrastrucutre.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
