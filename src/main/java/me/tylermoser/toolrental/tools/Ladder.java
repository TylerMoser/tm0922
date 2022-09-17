package me.tylermoser.toolrental.tools;

/**
 * A child of Tool used to instantiate Ladders for the tool inventory
 */
public class Ladder extends Tool {

	private static final String TOOL_TYPE = "Ladder";
	private static final double DAILY_CHARGE = 1.99;
	private static final boolean WEEKDAY_CHARGE = true;
	private static final boolean WEEKEND_CHARGE = true;
	private static final boolean HOLIDAY_CHARGE = false;

	public Ladder(String code, String brand) {
		super(code, brand, TOOL_TYPE, DAILY_CHARGE, WEEKDAY_CHARGE, WEEKEND_CHARGE, HOLIDAY_CHARGE);
	}
}
