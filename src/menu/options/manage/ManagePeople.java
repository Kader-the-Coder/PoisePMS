package menu.options.manage;

import records.*;
import database.DatabaseManager;
import database.Delete;
import database.Read;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static menu.options.manage.PeopleFinder.findPerson;
import static utils.outputs.DisplayPersons.displayPersonsTable;

/**
 * This class manages the workflow for interacting with existing people.
 * It allows the user to find people based on different criteria.
 */
public class ManagePeople {
  private static final int DIVIDER_WIDTH = 100;

  /**
   * Initiates the person management process.
   * This method displays a menu of people management options and handles user input to manage them.
   * Based on the user's choice, it finds people or displays related project information.
   *
   * @param scanner The Scanner instance used for reading user input.
   */
  public static void managePeople(Scanner scanner) {
    List<? extends Person> people;
    while (true) {
      displayMainPeopleMenu();
      String choice = scanner.nextLine();
      Utils.printDivider(DIVIDER_WIDTH);

      switch (choice) {
        case "1":
          people = findPerson(scanner, Manager.class);
          break;
        case "2":
          people = findPerson(scanner, Contractor.class);
          break;
        case "3":
          people = findPerson(scanner, Engineer.class);
          break;
        case "4":
          people = findPerson(scanner, Architect.class);
          break;
        case "5":
          people = findPerson(scanner, Customer.class);
          break;
        case "0":
          System.out.println();
          return;
        default:
          handleInvalidInput();
          continue;
      }

      if (people.isEmpty()) {
        Utils.printDivider(DIVIDER_WIDTH);
        System.out.println("No people found.");
        Utils.printDivider(DIVIDER_WIDTH);
        continue;
      }

      displayPersonsTable(people, DIVIDER_WIDTH, "#", "Name", "Telephone Number", "Email Address", "Physical Address");
      if (people.size() == 1) {
        Person person = people.getFirst();
        if (person.getClass() == Customer.class) {
          String customerQuery = "SELECT * FROM projects WHERE CustomerID = ?";
          int numberOfProjects = DatabaseManager.executeQuery(customerQuery, person.id()).size();
          System.out.println("WARNING! Deleting a customer will also delete all of their projects.");
          System.out.println("'" + person.name() + "' has " + numberOfProjects + " projects registered to their name.");
        }
        System.out.println("Are you sure you would wish to delete this person?");
        System.out.print("y/n [n]: ");
        choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("y")) {
          Delete.deletePerson(person);
        } else {
          System.out.println("Operation cancelled.");
        }

      }
      else {
        System.out.println(people.size() + " people match your criteria.\n" +
          "Refine your search or select a person by their ID Number (#).");
      }

      Utils.printDivider(DIVIDER_WIDTH);
    }
  }

   /**
   * Retrieves the person assigned to the specified role for the project.
   *
   * @param role The role of the person (e.g., "contractor", "architect").
   * @param project The project whose role is being retrieved.
   * @return The person assigned to the specified role, or null if no one is assigned.
   */
  private static Person getPersonByRole(String role, Project project) {
    return switch (role) {
      case "contractor" -> project.contractor();
      case "architect" -> project.architect();
      case "engineer" -> project.engineer();
      case "manager" -> project.manager();
      case "customer" -> project.customer();
      default -> null;
    };
  }

  /**
   * Retrieves the class type of the person based on their role.
   *
   * @param role The role of the person (e.g., "contractor", "architect").
   * @return The class type corresponding to the person's role.
   */
  public static Class<? extends Person> getClassByRole(String role) {
    return switch (role.toLowerCase()) {
      case "contractor" -> Contractor.class;
      case "architect" -> Architect.class;
      case "engineer" -> Engineer.class;
      case "manager" -> Manager.class;
      case "customer" -> Customer.class;
      default -> throw new IllegalArgumentException("Invalid role: " + role);
    };
  }

  /**
   * Updates the assigned person (contractor, architect, engineer, manager, or customer) for the project.
   *
   * @param scanner The scanner instance used for reading user input.
   * @param project The project whose assigned person is being updated.
   * @param field The field in the database representing the person's role.
   * @param role The role of the person being assigned (e.g., "contractor").
   */
  public static void updateAssignedPerson(Scanner scanner, Project project, String field, String role) {
    Person currentPerson = getPersonByRole(role, project);
    List<? extends Person> persons;
    if (currentPerson == null) {
      persons = Read.getAllPersons(getClassByRole(role), new ArrayList<>());
    } else {
      List<Person> excludedPersons = List.of(currentPerson);
      persons = Read.getAllPersons(getClassByRole(role), Utils.extractIds(excludedPersons));
    }

    if (persons.isEmpty()) {
      System.out.println("No available " + role + "s to assign.");
      return;
    }

    System.out.println("Available " + role + "s:");
    displayPersonsTable(persons, DIVIDER_WIDTH);

    System.out.println("Please select a " + role + " by id (#) to assign...");
    int newPersonID = Utils.inputInteger(scanner, ": ", false);

    if (persons.stream().noneMatch(person -> person.id() == newPersonID)) {
      System.out.println("Invalid id. Please input a valid " + role + " id.");
      return;
    }

    String query = "UPDATE projects SET " + field + " = ? WHERE ProjectNumber = ?";
    DatabaseManager.executeUpdate(query, newPersonID, project.projectNumber());
    System.out.println(role + " has been updated successfully.");
  }

  /**
   * Displays the main menu of options for managing people.
   */
  private static void displayMainPeopleMenu() {
    System.out.print("""
      Select the type of people to manage...
      1. Managers
      2. contractors
      3. Engineers
      4. Architects
      5. Customers
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
