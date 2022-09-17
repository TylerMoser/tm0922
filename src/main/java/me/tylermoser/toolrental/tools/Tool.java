package me.tylermoser.toolrental.tools;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.LocalDate.of;
import static java.time.Month.JULY;
import static java.time.Month.SEPTEMBER;
import static java.time.temporal.TemporalAdjusters.firstInMonth;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * An abstract parent class for all tools
 */
public abstract class Tool {

	private String code;
	private String brand;
	private String toolType;
	private double dailyCharge;
	private boolean weekdayCharge;
	private boolean weekendCharge;
	private boolean holidayCharge;

	public Tool(String code, String brand, String toolType, double dailyCharge, boolean weekdayCharge,
			boolean weekendCharge, boolean holidayCharge) {
		this.code = code;
		this.brand = brand;
		this.toolType = toolType;
		this.dailyCharge = dailyCharge;
		this.weekdayCharge = weekdayCharge;
		this.weekendCharge = weekendCharge;
		this.holidayCharge = holidayCharge;
	}

	/**
	 * Determines how many days to charge for during the rental period
	 * 
	 * Notes: In financial applications, I generally use BigDecimal over double, but
	 * I am going to keep this simplistic here.
	 * 
	 * @param checkoutDate The date that the rental begins
	 * @param dayCount     The number of days before the rental must be returned
	 * @return The number of days to charge the customer for
	 */
	public int getNumberOfRentalDays(LocalDate checkoutDate, int dayCount) {
		// The prompt states that rental days are measured from the day after checkout
		// through and including the return date. Java's LocalDate::datesUntil starts of
		// the first day and does not include the final day. This shifts the dates
		// forward 1 day.
		checkoutDate = checkoutDate.plusDays(1);

		// @formatter:off
		return (int) checkoutDate
				.datesUntil(checkoutDate.plusDays(dayCount))
				.map(date -> isRentalDay(date))
				.filter(isRentalDay -> isRentalDay == true)
				.count();
		// @formatter:on
	}

	/**
	 * Returns true if the rental fee should be charged for a day
	 */
	private boolean isRentalDay(LocalDate date) {
		if (!isWeekendCharge() && isWeekend(date)) {
			return false;
		} else if (!isHolidayCharge() && isHoliday(date)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns true if the date occurs on a weekend
	 */
	private boolean isWeekend(LocalDate date) {
		return DayOfWeek.SATURDAY.equals(date.getDayOfWeek()) || DayOfWeek.SUNDAY.equals(date.getDayOfWeek());
	}

	/**
	 * Returns true if the date is a Holiday
	 */
	private boolean isHoliday(LocalDate date) {
		return isObservedFourthOfJuly(date) || isLaborDay(date);
	}

	/**
	 * Returns true if the date is the 4th of July or the date that the 4th of July
	 * is being observed that year.
	 */
	private boolean isObservedFourthOfJuly(LocalDate date) {
		int dateYear = date.getYear();
		LocalDate julyFourth = of(dateYear, JULY, 4);
		if (julyFourth.getDayOfWeek().equals(SATURDAY)) {
			julyFourth = of(dateYear, JULY, 3);
		} else if (julyFourth.getDayOfWeek().equals(SUNDAY)) {
			julyFourth = of(dateYear, JULY, 5);
		}
		return date.equals(julyFourth);
	}

	/**
	 * REturns true if the date is labor day
	 */
	private boolean isLaborDay(LocalDate date) {
		LocalDate laborDay = of(date.getYear(), SEPTEMBER, 1).with(firstInMonth(MONDAY));
		return date.equals(laborDay);
	}

	/*
	 * I am not providing JavaDoc for the getters and setters because I have found
	 * that JavaDoc is applied differently on different teams. I have worked on
	 * teams where JavaDoc is required on everything, and teams where JavaDoc is
	 * frowned-upon. I am happy to follow the pattern of whatever team I am on, but
	 * for now I will provide JavaDoc for all methods EXCEPT getters and setters.
	 */

	public String getCode() {
		return code;
	}

	public String getToolType() {
		return toolType;
	}

	public String getBrand() {
		return brand;
	}

	public double getDailyCharge() {
		return dailyCharge;
	}

	public boolean isWeekdayCharge() {
		return weekdayCharge;
	}

	public boolean isWeekendCharge() {
		return weekendCharge;
	}

	public boolean isHolidayCharge() {
		return holidayCharge;
	}

}
