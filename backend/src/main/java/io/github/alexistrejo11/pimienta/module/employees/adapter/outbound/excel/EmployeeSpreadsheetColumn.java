package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.excel;

enum EmployeeSpreadsheetColumn {
  ID("ID"),
  NAME("Name"),
  EMAIL("Email"),
  PHONE("Phone"),
  ADDRESS("Address"),
  CURP("CURP"),
  RFC("RFC"),
  NSS("NSS"),
  CLABE("Clabe"),
  POSITION("Position"),
  DEPARTMENT("Department"),
  STATUS("Status"),
  CONTRACT_TYPE("ContractType"),
  WORK_SHIFT("WorkShift"),
  SALARY_PER_WEEK("SalaryPerWeek"),
  BONUSES("Bonuses"),
  FOOD_VOUCHERS("FoodVouchers"),
  INTEGRATION_FACTOR("IntegrationFactor");

  private final String header;

  EmployeeSpreadsheetColumn(String header) {
    this.header = header;
  }

  String header() {
    return header;
  }
}
