package records;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a Project with detailed information including financials, dates, and
 * related personnel.
 */
public record Project(
  int projectNumber,
  String projectName,
  String buildingType,
  String physicalAddress,
  String erfNumber,
  BigDecimal totalFee,
  BigDecimal amountPaidToDate,
  Date startDate,
  Date deadline,
  boolean finalised,
  Date completionDate,
  Engineer engineer,
  Manager manager,
  Architect architect,
  Contractor contractor,
  Customer customer
) {
}
