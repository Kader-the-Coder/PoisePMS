package records;

/**
 * Represents an Engineer with basic contact details.
 * This class implements the {@link Person} interface.
 */
public record Engineer(
  int id,
  String name,
  String telephoneNumber,
  String emailAddress,
  String physicalAddress
) implements Person {
}