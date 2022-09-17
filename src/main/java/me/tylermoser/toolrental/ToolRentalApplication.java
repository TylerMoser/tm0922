package me.tylermoser.toolrental;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import me.tylermoser.toolrental.exceptions.ToolRentalValidationException;
import me.tylermoser.toolrental.tools.Chainsaw;
import me.tylermoser.toolrental.tools.Jackhammer;
import me.tylermoser.toolrental.tools.Ladder;

/**
 * This command line user interface was added purely for my self-interest. The
 * demo requirements state that a user interface is not required. Please
 * disregard this file from evaluation.
 * 
 * If you are looking for the entrypoint into the main application logic, that
 * can be found in CheckoutService.
 */
public class ToolRentalApplication {

	private static final Scanner SCANNER = new Scanner(System.in);
	private static final DateTimeFormatter DATE_FORMATTER = ofPattern("MM/dd/yy");

	public static void main(String[] args) {
		CheckoutService.addToolToInventory(new Chainsaw("CHNS", "Stihl"));
		CheckoutService.addToolToInventory(new Ladder("LADW", "Werner"));
		CheckoutService.addToolToInventory(new Jackhammer("JAKD", "DeWalt"));
		CheckoutService.addToolToInventory(new Jackhammer("JAKR", "Ridgid"));

		do {
			checkoutTool();
			System.out.print("Would you like to checkout another tool? (yes/no): ");
		} while (SCANNER.nextBoolean());
	}

	private static void checkoutTool() {
		try {
			System.out.print("Enter the code for the tool you would like to checkout: ");
			String toolCode = SCANNER.next();
			System.out.print("Enter the number of days to rent the tool for: ");
			int dayCount = SCANNER.nextInt();
			System.out.print("Enter the discount percentage as a whole number: ");
			int discountPercent = SCANNER.nextInt();
			System.out.print("Enter the date you would like to checkout the tool on in the format MM/DD/YY: ");
			LocalDate checkoutDate = LocalDate.parse(SCANNER.next(), DATE_FORMATTER);

			RentalAgreement rentalAgreement = CheckoutService.checkout(toolCode, dayCount, discountPercent,
					checkoutDate);
			rentalAgreement.printReport();
		} catch (ToolRentalValidationException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("An unexpected error has occurred.");
			e.printStackTrace();
		}
	}
}
