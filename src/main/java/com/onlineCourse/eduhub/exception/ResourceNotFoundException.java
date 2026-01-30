package com.onlineCourse.eduhub.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7254567494706770082L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
