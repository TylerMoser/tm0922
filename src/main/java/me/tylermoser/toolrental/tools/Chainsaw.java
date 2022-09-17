package me.tylermoser.toolrental.tools;

/**
 * A child of Tool used to instantiate Chainsaws for the tool inventory
 */
public class Chainsaw extends Tool {

	private static final String TOOL_TYPE = "Chainsaw";
	private static final double DAILY_CHARGE = 1.49;
	private static final boolean WEEKDAY_CHARGE = true;
	private static final boolean WEEKEND_CHARGE = false;
	private static final boolean HOLIDAY_CHARGE = true;

	public Chainsaw(String code, String brand) {
		super(code, brand, TOOL_TYPE, DAILY_CHARGE, WEEKDAY_CHARGE, WEEKEND_CHARGE, HOLIDAY_CHARGE);
	}
}
