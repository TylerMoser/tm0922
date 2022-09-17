package me.tylermoser.toolrental.exceptions;

/**
 * An exception thrown when a discount percentage is provided outside of the
 * range (0,100)
 */
public class InvalidDiscountException extends ToolRentalValidationException {

	private static final String MESSAGE_FORMAT = "The discount percentage %s is not within the acceptable range of 0 to 100.";
	private static final long serialVersionUID = 1L;

	public InvalidDiscountException(int discount) {
		super.message = String.format(MESSAGE_FORMAT, discount);
	}

}
