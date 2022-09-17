package me.tylermoser.toolrental.exceptions;

/**
 * An Exception thrown when an attempt is made to checkout a tool that does not
 * exist.
 */
public class ToolNotFoundException extends ToolRentalValidationException {

	private static final String MESSAGE_FORMAT = "There is no tool in inventory with code %s.";
	private static final long serialVersionUID = 1L;

	public ToolNotFoundException(String code) {
		super.message = String.format(MESSAGE_FORMAT, code);
	}

}
