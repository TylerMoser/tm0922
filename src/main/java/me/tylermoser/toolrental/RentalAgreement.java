package me.tylermoser.toolrental;

import static java.text.NumberFormat.getCurrencyInstance;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.US;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import me.tylermoser.toolrental.tools.Tool;

/**
 * The agreement between the tool rental company and the customer.
 * 
 * Generates a report that provides all information related to a tool rental.
 */
public class RentalAgreement {

	private static final DateTimeFormatter DATE_FORMATTER = ofPattern("MM/dd/yy");
	private static final NumberFormat CURRENCY_FORMATTER = getCurrencyInstance(US);

	private Tool tool;
	private int rentalDays;
	private LocalDate checkoutDate;
	private int chargeDays;
	private int discountPercent;

	public RentalAgreement(Tool tool, int rentalDays, LocalDate checkoutDate, int chargeDays, int discountPercent) {
		this.tool = tool;
		this.rentalDays = rentalDays;
		this.checkoutDate = checkoutDate;
		this.chargeDays = chargeDays;
		this.discountPercent = discountPercent;
	}

	/**
	 * I generally disagree with the practice of having methods print output
	 * directly, and I don't like using System.out instead of a logger, but for the
	 * sake of this demo I will keep things simple.
	 */
	public void printReport() {
		System.out.println("\n" + getReport() + "\n");
	}

	/**
	 * Return a report including all rental information
	 */
	public String getReport() {
		final StringBuilder reportBuilder = new StringBuilder();
		reportBuilder.append("Tool code: ");
		reportBuilder.append(getToolCode());
		reportBuilder.append("\nTool type: ");
		reportBuilder.append(getToolType());
		reportBuilder.append("\nTool brand: ");
		reportBuilder.append(getToolBrand());
		reportBuilder.append("\nRental days: ");
		reportBuilder.append(getRentalDays());
		reportBuilder.append("\nCheck out date: ");
		reportBuilder.append(getCheckoutDate());
		reportBuilder.append("\nDue date: ");
		reportBuilder.append(getDueDate());
		reportBuilder.append("\nDaily rental charge: ");
		reportBuilder.append(getDailyRentalCharge());
		reportBuilder.append("\nCharge days: ");
		reportBuilder.append(getChargeDays());
		reportBuilder.append("\nPre-discount charge: ");
		reportBuilder.append(getPreDiscountCharge());
		reportBuilder.append("\nDiscount percent: ");
		reportBuilder.append(getDiscountPercent());
		reportBuilder.append("%");
		reportBuilder.append("\nDiscount amount: ");
		reportBuilder.append(getDiscountAmount());
		reportBuilder.append("\nFinal charge: ");
		reportBuilder.append(getFinalCharge());
		return reportBuilder.toString();
	}

	/*
	 * I chose to return Strings from some of these "getters" where I would
	 * generally instead return primitives or other Objects. This was done to
	 * satisfy the demo prompt. By returning Strings, I can easily show in the unit
	 * tests that I am formatting the data correctly. Without that requirement, I
	 * wouldn't have returned Strings, and I would have been able to more
	 * effectively reuse the methods below.
	 * 
	 * I am not providing JavaDoc for the getters and setters because I have found
	 * that JavaDoc is applied differently on different teams. I have worked on
	 * teams where JavaDoc is required on everything, and teams where JavaDoc is
	 * frowned-upon. I am happy to follow the pattern of whatever team I am on, but
	 * for now I will provide JavaDoc for all methods EXCEPT getters and setters.
	 */

	public String toString() {
		return getReport();
	}

	public String getToolCode() {
		return tool.getCode();
	}

	public String getToolType() {
		return tool.getToolType();
	}

	public String getToolBrand() {
		return tool.getBrand();
	}

	public int getRentalDays() {
		return rentalDays;
	}

	public String getCheckoutDate() {
		return checkoutDate.format(DATE_FORMATTER);
	}

	public String getDueDate() {
		final LocalDate dueDate = checkoutDate.plusDays(rentalDays);
		return dueDate.format(DATE_FORMATTER);
	}

	public Object getDailyRentalCharge() {
		return CURRENCY_FORMATTER.format(tool.getDailyCharge());
	}

	public int getChargeDays() {
		return chargeDays;
	}

	public Object getPreDiscountCharge() {
		final double preDiscountCharge = chargeDays * tool.getDailyCharge();
		return CURRENCY_FORMATTER.format(preDiscountCharge);
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public String getDiscountAmount() {
		final double preDiscountCharge = chargeDays * tool.getDailyCharge();
		final double discountAmount = preDiscountCharge * ((double) discountPercent / 100);
		return CURRENCY_FORMATTER.format(discountAmount);
	}

	public String getFinalCharge() {
		final double preDiscountCharge = chargeDays * tool.getDailyCharge();
		final double discountAmount = preDiscountCharge * ((double) discountPercent / 100);
		final double finalCharge = preDiscountCharge - discountAmount;
		return CURRENCY_FORMATTER.format(finalCharge);
	}
}
