package me.tylermoser.toolrental;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import me.tylermoser.toolrental.exceptions.InvalidDiscountException;
import me.tylermoser.toolrental.tools.Chainsaw;
import me.tylermoser.toolrental.tools.Jackhammer;
import me.tylermoser.toolrental.tools.Ladder;

/**
 * Normally I would provide unit tests for each individual file, but the
 * requirements specifically state that the following tests be included. I am
 * going to refrain from adding additional tests to keep this demo
 * straightforward.
 * 
 * I'm keeping the format of these tests to JUnit 4, despite some of the
 * improvements of JUnit 5, since in my experience the majority of the industry
 * still hasn't made the switch.
 */
public class CheckoutServiceTest {

	private static final String CHNS = "CHNS";
	private static final String LADW = "LADW";
	private static final String JAKD = "JAKD";
	private static final String JAKR = "JAKR";
	private static final String JACKHAMMER = "Jackhammer";
	private static final String CHAINSAW = "Chainsaw";
	private static final String LADDER = "Ladder";
	private static final String STIHL = "Stihl";
	private static final String WERNER = "Werner";
	private static final String DEWALT = "DeWalt";
	private static final String RIDGID = "Ridgid";
	private static final LocalDate SEPT_THIRD_2015 = LocalDate.of(2015, 9, 3);
	private static final LocalDate JULY_SECOND_2020 = LocalDate.of(2020, 7, 2);
	private static final LocalDate JULY_SECOND_2015 = LocalDate.of(2015, 7, 2);

	@Before
	public void setup() {
		CheckoutService.addToolToInventory(new Chainsaw(CHNS, STIHL));
		CheckoutService.addToolToInventory(new Ladder(LADW, WERNER));
		CheckoutService.addToolToInventory(new Jackhammer(JAKD, DEWALT));
		CheckoutService.addToolToInventory(new Jackhammer(JAKR, RIDGID));
	}

	/**
	 * Test 1 <br>
	 * Tool Code: JAKR <br>
	 * Checkout Date: 9/3/15 <br>
	 * Rental Days: 5 <br>
	 * Discount: 101%
	 * 
	 * The discount percentage is more than the allowed maximum of 100%. A
	 * validation error is thrown, and a well-formatted message is logged.
	 */
	@Test(expected = InvalidDiscountException.class)
	public void checkout_ridgidJackhammer_onSeptThird2015_for5days_at101PercentDiscount_throwsValidationException() {
		try {
			CheckoutService.checkout(JAKR, 5, 101, SEPT_THIRD_2015);
		} catch (InvalidDiscountException e) {
			assertTrue(e.getMessage().contains("101"));
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * Test 2 <br>
	 * Tool Code: LADW <br>
	 * Checkout Date: 7/2/20 <br>
	 * Rental Days: 3 <br>
	 * Discount: 10%
	 * 
	 * 7/3: July 4th Observed: No charge for ladders <br>
	 * 7/4: Weekend: Charged <br>
	 * 7/5: Weekend: Charged <br>
	 */
	@Test
	public void checkout_ladder_onJulySecond2020_for3days_at10PercentDiscount_returnsValidRentalAgreementWithTotalCharge3_58() {
		final int rentalDays = 3;
		final int discountPercent = 10;

		RentalAgreement rentalAgreement = CheckoutService.checkout(LADW, rentalDays, discountPercent, JULY_SECOND_2020);

		assertEquals(LADW, rentalAgreement.getToolCode());
		assertEquals(LADDER, rentalAgreement.getToolType());
		assertEquals(WERNER, rentalAgreement.getToolBrand());
		assertEquals(rentalDays, rentalAgreement.getRentalDays());
		assertEquals("07/02/20", rentalAgreement.getCheckoutDate());
		assertEquals("07/05/20", rentalAgreement.getDueDate());
		assertEquals("$1.99", rentalAgreement.getDailyRentalCharge());
		assertEquals(2, rentalAgreement.getChargeDays());
		assertEquals("$3.98", rentalAgreement.getPreDiscountCharge());
		assertEquals(10, rentalAgreement.getDiscountPercent());
		assertEquals("$0.40", rentalAgreement.getDiscountAmount());
		assertEquals("$3.58", rentalAgreement.getFinalCharge());

		rentalAgreement.printReport();
	}

	/**
	 * Test 3 <br>
	 * Tool Code: CHNS <br>
	 * Checkout Date: 7/2/15 <br>
	 * Rental Days: 5 <br>
	 * Discount: 25%
	 * 
	 * 7/3: July 4th Observed: Charged <br>
	 * 7/4: Weekend: No charge for chainsaws <br>
	 * 7/5: Weekend: No charge for chainsaws <br>
	 * 7/6: Weekday: Charged <br>
	 * 7/7: Weekday: Charged <br>
	 */
	@Test
	public void checkout_chainsaw_onJulySecond2015_for5days_at25PercentDiscount_returnsValidRentalAgreementWithTotalCharge3_35() {
		final int rentalDays = 5;
		final int discountPercent = 25;

		RentalAgreement rentalAgreement = CheckoutService.checkout(CHNS, rentalDays, discountPercent, JULY_SECOND_2015);

		// The prompt states that the Fourth of July is observed on the closest
		// weekday. There is no exception for tools that do not charge on weekends, but
		// do charge on holidays. This means that all holidays will always be weekdays.
		// The daily rental fee is always charged for weekdays.
		assertEquals(CHNS, rentalAgreement.getToolCode());
		assertEquals(CHAINSAW, rentalAgreement.getToolType());
		assertEquals(STIHL, rentalAgreement.getToolBrand());
		assertEquals(rentalDays, rentalAgreement.getRentalDays());
		assertEquals("07/02/15", rentalAgreement.getCheckoutDate());
		assertEquals("07/07/15", rentalAgreement.getDueDate());
		assertEquals("$1.49", rentalAgreement.getDailyRentalCharge());
		assertEquals(3, rentalAgreement.getChargeDays());
		assertEquals("$4.47", rentalAgreement.getPreDiscountCharge());
		assertEquals(25, rentalAgreement.getDiscountPercent());
		assertEquals("$1.12", rentalAgreement.getDiscountAmount());
		assertEquals("$3.35", rentalAgreement.getFinalCharge());

		rentalAgreement.printReport();
	}

	/**
	 * Test 4 <br>
	 * Tool Code: JAKD <br>
	 * Checkout Date: 9/3/15 <br>
	 * Rental Days: 6 <br>
	 * Discount: 0%
	 * 
	 * 9/4: Weekday: Charged <br>
	 * 9/5: Weekend: No charge for jackhammers <br>
	 * 9/6: Weekend: No charge for jackhammers <br>
	 * 9/7: Labor Day: No charge for jackhammers <br>
	 * 9/8: Weekday: Charged <br>
	 * 9/9: Weekday: Charged
	 */
	@Test
	public void checkout_deWaltJackhammer_onSeptThird2015_for6days_returnsValidRentalAgreementWithTotalCharge8_97() {
		final int rentalDays = 6;
		final int discountPercent = 0;

		RentalAgreement rentalAgreement = CheckoutService.checkout(JAKD, rentalDays, discountPercent, SEPT_THIRD_2015);

		assertEquals(JAKD, rentalAgreement.getToolCode());
		assertEquals(JACKHAMMER, rentalAgreement.getToolType());
		assertEquals(DEWALT, rentalAgreement.getToolBrand());
		assertEquals(rentalDays, rentalAgreement.getRentalDays());
		assertEquals("09/03/15", rentalAgreement.getCheckoutDate());
		assertEquals("09/09/15", rentalAgreement.getDueDate());
		assertEquals("$2.99", rentalAgreement.getDailyRentalCharge());
		assertEquals(3, rentalAgreement.getChargeDays());
		assertEquals("$8.97", rentalAgreement.getPreDiscountCharge());
		assertEquals(0, rentalAgreement.getDiscountPercent());
		assertEquals("$0.00", rentalAgreement.getDiscountAmount());
		assertEquals("$8.97", rentalAgreement.getFinalCharge());

		rentalAgreement.printReport();
	}

	/**
	 * Test 5 <br>
	 * Tool Code: JAKR <br>
	 * Checkout Date: 7/2/15 <br>
	 * Rental Days: 9 <br>
	 * Discount: 0%
	 * 
	 * 7/3: July 4th Observed: No charge for jackhammers <br>
	 * 7/4: Weekend: No charge for jackhammers <br>
	 * 7/5: Weekend: No charge for jackhammers <br>
	 * 7/6: Weekday: Charged <br>
	 * 7/7: Weekday: Charged <br>
	 * 7/8: Weekday: Charged <br>
	 * 7/9: Weekday: Charged <br>
	 * 7/10: Weekday: Charged <br>
	 * 7/11: Weekend: No charge for jackhammers <br>
	 */
	@Test
	public void checkout_ridgidJackhammer_onJulySecond2015_for9days_returnsValidRentalAgreementWithTotalCharge14_95() {
		final int rentalDays = 9;
		final int discountPercent = 0;

		RentalAgreement rentalAgreement = CheckoutService.checkout(JAKR, rentalDays, discountPercent, JULY_SECOND_2015);

		assertEquals(JAKR, rentalAgreement.getToolCode());
		assertEquals(JACKHAMMER, rentalAgreement.getToolType());
		assertEquals(RIDGID, rentalAgreement.getToolBrand());
		assertEquals(rentalDays, rentalAgreement.getRentalDays());
		assertEquals("07/02/15", rentalAgreement.getCheckoutDate());
		assertEquals("07/11/15", rentalAgreement.getDueDate());
		assertEquals("$2.99", rentalAgreement.getDailyRentalCharge());
		assertEquals(5, rentalAgreement.getChargeDays());
		assertEquals("$14.95", rentalAgreement.getPreDiscountCharge());
		assertEquals(0, rentalAgreement.getDiscountPercent());
		assertEquals("$0.00", rentalAgreement.getDiscountAmount());
		assertEquals("$14.95", rentalAgreement.getFinalCharge());

		rentalAgreement.printReport();
	}

	/**
	 * Test 6 <br>
	 * Tool Code: JAKR <br>
	 * Checkout Date: 7/2/20 <br>
	 * Rental Days: 4 <br>
	 * Discount: 50%
	 * 
	 * 7/3: July 4th Observed: No charge for jackhammers <br>
	 * 7/4: Weekend: No charge for jackhammers <br>
	 * 7/5: Weekend: No charge for jackhammers <br>
	 * 7/6: Weekday: Charged <br>
	 */
	@Test
	public void checkout_ridgidJackhammer_onJulySecond2020_for4days_at50PercentDiscount_returnsValidRentalAgreementWithTotalCharge1_50() {
		final int rentalDays = 4;
		final int discountPercent = 50;

		RentalAgreement rentalAgreement = CheckoutService.checkout(JAKR, rentalDays, discountPercent, JULY_SECOND_2020);

		assertEquals(JAKR, rentalAgreement.getToolCode());
		assertEquals(JACKHAMMER, rentalAgreement.getToolType());
		assertEquals(RIDGID, rentalAgreement.getToolBrand());
		assertEquals(rentalDays, rentalAgreement.getRentalDays());
		assertEquals("07/02/20", rentalAgreement.getCheckoutDate());
		assertEquals("07/06/20", rentalAgreement.getDueDate());
		assertEquals("$2.99", rentalAgreement.getDailyRentalCharge());
		assertEquals(1, rentalAgreement.getChargeDays());
		assertEquals("$2.99", rentalAgreement.getPreDiscountCharge());
		assertEquals(50, rentalAgreement.getDiscountPercent());
		assertEquals("$1.50", rentalAgreement.getDiscountAmount());
		assertEquals("$1.50", rentalAgreement.getFinalCharge());

		rentalAgreement.printReport();
	}
}
