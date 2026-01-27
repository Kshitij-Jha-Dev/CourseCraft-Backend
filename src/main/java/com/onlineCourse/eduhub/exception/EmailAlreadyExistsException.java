package com.onlineCourse.eduhub.exception;

public class EmailAlreadyExistsException extends RuntimeException {
	
    private static final long serialVersionUID = 5818966420955244397L;

	public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
