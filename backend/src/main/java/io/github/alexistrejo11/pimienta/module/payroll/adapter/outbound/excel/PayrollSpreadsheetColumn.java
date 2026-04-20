package io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.excel;

public enum PayrollSpreadsheetColumn {
  ID("id"),
  EMPLOYEE_ID("employee_id"),
  PERIOD_ID("period_id"),
  WORKED_DAYS_START("worked_days_start"),
  WORKED_DAYS_END("worked_days_end"),
  GROSS_AMOUNT("gross_amount"),
  TOTAL_DISCOUNTS("total_discounts"),
  TOTAL_BONUSES("total_bonuses"),
  NET_AMOUNT("net_amount"),
  STATUS("status");

  private final String header;

  PayrollSpreadsheetColumn(String header) {
    this.header = header;
  }

  public String header() {
    return header;
  }
}
