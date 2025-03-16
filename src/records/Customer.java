package records;

/**
 * Represents a Customer with basic contact details.
 * This class implements the {@link Person} interface.
 */
public record Customer(
  int id,
  String name,
  String telephoneNumber,
  String emailAddress,
  String physicalAddress
) implements Person {
}