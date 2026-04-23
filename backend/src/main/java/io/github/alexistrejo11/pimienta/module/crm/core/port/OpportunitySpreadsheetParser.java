package io.github.alexistrejo11.pimienta.module.crm.core.port;

import io.github.alexistrejo11.pimienta.module.crm.core.application.dto.OpportunityImportRow;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface OpportunitySpreadsheetParser {

  List<OpportunityImportRow> parse(InputStream xlsx, String originalFilename) throws IOException;
}
