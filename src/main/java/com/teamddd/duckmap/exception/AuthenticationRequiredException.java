package com.teamddd.duckmap.exception;

import com.teamddd.duckmap.common.ExceptionCodeMessage;

public class AuthenticationRequiredException extends RuntimeException {
	public AuthenticationRequiredException() {
		super(ExceptionCodeMessage.AUTHENTICATION_REQUIRED_EXCEPTION.message());
	}

	public AuthenticationRequiredException(String message) {
		super(message);
	}

	public AuthenticationRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationRequiredException(Throwable cause) {
		super(cause);
	}

	public AuthenticationRequiredException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
