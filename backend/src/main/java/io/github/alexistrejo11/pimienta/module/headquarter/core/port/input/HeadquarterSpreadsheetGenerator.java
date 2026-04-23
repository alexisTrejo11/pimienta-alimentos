package io.github.alexistrejo11.pimienta.module.headquarter.core.port.input;

import io.github.alexistrejo11.pimienta.module.headquarter.core.application.dto.HeadquarterExportRow;
import java.io.IOException;
import java.util.List;

public interface HeadquarterSpreadsheetGenerator {

  byte[] generate(List<HeadquarterExportRow> rows) throws IOException;
}
