package database;

import records.Person;
import records.Project;

/**
 * This class manages the deletion of records from the database.
 */
public class Delete {

  /**
   * This method deletes a project from the database.
   * @param project The person to be deleted.
   */
  public static void deleteProject(Project project) {
    String query = "DELETE FROM projects WHERE ProjectNumber = ?";
    DatabaseManager.executeUpdate(query, project.projectNumber());
    System.out.println("'" + project.projectName() + "' deleted successfully.");
  }

  /**
   * This method deletes a person from the database.
   * @param person The person to be deleted.
   */
  public static void deletePerson(Person person) {
    String table = person.getClass().getSimpleName().toLowerCase() + "s";
    String idName = person.getClass().getSimpleName() + "ID";
    String query = "DELETE FROM " + table + " WHERE " + idName + " = ?";
    DatabaseManager.executeUpdate(query, person.id());
    System.out.println("'" + person.name() + "' deleted successfully.");
  }
}
