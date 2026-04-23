package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.out.excel;

enum HeadquarterSpreadsheetColumn {
  ID("ID"),
  NAME("Name"),
  ADDRESS("Address"),
  DESCRIPTION("Description");

  private final String header;

  HeadquarterSpreadsheetColumn(String header) {
    this.header = header;
  }

  String header() {
    return header;
  }
}
