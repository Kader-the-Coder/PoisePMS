package menu.options.manage;

import records.*;
import database.DatabaseManager;

import utils.Utils;

import java.util.Scanner;

import static menu.options.manage.ManagePeople.updateAssignedPerson;

/**
 * The ProjectChanger class provides methods to manage and update the details of a project.
 * This includes updating fields like project name, type, address, ERF number, total fee, assigned personnel, and finalised status.
 */
public class ProjectChanger {
  private static final int DIVIDER_WIDTH = 100;

  /**
   * Initiates the project update process. This method displays a menu of fields to be updated and handles user input to modify a project's details.
   *
   * @param scanner The scanner instance used for reading user input.
   * @param project The project to be updated.
   */
  public static void projectChanger(Scanner scanner, Project project) {
    while (true) {
      displayMainMenu();
      String choice = scanner.nextLine();
      Utils.printDivider(DIVIDER_WIDTH);
      switch (choice) {
        case "1" -> updateProjectName(scanner, project);
        case "2" -> updateProjectType(scanner, project);
        case "3" -> updatePhysicalAddress(scanner, project);
        case "4" -> updateERFNumber(scanner, project);
        case "5" -> updateTotalFee(scanner, project);
        case "6" -> updateAmountPaidToDate(scanner, project);
        case "7" -> updateDeadline(scanner, project);
        case "8" -> updateFinalised(scanner, project);
        case "9" -> updateAssignedPerson(scanner, project, "ManagerID", "manager");
        case "10" -> updateAssignedPerson(scanner, project, "EngineerID", "engineer");
        case "11" -> updateAssignedPerson(scanner, project, "ArchitectID", "architect");
        case "12" -> updateAssignedPerson(scanner, project, "ContractorID", "contractor");
        case "13" -> {deleteProject(scanner, project); return;}

        // Return to previous menu
        case "0" -> {
          System.out.println();
          return;
        }
        // Handle invalid inputs
        default -> handleInvalidInput();
      }
    }
  }



  /**
   * Updates the finalised status of the project and sets the completion date if applicable.
   *
   * @param scanner The scanner instance used for reading user input.
   * @param project The project whose finalised status is being updated.
   */
  private static void updateFinalised(Scanner scanner, Project project) {
    System.out.println("Is the project finalised? (yes/no): ");
    boolean isFinalised = scanner.nextLine().trim().equalsIgnoreCase("yes");

    // Set to finalised
    String query = "UPDATE projects SET Finalised = ? WHERE ProjectNumber = ?";
    DatabaseManager.executeUpdate(query, isFinalised, project.projectNumber());

    // Set or unset CompletionDate if finalised or not
    if (isFinalised) {
      query = "UPDATE projects SET CompletionDate = CURDATE() WHERE ProjectNumber = ?";
    } else {
      query = "UPDATE projects SET CompletionDate = NULL WHERE ProjectNumber = ?";
    }
    DatabaseManager.executeUpdate(query, project.projectNumber());

    System.out.println("Project finalised status updated.");
  }

  /**
   * Updates the deadline for the project.
   *
   * @param scanner The scanner instance used for reading user input.
   * @param project The project whose deadline is being updated.
   */
  private static void updateDeadline(Scanner scanner, Project project) {
    System.out.println("Enter new deadline (YYYY-MM-DD): ");
    String newDeadline = scanner.nextLine().trim();
    String query = "UPDATE projects SET Deadline = ? WHERE ProjectNumber = ?";
    DatabaseManager.executeUpdate(query, newDeadline, project.projectNumber());
    System.out.println("Deadline updated successfully.");
  }

  /**
   * Updates the amount paid to date for the project.
   *
   * @param scanner The scanner instance used for reading user input.
   * @param project The project whose amount paid to date is being updated.
   */
  private static void updateAmountPaidToDate(Scanner scanner, Project project) {
    System.out.println("Enter new amount paid to date: ");
    double newAmount = Utils.inputDouble(scanner, ": ", false);
    String query = "UPDATE projects SET AmountPaidToDate = ? WHERE ProjectNumber = ?";
    DatabaseManager.executeUpdate(query, newAmount, project.projectNumber());
    System.out.println("Amount paid updated successfully.");
  }

  /**
   * Updates the total fee for the project.
   *
   * @param scanner The scanner instance used for reading user input.
   * @param project The project whose total fee is being updated.
   */
  private static void updateTotalFee(Scanner scanner, Project project) {
    System.out.println("Enter new total fee: ");
    double newFee = Utils.inputDouble(scanner, ": ", false);
    String query = "UPDATE projects SET TotalFee = ? WHERE ProjectNumber = ?";
    DatabaseManager.executeUpdate(query, newFee, project.projectNumber());
    System.out.println("Total fee updated successfully.");
  }

  /**
   * Updates the ERF number for the project.
   *
   * @param scanner The scanner instance used for reading user input.
   * @param project The project whose ERF number is being updated.
   */
  private static void updateERFNumber(Scanner scanner, Project project) {
    System.out.println("Enter new ERF number: ");
    String newERF = scanner.nextLine().trim();
    String query = "UPDATE projects SET ERFNumber = ? WHERE ProjectNumber = ?";
    DatabaseManager.executeUpdate(query, newERF, project.projectNumber());
    System.out.println("ERF number updated successfully.");
  }

  /**
   * Updates the physical address for the project.
   *
   * @param scanner The scanner instance used for reading user input.
   * @param project The project whose physical address is being updated.
   */
  private static void updatePhysicalAddress(Scanner scanner, Project project) {
    System.out.println("Enter new physical address: ");
    String newAddress = scanner.nextLine().trim();
    String query = "UPDATE projects SET PhysicalAddress = ? WHERE ProjectNumber = ?";
    DatabaseManager.executeUpdate(query, newAddress, project.projectNumber());
    System.out.println("Physical address updated successfully.");
  }

  /**
   * Updates the project type for the project.
   *
   * @param scanner The scanner instance used for reading user input.
   * @param project The project whose project type is being updated.
   */
  private static void updateProjectType(Scanner scanner, Project project) {
    System.out.println("Enter new project type: ");
    String newType = scanner.nextLine().trim();
    String query = "UPDATE projects SET ProjectType = ? WHERE ProjectNumber = ?";
    DatabaseManager.executeUpdate(query, newType, project.projectNumber());
    System.out.println("Project type updated successfully.");
  }

  /**
   * Updates the project name for the project.
   *
   * @param scanner The scanner instance used for reading user input.
   * @param project The project whose project name is being updated.
   */
  private static void updateProjectName(Scanner scanner, Project project) {
    System.out.println("Enter new project name: ");
    String newName = scanner.nextLine().trim();
    String query = "UPDATE projects SET ProjectName = ? WHERE ProjectNumber = ?";
    DatabaseManager.executeUpdate(query, newName, project.projectNumber());
    System.out.println("Project name updated successfully.");
  }

  /**
   * Displays the main menu for project updates.
   */
  private static void displayMainMenu() {
    System.out.print("""
        Select a field you would wish to update...
        1. Project Name   2. Project Type    3. Address       4. ERF Number
        5. Total Fee      6. Amount Paid     7. Deadline      8. Finalized
        9. Manager       10. Engineer       11. Architect    12. Contractor
        
        13. Delete Project
        
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

  /**
   * Deletes selected project from the database.
   *
   * @param scanner The scanner instance used for reading user input.
   * @param project The project to delete.
   */
  private static void deleteProject(Scanner scanner, Project project) {
    System.out.println("Are you sure you wish to delete this project?");
    System.out.print("y/n [n]: ");
    String confirmation = scanner.nextLine().trim();
    if (confirmation.equals("y")) {
      System.out.println("WARNING: This action is irreversible...");
      System.out.println("Enter '" + project.projectName() + "' (case-sensitive) to confirm...");
      confirmation = scanner.nextLine().trim();
      if (confirmation.equals(project.projectName())) {
        database.Delete.deleteProject(project);
        return;
      }
    }

    System.out.println("Operation cancelled.");
  }
}