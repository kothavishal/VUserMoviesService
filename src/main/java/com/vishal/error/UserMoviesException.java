package com.vishal.error;

import org.springframework.http.HttpStatus;

public class UserMoviesException extends RuntimeException {

	private static final long serialVersionUID = 123456789L;

	public final HttpStatus httpStatus;

	public final ErrorCategory errorCategory;

	public final int errorCode;

	public UserMoviesException(String message, Error error) {
		this(message, error.code, error.category, null, null);
	}

	public UserMoviesException(String message, Error error, HttpStatus httpStatus) {
		this(message, error.code, error.category, httpStatus, null);
	}

	public UserMoviesException(String message, Error error, HttpStatus httpStatus, Throwable cause) {
		this(message, error.code, error.category, httpStatus, cause);
	}

	public UserMoviesException(String message, Error error, Throwable cause) {
		this(message, error.code, error.category, null, cause);
	}

	public UserMoviesException(String message, int errorCode, ErrorCategory errorCategory, HttpStatus httpStatus,
			Throwable cause) {
		super(message, cause);
		this.httpStatus = httpStatus;
		this.errorCategory = errorCategory;
		this.errorCode = errorCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		UserMoviesException that = (UserMoviesException) o;

		if (errorCode != that.errorCode) {
			return false;
		}
		if (httpStatus != that.httpStatus) {
			return false;
		}
		return errorCategory == that.errorCategory;

	}

	@Override
	public int hashCode() {
		int result = httpStatus.hashCode();
		result = 31 * result + errorCategory.hashCode();
		result = 31 * result + errorCode;
		return result;
	}

}
