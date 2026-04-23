package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.out.excel;

/** Cabeceras del Excel de tareas (export/import alineados). */
enum TaskSpreadsheetColumn {
  ID("ID"),
  TITLE("Title"),
  STATUS("Status"),
  CHECKLIST("Checklist"),
  HEADQUARTER("Headquarter");

  private final String header;

  TaskSpreadsheetColumn(String header) {
    this.header = header;
  }

  String header() {
    return header;
  }
}
