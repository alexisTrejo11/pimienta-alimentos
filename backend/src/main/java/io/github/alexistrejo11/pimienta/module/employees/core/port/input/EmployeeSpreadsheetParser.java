package io.github.alexistrejo11.pimienta.module.employees.core.port.input;

import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.EmployeeImportRow;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface EmployeeSpreadsheetParser {

  List<EmployeeImportRow> parse(InputStream xlsx, String originalFilename) throws IOException;
}
