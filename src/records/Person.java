package records;

/**
 * Represents a person with basic contact details.
 */
public interface Person {
  int id();
  String name();
  String telephoneNumber();
  String emailAddress();
  String physicalAddress();
}