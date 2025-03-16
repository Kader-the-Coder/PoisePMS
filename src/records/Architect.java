package records;

/**
 * Represents an Architect with basic contact details.
 * This class implements the {@link Person} interface.
 */
public record Architect(
  int id,
  String name,
  String telephoneNumber,
  String emailAddress,
  String physicalAddress
) implements Person {
}
