package menu.options.manage;

import java.util.*;

import records.Project;
import utils.outputs.DisplayProjects;

import static database.Read.readProjects;

/**
 * The ProjectGetter class provides methods for retrieving different sets of projects
 * based on various criteria such as project completion, past deadlines, or unassigned people.
 */
public class ProjectGetter {
  private static final int DIVIDER_WIDTH = 100;

  /**
   * Retrieves a list of incomplete projects (projects that are not finalised).
   * Displays the project details in a table format.
   *
   * @return A list of incomplete projects.
   */
  public static List<Project> getIncompleteProjects() {
    System.out.println("Incomplete projects...");
    List<Project> projects = readProjects("AND", "Finalised = FALSE");
    DisplayProjects.displayProjectsTable(projects, DIVIDER_WIDTH,
      "Project Name", "Building Type", "Physical Address", "ERF No.",
      "Total Fee", "Start Date", "Deadline", "Customer");
    return projects;
  }

  /**
   * Retrieves a list of projects that are past their deadlines and are not finalised.
   * Displays the project details in a table format.
   *
   * @return A list of projects that are past deadline.
   */
  public static List<Project> getProjectsPastDeadline() {
    System.out.println("Projects past deadline");
    List<Project> projects = readProjects("AND", "CURRENT_DATE > Deadline", "Finalised = FALSE");
    DisplayProjects.displayProjectsTable(projects, DIVIDER_WIDTH,
      "Project Name", "Building Type", "Physical Address", "ERF No.",
      "Total Fee", "Start Date", "Deadline", "Customer");
    return projects;
  }

  /**
   * Retrieves a list of projects that have unassigned people (Engineer, Manager, Architect, Contractor, or Customer).
   * Displays the project details in a table format with the names of unassigned people.
   *
   * @return A list of projects with unassigned people.
   */
  public static List<Project> getProjectsWithUnassignedPeople() {
    System.out.println("Projects with unassigned people");
    List<Project> projects = readProjects(
      "OR",
      "EngineerID is NULL",
      "ManagerID is NULL",
      "ArchitectID is NULL",
      "ContractorID is NULL",
      "CustomerID is NULL"
    );
    DisplayProjects.displayProjectsTable(projects, DIVIDER_WIDTH,
      "Project Name", "Building Type", "Physical Address", "ERF No.",
      "Total Fee", "Start Date", "Deadline",
      "Engineer", "Manager", "Architect", "Contractor", "Customer");
    return projects;
  }
}
