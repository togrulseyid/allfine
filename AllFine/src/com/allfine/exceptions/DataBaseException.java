package com.allfine.exceptions;

public class DataBaseException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;

	public DataBaseException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
