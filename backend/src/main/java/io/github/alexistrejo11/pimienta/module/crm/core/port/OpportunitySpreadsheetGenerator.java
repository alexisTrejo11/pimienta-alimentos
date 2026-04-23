package io.github.alexistrejo11.pimienta.module.crm.core.port;

import io.github.alexistrejo11.pimienta.module.crm.core.application.dto.OpportunityExportRow;
import java.io.IOException;
import java.util.List;

public interface OpportunitySpreadsheetGenerator {

  byte[] generate(List<OpportunityExportRow> rows) throws IOException;
}
