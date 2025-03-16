package records;

/**
 * Represents a Manager with basic contact details.
 * This class implements the {@link Person} interface.
 */
public record Manager(
  int id,
  String name,
  String telephoneNumber,
  String emailAddress,
  String physicalAddress
) implements Person {
}
