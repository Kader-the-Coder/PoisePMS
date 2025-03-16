package menu.options.manage;

import java.util.List;
import java.util.Scanner;

import records.*;
import utils.Utils;

/**
 * This class manages the workflow for interacting with existing projects.
 * It allows the user to find projects based on different criteria, such as incomplete status,
 * past deadlines, or unassigned personnel.
 */
public class ManageProject {
  private static final int DIVIDER_WIDTH = 100;

  /**
   * Initiates the project management process.
   * This method displays a menu of project management options and handles user input to manage projects.
   * Based on the user's choice, it finds projects or displays related project information.
   *
   * @param scanner The Scanner instance used for reading user input.
   */
  public static void manageProject(Scanner scanner) {
    List<Project> projects;
    while (true) {
      displayMainProjectMenu();
      String choice = scanner.nextLine();
      Utils.printDivider(DIVIDER_WIDTH);

      switch (choice) {
        case "1":
          projects = ProjectFinder.findProject(scanner);
          break;
        case "2":
          projects = ProjectGetter.getIncompleteProjects();
          break;
        case "3":
          projects = ProjectGetter.getProjectsPastDeadline();
          break;
        case "4":
          projects = ProjectGetter.getProjectsWithUnassignedPeople();
          break;
        case "0":
          System.out.println();
          return;
        default:
          handleInvalidInput();
          continue;
      }

      if (projects.isEmpty()) {
        Utils.printDivider(DIVIDER_WIDTH);
        System.out.println("No projects found matching your criteria.");
      }
      if (projects.size() == 1) {
        ProjectChanger.projectChanger(scanner, projects.getFirst());
      }
      else {
        System.out.println(projects.size() + " tasks match your criteria.\n" +
          "Refine your search or select a task by its Project Number (#).");
      }

      Utils.printDivider(DIVIDER_WIDTH);
    }
  }

  /**
   * Displays the main menu of options for managing projects.
   */
  private static void displayMainProjectMenu() {
    System.out.print("""
      1. Find a project
      2. View all incomplete projects
      3. View all projects past deadline
      4. View all projects with unassigned people
      0. Back
      
      Enter your choice:\s""");
  }

  /**
   * Handles invalid input by displaying an error message and a divider.
   */
  private static void handleInvalidInput() {
    System.out.println("Error: Invalid input. Please try again.");
    Utils.printDivider(DIVIDER_WIDTH);
  }
}
