package me.tylermoser.toolrental.exceptions;

/**
 * An exception thrown when an attempt is made to checkout a tool for less than
 * 1 day.
 */
public class InvalidDayCountException extends ToolRentalValidationException {

	private static final String MESSAGE_FORMAT = "The day count %s is not within the acceptable range of 1 or greater.";
	private static final long serialVersionUID = 1L;

	public InvalidDayCountException(int dayCount) {
		super.message = String.format(MESSAGE_FORMAT, dayCount);
	}

}
