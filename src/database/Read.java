package database;

import records.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * This class manages the reading of records from the database.
 */
public class Read {

  /**
   * Reads projects from the database based on specified conditions.
   *
   * @param chainBy    The logical operator (e.g., "AND", "OR") to chain conditions.
   * @param conditions The conditions to filter the projects.
   * @return           A list of Project objects matching the conditions.
   */
  public static List<Project> readProjects(String chainBy, String... conditions) {
    StringBuilder query = new StringBuilder("SELECT * FROM projects WHERE ");

    // Build the WHERE conditions dynamically
    for (int i = 0; i < conditions.length; i++) {
      if (i == 0) {
        query.append(conditions[i]);
      } else {
        query.append(" ").append(chainBy).append(" ").append(conditions[i]);
      }
    }

    // Execute the query and get the result list
    List<Map<String, Object>> results = DatabaseManager.executeQuery(query.toString());

    // If no results are found, return an empty list
    if (results.isEmpty()) {
      return new ArrayList<>();
    }

    // List to store the Project objects
    List<Project> projects = new ArrayList<>();

    // Loop through the results and map each one to a Project object
    for (Map<String, Object> result : results) {
      // Map the result set to Project object
      int projectNumber = (int) result.get("ProjectNumber");
      String projectName = (String) result.get("ProjectName");
      String buildingType = (String) result.get("BuildingType");
      String physicalAddress = (String) result.get("PhysicalAddress");
      String erfNumber = (String) result.get("ERFNumber");
      BigDecimal totalFee = (BigDecimal) result.get("TotalFee");
      BigDecimal amountPaidToDate = (BigDecimal) result.get("AmountPaidToDate");
      Date startDate = (Date) result.get("StartDate");
      Date deadline = (Date) result.get("Deadline");
      boolean finalised = (Boolean) result.get("Finalised");
      Date completeDate = (Date) result.get("CompletionDate");

      // Get related entities (Engineer, Manager, Architect, Contractor, Customer)
      Engineer engineer = getEngineer((Integer) result.get("EngineerID"));
      Manager manager = getManager((Integer) result.get("ManagerID"));
      Architect architect = getArchitect((Integer) result.get("ArchitectID"));
      Contractor contractor = getContractor((Integer) result.get("ContractorID"));
      Customer customer = getCustomer((Integer) result.get("CustomerID"));

      // Create the Project object and add it to the list
      Project project = new Project(
        projectNumber,
        projectName,
        buildingType,
        physicalAddress,
        erfNumber,
        totalFee,
        amountPaidToDate,
        startDate,
        deadline,
        finalised,
        completeDate,
        engineer,
        manager,
        architect,
        contractor,
        customer
      );

      projects.add(project);
    }

    // Return the list of Project objects
    return projects;
  }

  /**
   * Retrieves a person from the specified table based on their ID.
   *
   * @param tableName The name of the database table.
   * @param idColumn  The name of the ID column in the table.
   * @param id        The ID of the person to retrieve.
   * @param clazz     The class type of the person to be returned.
   * @param <T>       The type of person (Engineer, Manager, Architect, Contractor, or Customer).
   * @return          An instance of the specified class, or null if not found.
   */
  private static <T> T getPersonById(String tableName, String idColumn, Integer id, Class<T> clazz) {
    String query = "SELECT * FROM " + tableName + " WHERE " + idColumn + " = ?";
    List<Map<String, Object>> results = DatabaseManager.executeQuery(query, String.valueOf(id));

    // If exactly one record is found
    if (results.size() == 1) {
      Map<String, Object> result = results.getFirst();
      String name = (String) result.get("name");
      String telephoneNumber = (String) result.get("TelephoneNumber");
      String emailAddress = (String) result.get("EmailAddress");
      String physicalAddress = (String) result.get("PhysicalAddress");

      if (clazz == Engineer.class) {
        return clazz.cast(new Engineer(id, name, telephoneNumber, emailAddress, physicalAddress));
      }
      else if (clazz == Manager.class) {
        return clazz.cast(new Manager(id, name, telephoneNumber, emailAddress, physicalAddress));
      }
      else if (clazz == Architect.class) {
        return clazz.cast(new Architect(id, name, telephoneNumber, emailAddress, physicalAddress));
      }
      else if (clazz == Contractor.class) {
        return clazz.cast(new Contractor(id, name, telephoneNumber, emailAddress, physicalAddress));
      }
      else if (clazz == Customer.class) {
        return clazz.cast(new Customer(id, name, telephoneNumber, emailAddress, physicalAddress));
      }
    }

    return null;
  }

  /**
   * Retrieves an Engineer by their ID.
   *
   * @param engineerID The ID of the Engineer.
   * @return           The Engineer object, or null if not found.
   */
  public static Engineer getEngineer(Integer engineerID) {
    return getPersonById("engineers", "EngineerID", engineerID, Engineer.class);
  }

  /**
   * Retrieves a Manager by their ID.
   *
   * @param managerID The ID of the Manager.
   * @return          The Manager object, or null if not found.
   */
  public static Manager getManager(Integer managerID) {
    return getPersonById("managers", "ManagerID", managerID, Manager.class);
  }

  /**
   * Retrieves an Architect by their ID.
   *
   * @param architectID The ID of the Architect.
   * @return            The Architect object, or null if not found.
   */
  public static Architect getArchitect(Integer architectID) {
    return getPersonById("architects", "ArchitectID", architectID, Architect.class);
  }

  /**
   * Retrieves a Contractor by their ID.
   *
   * @param contractorID The ID of the Contractor.
   * @return             The Contractor object, or null if not found.
   */
  public static Contractor getContractor(Integer contractorID) {
    return getPersonById("contractors", "ContractorID", contractorID, Contractor.class);
  }

  /**
   * Retrieves a Customer by their ID.
   *
   * @param customerID The ID of the Customer.
   * @return           The Customer object, or null if not found.
   */
  public static Customer getCustomer(Integer customerID) {
    return getPersonById("customers", "CustomerID", customerID, Customer.class);
  }

  /**
   * Retrieves all persons of a specific type, optionally excluding specific IDs.
   *
   * @param clazz              The class type of the persons to retrieve.
   * @param excludedPersonIDs  A list of person IDs to exclude from the results.
   * @return                   A list of persons matching the specified class type.
   */
  public static List<? extends Person> getAllPersons(Class<? extends Person> clazz, List<Integer> excludedPersonIDs) {
    String tableName;
    String idName;
    if (clazz == Architect.class) {
      tableName = "architects";
      idName = "ArchitectID";
    }
    else if (clazz == Contractor.class) {
      tableName = "contractors";
      idName = "ContractorID";
    }
    else if (clazz == Customer.class) {
      tableName = "customers";
      idName = "CustomerID";
    }
    else if (clazz == Engineer.class) {
      tableName = "engineers";
      idName = "EngineerID";
    }
    else if (clazz == Manager.class) {
      tableName = "managers";
      idName = "ManagerID";
    }
    else {
      return Collections.emptyList();
    }

    String query = "SELECT * FROM " + tableName;

    // Exclude specific IDs if provided
    List<Map<String, Object>> results;
    if (!excludedPersonIDs.isEmpty()) {
      String placeholders = String.join(",", Collections.nCopies(excludedPersonIDs.size(), "?"));
      query += " WHERE id NOT IN (" + placeholders + ")";
      results = DatabaseManager.executeQuery(query, excludedPersonIDs.toArray());
    } else {
      results = DatabaseManager.executeQuery(query);
    }

    List<Person> persons = new ArrayList<>();

    for (Map<String, Object> result : results) {
      int id = (int) result.get(idName);
      String name = (String) result.get("Name");
      String telephoneNumber = (String) result.get("TelephoneNumber");
      String emailAddress = (String) result.get("EmailAddress");
      String physicalAddress = (String) result.get("PhysicalAddress");

      Person person = null;
      if (clazz == Engineer.class) {
        person = clazz.cast(new Engineer(id, name, telephoneNumber, emailAddress, physicalAddress));
      }
      else if (clazz == Manager.class) {
        person = clazz.cast(new Manager(id, name, telephoneNumber, emailAddress, physicalAddress));
      }
      else if (clazz == Architect.class) {
        person = clazz.cast(new Architect(id, name, telephoneNumber, emailAddress, physicalAddress));
      }
      else if (clazz == Contractor.class) {
        person = clazz.cast(new Contractor(id, name, telephoneNumber, emailAddress, physicalAddress));
      }
      else { //if (clazz == Customer.class)
        person = clazz.cast(new Customer(id, name, telephoneNumber, emailAddress, physicalAddress));
      }

      persons.add(person);
    }

    return persons;
  }


}
