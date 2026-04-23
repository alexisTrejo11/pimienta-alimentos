package io.github.alexistrejo11.pimienta.module.employees.core.port.input;

import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.EmployeeExportRow;
import java.io.IOException;
import java.util.List;

public interface EmployeeSpreadsheetGenerator {

  byte[] generate(List<EmployeeExportRow> rows) throws IOException;
}
