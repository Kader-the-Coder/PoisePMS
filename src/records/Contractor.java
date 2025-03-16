package records;

/**
 * Represents a Contractor with basic contact details.
 * This class implements the {@link Person} interface.
 */
public record Contractor(
  int id,
  String name,
  String telephoneNumber,
  String emailAddress,
  String physicalAddress
) implements Person {
}
