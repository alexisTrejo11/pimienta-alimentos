package io.github.alexistrejo11.pimienta.module.payroll.core.port.input;

import io.github.alexistrejo11.pimienta.module.payroll.core.application.dto.PayrollImportRow;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface PayrollSpreadsheetParser {

  List<PayrollImportRow> parse(InputStream xlsx, String originalFilename) throws IOException;
}
