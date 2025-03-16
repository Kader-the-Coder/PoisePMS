package utils;

import records.Person;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class containing helper methods for input validation and formatting.
 * Includes methods for handling different types of user inputs such as integers, doubles, currency, strings, and dates.
 */
public class Utils {

  /**
   * Prints a divider line of a specified width using hyphens.
   *
   * @param width The width of the divider.
   */
  public static void printDivider(int width) {
    System.out.println("-".repeat(width));
  }

  /**
   * Prints a divider line of a specified width using a custom symbol.
   *
   * @param width The width of the divider.
   * @param symbol The symbol to use for the divider.
   */
  public static void printDivider(int width, String symbol) {
    System.out.println(symbol.repeat(width));
  }

  /**
   * Prompts the user for an integer input and validates it.
   * The input can optionally be negative depending on the canBeNegative flag.
   *
   * @param scanner The scanner object used to read input.
   * @param prompt The prompt message to display to the user.
   * @param canBeNegative Flag indicating whether the number can be negative.
   * @return The valid integer input from the user.
   */
  public static int inputInteger(Scanner scanner, String prompt, boolean canBeNegative) {
    while (true) {
      try {
        System.out.print(prompt);
        int number = scanner.nextInt();
        if (number < 0 && !canBeNegative) {
          throw new InputMismatchException();
        }
        scanner.nextLine(); // Consume the leftover newline
        return number;
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a positive integer.");
        scanner.nextLine(); // Flush invalid input
      }
    }
  }

  /**
   * Prompts the user for a double input and validates it.
   * The input can optionally be negative depending on the canBeNegative flag.
   *
   * @param scanner The scanner object used to read input.
   * @param prompt The prompt message to display to the user.
   * @param canBeNegative Flag indicating whether the number can be negative.
   * @return The valid double input from the user.
   */
  public static double inputDouble(Scanner scanner, String prompt, boolean canBeNegative) {
    while (true) {
      try {
        System.out.print(prompt);
        String input = scanner.next();
        double number = Double.parseDouble(input);
        if (number < 0 && !canBeNegative) {
          throw new InputMismatchException();
        }
        scanner.nextLine(); // Consume the leftover newline
        return number;
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a valid amount.");
        scanner.nextLine(); // Flush invalid input
      }
    }
  }

  /**
   * Prompts the user for a currency input and validates it as a positive amount.
   *
   * @param scanner The scanner object used to read input.
   * @param prompt The prompt message to display to the user.
   * @return The valid BigDecimal input representing the currency.
   */
  public static BigDecimal inputCurrency(Scanner scanner, String prompt) {
    while (true) {
      try {
        System.out.print(prompt);
        String input = scanner.nextLine().strip();

        // Parse input as BigDecimal
        BigDecimal number = new BigDecimal(input);

        // Check if the number is negative
        if (number.compareTo(BigDecimal.ZERO) < 0) {
          throw new InputMismatchException("Number must be positive");
        }

        return number;

      } catch (NumberFormatException | InputMismatchException e) {
        System.out.println("Invalid input. Please enter a positive amount.");
      }
    }
  }

  /**
   * Prompts the user for a non-empty string input.
   *
   * @param scanner The scanner object used to read input.
   * @param prompt The prompt message to display to the user.
   * @return The valid string input from the user.
   */
  public static String inputString(Scanner scanner, String prompt) {
    while (true) {
      try {
        System.out.print(prompt);
        String input = scanner.nextLine().strip();
        if (input.isEmpty()) {
          throw new IllegalArgumentException("Input cannot be empty.");
        }
        return input;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Prompts the user for a string input, allowing empty input if specified.
   *
   * @param scanner The scanner object used to read input.
   * @param prompt The prompt message to display to the user.
   * @param canBeEmpty Flag indicating whether the input can be empty.
   * @return The valid string input from the user.
   */
  public static String inputString(Scanner scanner, String prompt, boolean canBeEmpty) {
    while (true) {
      try {
        System.out.print(prompt);
        String input = scanner.nextLine().strip();

        // Check if input is empty when it shouldn't be
        if (input.isEmpty() && !canBeEmpty) {
          throw new IllegalArgumentException("Input cannot be empty.");
        }

        return input;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Prompts the user for a valid date input and validates it. The date must be later than today's date.
   *
   * @param scanner The scanner object used to read input.
   * @param prompt The prompt message to display to the user.
   * @return The valid Date object.
   */
  public static Date getValidDate(Scanner scanner, String prompt) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    boolean isValidDate = false;
    String inputDate;
    Date validDate = null;
    Date currentDate = new Date(); // Current date

    // Enforcing strict validation
    dateFormat.setLenient(false);

    // Loop until a valid date is entered
    while (!isValidDate) {
      System.out.print(prompt);
      inputDate = scanner.nextLine();

      try {
        // Try to parse the input date
        validDate = dateFormat.parse(inputDate);

        // Check if the entered date is earlier than the current date
        if (validDate.before(currentDate)) {
          System.out.println("The date cannot be earlier than today's date. Please enter a valid date.");
        } else {
          isValidDate = true; // Valid date is entered
        }
      } catch (ParseException e) {
        System.out.println("Invalid date format. Please enter a valid date (yyyy-MM-dd).");
      }
    }

    return validDate;
  }

  /**
   * Extracts the IDs of a list of persons.
   *
   * @param persons The list of persons whose IDs are to be extracted.
   * @return A list of integer IDs of the persons.
   */
  public static List<Integer> extractIds(List<? extends Person> persons) {
    return persons.stream()
      .map(Person::id)
      .toList();
  }
}
