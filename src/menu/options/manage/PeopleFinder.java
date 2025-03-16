package menu.options.manage;

import records.*;
import database.Read;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The ProjectFinder class provides methods for searching and finding projects
 * based on project number or project name.
 */
public class PeopleFinder {
  private static final int DIVIDER_WIDTH = 100;

  /**
   * Initiates the project search process. This method displays a menu of search options and retrieves projects based on the user's input.
   *
   * @param scanner The scanner instance used for reading user input.
   * @return A list of projects that match the search criteria.
   */
  public static List<? extends Person> findPerson(Scanner scanner, Class<? extends Person> role) {
    int idNumber;
    String name;
    while (true) {
      displayFindPersonMenu(role);
      String choice = scanner.nextLine();
      Utils.printDivider(DIVIDER_WIDTH);

      switch (choice) {
        case "1":
          System.out.println("Enter the ID number...");
          idNumber = Utils.inputInteger(scanner, "number: ", false);
          name = "";
          break;
        case "2":
          System.out.println("Enter the project name (or part of it)...");
          name = Utils.inputString(scanner, "name: ");
          idNumber = -1;
          break;
        case "3":
          name = "";
          idNumber = -1;
          break;
        case "0":
          Utils.printDivider(DIVIDER_WIDTH);
          return new ArrayList<>();
        default:
          handleInvalidInput();
          continue;
      }

      List<? extends Person> people = Read.getAllPersons(role, new ArrayList<>());
      // Filter list to match selected ID
      if (name.isEmpty() && idNumber != -1) {
        people = people.stream()
          .filter(person -> person.id() == idNumber)
          .findFirst()
          .map(Collections::singletonList)
          .orElse(Collections.emptyList());
      // Filter list to match selected name
      } if (!name.isEmpty()) {
        people = people.stream()
          .filter(person -> person.name().toLowerCase().contains(name.toLowerCase()))
          .collect(Collectors.toList());
      }

      if (!people.isEmpty()) {
        System.out.println("Displaying selected " + role.getSimpleName() + "s...");
      }
      return people;
    }
  }

  /**
   * Displays the menu for searching projects by ID or name.
   */
  private static void displayFindPersonMenu(Class<? extends Person> role) {
    if (role == Architect.class) {
      System.out.println("Searching for Architect...");
    }
    else if (role == Contractor.class) {
      System.out.println("Searching for Contractor...");
    }
    else if (role == Customer.class) {
      System.out.println("Searching for Customer...");
    }
    else if (role == Engineer.class) {
      System.out.println("Searching for Engineer...");
    }
    else if (role == Manager.class) {
      System.out.println("Searching for Manager...");
    }
    else {
      throw new IllegalArgumentException("Invalid role provided: " + role.getName());
    }
    System.out.println();
    System.out.print("""
      1. Find by Number
      2. Find by Name
      3. Show all
      
      0. Back
      
      Enter your choice:\s""");
  }

  /**
   * Prompts the user to input a project number and returns a query condition based on the input.
   *
   * @param scanner The scanner instance used for reading user input.
   * @return The query condition to be used in the project search.
   */
  private static String findPersonByID(Scanner scanner, Class<? extends Person> role) {
    System.out.println("Enter the ID number...");
    int idNumber = Utils.inputInteger(scanner, "number: ", false);

    if (role == Architect.class) {
      return "architectID = " + idNumber;
    }
    else if (role == Contractor.class) {
      return "contractorID = " + idNumber;
    }
    else if (role == Customer.class) {
      return "customerID = " + idNumber;
    }
    else if (role == Engineer.class) {
      return "engineerID = " + idNumber;
    }
    else if (role == Manager.class) {
      return "managerID = " + idNumber;
    }
    else {
      throw new IllegalArgumentException("Invalid role provided: " + role.getName());
    }
  }

  /**
   * Prompts the user to input a project name (or part of it) and returns a query condition based on the input.
   *
   * @param scanner The scanner instance used for reading user input.
   * @return The query condition to be used in the project search.
   */
  private static String findPersonByName(Scanner scanner, Class<?> role) {
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
