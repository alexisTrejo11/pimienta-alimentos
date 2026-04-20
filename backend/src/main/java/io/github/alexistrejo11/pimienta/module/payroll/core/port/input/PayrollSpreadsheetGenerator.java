package io.github.alexistrejo11.pimienta.module.payroll.core.port.input;

import io.github.alexistrejo11.pimienta.module.payroll.core.application.dto.PayrollExportRow;
import java.io.IOException;
import java.util.List;

public interface PayrollSpreadsheetGenerator {

  byte[] generate(List<PayrollExportRow> rows) throws IOException;
}
