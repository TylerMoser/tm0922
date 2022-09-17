package me.tylermoser.toolrental.exceptions;

/**
 * A parent Exception for all other validation exceptions
 */
public abstract class ToolRentalValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected String message;

	public String getMessage() {
		return message;
	}

	public String toString() {
		return getMessage();
	}

}
