package utils.outputs;

import records.Person;
import java.util.*;
import java.util.function.Function;

/**
 * Utility class to display a table of person records in a formatted manner.
 */
public class DisplayPersons {

  /**
   * Displays a table of person records with customizable columns.
   *
   * @param people A list of persons to be displayed in the table.
   * @param width The maximum width of each column. If set to 0, the column widths are determined by the content.
   * @param fields The fields to display in the table. If no fields are provided, all fields will be displayed.
   */
  public static void displayPersonsTable(List<? extends Person> people, int width, String... fields) {
    if (people == null || people.isEmpty()) {
      System.out.println("No people found.");
      return;
    }

    // Define mapping of field names to their corresponding getters
    Map<String, Function<Person, String>> fieldGetters = new LinkedHashMap<>();
    fieldGetters.put("#", person -> String.valueOf(person.id()));
    fieldGetters.put("Name", Person::name);
    fieldGetters.put("Telephone Number", Person::telephoneNumber);
    fieldGetters.put("Email Address", Person::emailAddress);
    fieldGetters.put("Physical Address", Person::physicalAddress);

    // Filter only the fields specified in the parameters
    List<String> selectedFields = new ArrayList<>();
    List<Function<Person, String>> selectedGetters = new ArrayList<>();

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

      for (Person person : people) {
        String value = selectedGetters.get(i).apply(person);
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
    for (Person person : people) {
      Object[] rowValues = selectedGetters.stream()
        .map(getter -> {
          String value = getter.apply(person);
          return (value == null) ? "N/A" : value; // Handle null values
        })
        .toArray();

      System.out.printf(format, rowValues);
      System.out.println();
    }

    // Print bottom divider
    System.out.println(divider);
  }
}
