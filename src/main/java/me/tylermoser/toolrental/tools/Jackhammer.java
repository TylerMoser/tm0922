package me.tylermoser.toolrental.tools;

/**
 * A child of Tool used to instantiate Jackhammers for the tool inventory
 */
public class Jackhammer extends Tool {

	private static final String TOOL_TYPE = "Jackhammer";
	private static final double DAILY_CHARGE = 2.99;
	private static final boolean WEEKDAY_CHARGE = true;
	private static final boolean WEEKEND_CHARGE = false;
	private static final boolean HOLIDAY_CHARGE = false;

	public Jackhammer(String code, String brand) {
		super(code, brand, TOOL_TYPE, DAILY_CHARGE, WEEKDAY_CHARGE, WEEKEND_CHARGE, HOLIDAY_CHARGE);
	}
}
