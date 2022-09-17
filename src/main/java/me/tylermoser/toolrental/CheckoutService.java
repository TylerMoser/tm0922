package me.tylermoser.toolrental;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import me.tylermoser.toolrental.exceptions.InvalidDayCountException;
import me.tylermoser.toolrental.exceptions.InvalidDiscountException;
import me.tylermoser.toolrental.exceptions.ToolNotFoundException;
import me.tylermoser.toolrental.tools.Tool;

/**
 * The primary service used to create a rental.
 * 
 * I would generally prefer to use a Spring service for this, but this demo
 * appears to be very object-oriented in design, and so I am going to keep my
 * solution to pure-Java.
 * 
 * While the requirements do not explicitly state it, I would prefer that this
 * application be able to handle many different sets of tools. Tools can be
 * added and removed from the inventory at will.
 */
public class CheckoutService {

	private static List<Tool> inventory = new ArrayList<>();

	/**
	 * Add a single tool to the rental inventory
	 * 
	 * @param tool The tool to add
	 */
	public static void addToolToInventory(Tool tool) {
		inventory.add(tool);
	}

	/**
	 * Remove a single tool from the rental inventory
	 * 
	 * @param tool The tool to remove from the inventory
	 */
	public static void removeToolFromInventory(Tool tool) {
		inventory.remove(tool);
	}

	/**
	 * Creates a rental agreement based on the checkout information
	 * 
	 * @param code            The code for the tool being rented
	 * @param dayCount        The number of days the tool will be rented
	 * @param discountPercent The discount applied at checkout
	 * @param checkoutDate    The date that the rental will begin
	 * @return A RentalAgreement used to view all the information for a rental
	 */
	public static RentalAgreement checkout(String code, int dayCount, int discountPercent, LocalDate checkoutDate) {
		validateDayCount(dayCount);
		validateDiscount(discountPercent);

		final Tool toolToCheckout = findTool(code);
		validateTool(toolToCheckout, code);

		final int chargeDays = toolToCheckout.getNumberOfRentalDays(checkoutDate, dayCount);
		return new RentalAgreement(toolToCheckout, dayCount, checkoutDate, chargeDays, discountPercent);
	}

	/**
	 * Note: This can be done more elegantly with annotations, but I'm keeping
	 * things straightforward for the demo.
	 */
	private static void validateDayCount(int dayCount) {
		if (dayCount < 1) {
			throw new InvalidDayCountException(dayCount);
		}
	}

	/**
	 * Note: This can be done more elegantly with annotations, but I'm keeping
	 * things straightforward for the demo.
	 */
	private static void validateDiscount(int discount) {
		if (discount < 0 || discount > 100) {
			throw new InvalidDiscountException(discount);
		}
	}

	/**
	 * Note: This can be done more elegantly with annotations, but I'm keeping
	 * things straightforward for the demo.
	 */
	private static void validateTool(Tool toolToCheckout, String code) {
		if (toolToCheckout == null) {
			throw new ToolNotFoundException(code);
		}
	}

	/**
	 * Get a tool in the inventory by code
	 */
	private static Tool findTool(String code) {
		for (Tool tool : inventory) {
			if (tool.getCode().equals(code)) {
				return tool;
			}
		}
		return null;
	}

}
