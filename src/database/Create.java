package database;

import records.Person;
import records.Project;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class manages the creation of records from the database.
 */
public class Create {

  /**
   * Creates a new project and inserts it into the database.
   *
   * @param projectName      The name of the project.
   * @param buildingType     The type of building for the project.
   * @param physicalAddress  The physical address of the project.
   * @param ERFNumber        The ERF number of the project.
   * @param totalFee         The total fee for the project.
   * @param deadline         The deadline for project completion.
   * @param CustomerID       The ID of the customer associated with the project.
   * @return                 The created Project object, or null if creation fails.
   */
  public static Project createNewProject(
    String projectName, String buildingType, String physicalAddress,
    String ERFNumber, BigDecimal totalFee, Date deadline, int CustomerID) {

    // SQL query to insert a new project
    String insertQuery = """
            INSERT INTO projects (
                ProjectName, BuildingType, PhysicalAddress,
                ERFNumber, TotalFee, Deadline, CustomerID
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

    // Execute insert query
    int result = DatabaseManager.executeUpdate(insertQuery,
      projectName, buildingType, physicalAddress, ERFNumber, totalFee, deadline, CustomerID
    );

    if (result > 0) {
      System.out.println("Project successfully captured.");

      // Retrieve the last inserted project ID (ProjectNumber)
      String selectQuery = "SELECT MAX(ProjectNumber) AS max_project_number FROM projects ";
      List<Map<String, Object>> resultList = DatabaseManager.executeQuery(selectQuery);
      int projectID = Integer.parseInt(resultList.getFirst().get("max_project_number").toString());
      return Read.readProjects("AND", "ProjectNumber=" + projectID).getFirst();
    }

    System.out.println("Failed to capture project.");
    return null;
  }

  /**
   * Creates a new person based on their role and inserts them into the database.
   *
   * @param role             The role of the person (e.g., "architect", "contractor", etc.).
   * @param name             The name of the person.
   * @param telephoneNumber  The telephone number of the person.
   * @param emailAddress     The email address of the person.
   * @param physicalAddress  The physical address of the person.
   * @return                 The created Person object, or null if creation fails.
   */
  public static Person createNewPerson(
    String role, String name, String telephoneNumber, String emailAddress, String physicalAddress) {
    String tableName;
    String idName = switch (role.toLowerCase()) {
      case "architect" -> {
        tableName = "architects";
        yield "ArchitectID";
      }
      case "contractor" -> {
        tableName = "contractors";
        yield "ContractorID";
      }
      case "customer" -> {
        tableName = "customers";
        yield "CustomerID";
      }
      case "engineer" -> {
        tableName = "engineers";
        yield "EngineerID";
      }
      case "manager" -> {
        tableName = "managers";
        yield "ManagerID";
      }
      default -> throw new IllegalArgumentException("Invalid role: " + role);
    };

    // SQL query to insert a new customer
    String insertQuery =
      "INSERT INTO " +
      tableName +
      " (name, telephoneNumber, emailAddress, physicalAddress) VALUES (?, ?, ?, ?)";

    // Execute insert query
    int result = DatabaseManager.executeUpdate(
      insertQuery, name, telephoneNumber, emailAddress, physicalAddress
    );

    if (result > 0) {
      System.out.println("Project successfully captured.");

      // Retrieve the person that was added via their ID
      String selectQuery = "SELECT MAX(" + idName + ") AS max_id FROM " + tableName;
      List<Map<String, Object>> resultList = DatabaseManager.executeQuery(selectQuery);

      if (resultList.isEmpty()) {
        System.out.println("Failed to retrieve new " + role + " ID.");
        return null;
      }

      int personID = Integer.parseInt(resultList.getFirst().get("max_id").toString());

      return switch (role.toLowerCase()) {
        case "architect" -> Read.getArchitect(personID);
        case "contractor" -> Read.getContractor(personID);
        case "customer" -> Read.getCustomer(personID);
        case "engineer" -> Read.getEngineer(personID);
        case "manager" -> Read.getManager(personID);
        default -> throw new IllegalArgumentException("Invalid role: " + role);
      };
    }

    System.out.println("Failed to create new " + role);
    return null;
  }
}

