package menu.options.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import records.Project;
import utils.Utils;
import static database.Read.readProjects;
import static utils.outputs.DisplayProjects.displayProjectsTable;

/**
 * The ProjectFinder class provides methods for searching and finding projects
 * based on project number or project name.
 */
public class ProjectFinder {
  private static final int DIVIDER_WIDTH = 100;

  /**
   * Initiates the project search process. This method displays a menu of search options and retrieves projects based on the user's input.
   *
   * @param scanner The scanner instance used for reading user input.
   * @return A list of projects that match the search criteria.
   */
  public static List<Project> findProject(Scanner scanner) {
    while (true) {
      displayFindProjectMenu();
      String choice = scanner.nextLine();
      Utils.printDivider(DIVIDER_WIDTH);

      String condition;
      switch (choice) {
        case "1":
          condition = findProjectByID(scanner);
          break;
        case "2":
          condition = findProjectByName(scanner);
          break;
        case "0":
          Utils.printDivider(DIVIDER_WIDTH);
          return new ArrayList<>();
        default:
          handleInvalidInput();
          continue;
      }

      List<Project> projects = readProjects("AND", condition);
      displayProjectsTable(projects, DIVIDER_WIDTH,
        "Project Name", "Building Type", "Physical Address", "ERF No.",
        "Total Fee", "Start Date", "Deadline", "Customer");
      return projects;
    }
  }

  /**
   * Displays the menu for searching projects by ID or name.
   */
  private static void displayFindProjectMenu() {
    System.out.print("""
            1. Find by Number
            2. Find by Name
            
            0. Back
            
            Enter your choice:\s""");
  }

  /**
   * Prompts the user to input a project number and returns a query condition based on the input.
   *
   * @param scanner The scanner instance used for reading user input.
   * @return The query condition to be used in the project search.
   */
  private static String findProjectByID(Scanner scanner) {
    System.out.println("Enter the project number...");
    int projectNumber = Utils.inputInteger(scanner, "number: ", false);
    return "ProjectNumber = " + projectNumber;
  }

  /**
   * Prompts the user to input a project name (or part of it) and returns a query condition based on the input.
   *
   * @param scanner The scanner instance used for reading user input.
   * @return The query condition to be used in the project search.
   */
  private static String findProjectByName(Scanner scanner) {
    System.out.println("Enter the project name (or part of it)...");
    String projectName = Utils.inputString(scanner, "name: ").replace("'", "''");
    return "ProjectName LIKE '%" + projectName + "%'";
  }

  /**
   * Handles invalid input by displaying an error message and a divider.
   */
  private static void handleInvalidInput() {
    System.out.println("Error: Invalid input. Please try again.");
    Utils.printDivider(DIVIDER_WIDTH);
  }
}
