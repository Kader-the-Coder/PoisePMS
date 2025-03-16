package menu.options.capture;

import records.Customer;
import records.Person;
import records.Project;

import menu.options.manage.ProjectChanger;
import utils.outputs.DisplayPersons;
import utils.Utils;

import java.math.BigDecimal;
import java.util.*;

import static database.Create.createNewPerson;
import static database.Create.createNewProject;
import static database.Read.getAllPersons;
import static utils.outputs.DisplayProjects.displayProjectsTable;

/**
 * This class handles the process of capturing a new project within the system.
 * It interacts with the user to gather necessary information and then creates a new project entry in the database.
 */
public class CaptureProject {
  private static final int DIVIDER_WIDTH = 100;

  /**
   * Initiates the process of capturing a new project.
   * It prompts the user for project details, validates customer information, and creates a new project in the system.
   *
   * @param scanner The Scanner instance used for reading user input.
   */
  public static void captureProject(Scanner scanner) {
    String choice;

    // Get project details
    System.out.println("Capturing new project...");
    String projectName = Utils.inputString(scanner, "Project Name: ");
    String buildingType = Utils.inputString(scanner, "Building Type: ");
    String physicalAddress = Utils.inputString(scanner, "Physical address: ");
    String ERFNumber = Utils.inputString(scanner, "ERF number: ");
    BigDecimal totalFee = Utils.inputCurrency(scanner, "Total Fee: R");
    Date deadline = Utils.getValidDate(scanner, "Deadline (yyyy-MM-dd): ");

    // Determine customer details
    List<? extends Person> customers = getAllPersons(Customer.class, Collections.emptyList());
    DisplayPersons.displayPersonsTable(customers, DIVIDER_WIDTH);
    System.out.println("""
      Enter the customer ID (#) if this project is for an existing customer.
      ENTER '-1' to create new customer instead.
      """);

    int customerID = -1;
    while (true) {
      int selectedCustomerID = Utils.inputInteger(scanner, ": ", true);

      if (selectedCustomerID == -1) {
        // Create new customer
        String customerName = Utils.inputString(scanner, "Customer Name: ");
        String customerTelephoneNumber = Utils.inputString(scanner, "Customer Telephone Number: ");
        String customerEmailAddress = Utils.inputString(scanner, "Customer Email Address: ");
        String customerPhysicalAddress = Utils.inputString(scanner, "Customer Physical Address: ");
        customerID = Objects.requireNonNull(
          createNewPerson(
            "customer",
            customerName,
            customerTelephoneNumber,
            customerEmailAddress,
            customerPhysicalAddress
          )).id();
        break;
      } else if (customers.stream().anyMatch(customer -> customer.id() == selectedCustomerID)) {
        customerID = selectedCustomerID; // Now assign it to customerID only after validation
        break;
      }

      System.out.println("Invalid ID selected. Please try again.");
    }

    // Capture project
    Project project = createNewProject(
      projectName, buildingType, physicalAddress, ERFNumber, totalFee, deadline, customerID
    );

    // Determine if user would like to manage the project
    assert project != null;
    displayProjectsTable(List.of(project), DIVIDER_WIDTH,
      "Project Name", "Building Type", "Physical Address", "ERF No.",
      "Total Fee", "Start Date", "Deadline", "Customer");

    System.out.println("Would you like to manage this project? (y/n) [y]: ");
    choice = scanner.nextLine().toLowerCase();
    if (choice.equals("n")) {
      return;
    }
    ProjectChanger.projectChanger(scanner, project);

    Utils.printDivider(DIVIDER_WIDTH);
  }
}
