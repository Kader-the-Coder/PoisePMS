package utils.outputs;

import records.Person;
import records.Project;

import java.util.*;
import java.util.function.Function;

/**
 * Utility class to display a formatted table of project records in a readable format.
 */
public class DisplayProjects {

  /**
   * Displays a table of project details with customizable columns.
   * The table includes dynamically adjusted column widths to fit the content.
   *
   * @param projects The list of projects to display in the table.
   * @param width The maximum width of each column. If set to 0, the column width is adjusted based on content.
   * @param fields The fields to display in the table. If no fields are provided, all fields will be displayed.
   */
  public static void displayProjectsTable(List<Project> projects, int width, String... fields) {
    if (projects == null || projects.isEmpty()) {
      System.out.println("No projects found.");
      return;
    }

    // Define mapping of field names to their corresponding getters
    Map<String, Function<Project, String>> fieldGetters = new LinkedHashMap<>();
    fieldGetters.put("#", p -> String.valueOf(p.projectNumber()));
    fieldGetters.put("Project Name", Project::projectName);
    fieldGetters.put("Building Type", p -> optional(p.buildingType()));
    fieldGetters.put("Physical Address", p -> optional(p.physicalAddress()));
    fieldGetters.put("ERF No.", p -> optional(p.erfNumber()));
    fieldGetters.put("Total Fee", p -> String.format("%.2f", p.totalFee()));
    fieldGetters.put("Paid", p -> String.format("%.2f", p.amountPaidToDate()));
    fieldGetters.put("Start Date", p -> p.startDate().toString());
    fieldGetters.put("Deadline", p -> optionalDate(p.deadline()));
    fieldGetters.put("Finalised", p -> p.finalised() ? "Yes" : "No");
    fieldGetters.put("Completion", p -> optionalDate(p.completionDate()));
    fieldGetters.put("Engineer", p -> optionalPerson(p.engineer()));
    fieldGetters.put("Manager", p -> optionalPerson(p.manager()));
    fieldGetters.put("Architect", p -> optionalPerson(p.architect()));
    fieldGetters.put("Contractor", p -> optionalPerson(p.contractor()));
    fieldGetters.put("Customer", p -> optionalPerson(p.customer()));

    // Filter only the fields specified in the parameters
    List<String> selectedFields = new ArrayList<>();
    List<Function<Project, String>> selectedGetters = new ArrayList<>();

    // Insert the project number as a default field.
    selectedFields.add("#");
    selectedGetters.add(fieldGetters.get("#"));

    if (fields.length > 0) {
      for (String field : fields) {
        if (fieldGetters.containsKey(field)) {
          selectedFields.add(field);
          selectedGetters.add(fieldGetters.get(field));
        }
      }
    } else {
      // If no specific fields are provided, use all fields
      selectedFields.addAll(fieldGetters.keySet());
      selectedGetters.addAll(fieldGetters.values());
    }

    // Compute max width for each column based on headers and values
    Map<String, Integer> columnWidths = new LinkedHashMap<>();

    for (int i = 0; i < selectedFields.size(); i++) {
      String fieldName = selectedFields.get(i);
      int maxLen = fieldName.length(); // Start with header length

      for (Project project : projects) {
        String value = selectedGetters.get(i).apply(project);
        if (value == null) value = "N/A"; // Handle potential null values
        maxLen = Math.max(maxLen, value.length());
      }

      // Limit width to `width` if applicable
      if (width > 0) {
        maxLen = Math.min(maxLen, width);
      }

      columnWidths.put(fieldName, maxLen);
    }

    // Construct formatted print strings
    StringBuilder formatBuilder = new StringBuilder("|");
    StringBuilder dividerBuilder = new StringBuilder("+");

    for (String field : selectedFields) {
      int colWidth = columnWidths.get(field);
      formatBuilder.append(" %-").append(colWidth).append("s |");
      dividerBuilder.append("-".repeat(colWidth + 2)).append("+");
    }

    String format = formatBuilder.toString();
    String divider = dividerBuilder.toString();

    // Print header
    System.out.println(divider);
    System.out.printf(format, selectedFields.toArray());
    System.out.println();
    System.out.println(divider);

    // Print data rows
    for (Project project : projects) {
      Object[] rowValues = selectedGetters.stream()
        .map(getter -> {
          String value = getter.apply(project);
          return (value == null) ? "N/A" : value; // Handle null values
        })
        .toArray();

      System.out.printf(format, rowValues);
      System.out.println();
    }

    // Print bottom divider
    System.out.println(divider);
  }

  /**
   * Returns a default value if the provided string is null or empty.
   *
   * @param value The string to check.
   * @return The value itself if it's not null or empty, otherwise "N/A".
   */
  private static String optional(String value) {
    return (value == null || value.isEmpty()) ? "N/A" : value;
  }

  /**
   * Returns a default value if the provided date is null.
   *
   * @param date The date to check.
   * @return The string representation of the date if it's not null, otherwise "N/A".
   */
  private static String optionalDate(Date date) {
    return (date == null) ? "N/A" : date.toString();
  }

  /**
   * Returns a default value if the provided person is null.
   *
   * @param person The person to check.
   * @param <T> The type of the person (extends {@link Person}).
   * @return The person's name if the person is not null, otherwise "N/A".
   */
  private static <T extends Person> String optionalPerson(T person) {
    return (person == null) ? "N/A" : person.name();
  }
}
