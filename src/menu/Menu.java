package menu;

import java.util.Scanner;

import menu.options.capture.CaptureProject;
import menu.options.manage.ManageProject;
import menu.options.manage.ManagePeople;
import utils.Utils;

/**
 * This class manages the main menu of the program.
 */
public class Menu {
  private static final int DIVIDER_WIDTH = 100;

  /**
   * Displays the main menu for the PoisePMS system and handles user input.
   *
   * The menu allows the user to:
   * - Capture a new project
   * - Manage existing projects
   * - Exit the program
   *
   * The method continuously prompts the user for input until they choose to exit.
   */
  private Menu() {
    throw new UnsupportedOperationException("Utility class - cannot be instantiated");
  }

  public static void displayMenu() {
    Scanner scanner = new Scanner(System.in);
    String choice;

    Utils.printDivider(DIVIDER_WIDTH);
    // Displays the main menu to the user
    with:
    while (true) {
      System.out.print("""
          Welcome to the PoisePMS...
          1. Capture new project
          2. Manage existing projects
          3. Manage people
          
          0. Exit
          
          Enter your choice:\s""");
      // Get user choice
      choice = scanner.nextLine();
      Utils.printDivider(DIVIDER_WIDTH);

      // Handle user choice
      switch (choice) {
        // Capture new project
        case "1":
          CaptureProject.captureProject(scanner);
          Utils.printDivider(DIVIDER_WIDTH);
          break;
        // Manage existing projects
        case "2":
          ManageProject.manageProject(scanner);
          Utils.printDivider(DIVIDER_WIDTH);
          break;
        // Manage people
        case "3":
          ManagePeople.managePeople(scanner);
          Utils.printDivider(DIVIDER_WIDTH);
          break;
        // Exit the program
        case "0":
          System.out.println("Exiting program...");
          Utils.printDivider(DIVIDER_WIDTH);
          break with;
        // Invalid input
        default:
          System.out.println("Error: Invalid input.");
          Utils.printDivider(DIVIDER_WIDTH);
          break;
      }
    }
  }
}
