package io.github.alexistrejo11.pimienta.module.headquarter.core.port.input;

import io.github.alexistrejo11.pimienta.module.headquarter.core.application.dto.HeadquarterImportRow;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface HeadquarterSpreadsheetParser {

  List<HeadquarterImportRow> parse(InputStream xlsx, String originalFilename) throws IOException;
}
