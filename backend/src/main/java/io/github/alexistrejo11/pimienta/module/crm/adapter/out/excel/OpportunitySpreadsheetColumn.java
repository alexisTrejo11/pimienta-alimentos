package io.github.alexistrejo11.pimienta.module.crm.adapter.out.excel;

public enum OpportunitySpreadsheetColumn {
  ID("ID"),
  TITLE("Title"),
  DESCRIPTION("Description"),
  CONTACT_NAME("ContactName"),
  CONTACT_EMAIL("ContactEmail"),
  CONTACT_PHONE("ContactPhone"),
  COMPANY_NAME("CompanyName"),
  COMPANY_LOCATION("CompanyLocation"),
  INDUSTRY("Industry"),
  ESTIMATED_VALUE("EstimatedValue"),
  PROBABILITY_PERCENT("ProbabilityPercent"),
  SOURCE("Source"),
  EXPECTED_CLOSE_DATE("ExpectedCloseDate"),
  ASSIGNED_SALESMAN_ID("AssignedSalesmanId"),
  STATUS("Status");

  private final String header;

  OpportunitySpreadsheetColumn(String header) {
    this.header = header;
  }

  public String header() {
    return header;
  }
}
