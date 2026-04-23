package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.out.excel;

import io.github.alexistrejo11.pimienta.module.headquarter.core.application.dto.HeadquarterImportRow;
import io.github.alexistrejo11.pimienta.module.headquarter.core.port.input.HeadquarterSpreadsheetParser;
import io.github.alexistrejo11.pimienta.shared.spreadsheet.SpreadsheetCellReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

@Component
public class HeadquarterSpreadsheetParserImpl implements HeadquarterSpreadsheetParser {

  @Override
  public List<HeadquarterImportRow> parse(InputStream xlsx, String originalFilename)
      throws IOException {
    if (originalFilename != null
        && !originalFilename.isBlank()
        && !originalFilename.toLowerCase(Locale.ROOT).endsWith(".xlsx")) {
      throw new IllegalArgumentException("Se espera un archivo .xlsx");
    }
    try (Workbook workbook = WorkbookFactory.create(xlsx)) {
      Sheet sheet = workbook.getSheetAt(0);
      Row headerRow = sheet.getRow(0);
      if (headerRow == null) {
        throw new IllegalArgumentException("El archivo no tiene cabeceras en la fila 1");
      }
      Map<String, Integer> col = mapHeaders(headerRow);
      requireColumn(col, HeadquarterSpreadsheetColumn.ID);
      requireColumn(col, HeadquarterSpreadsheetColumn.NAME);
      requireColumn(col, HeadquarterSpreadsheetColumn.ADDRESS);
      requireColumn(col, HeadquarterSpreadsheetColumn.DESCRIPTION);

      List<HeadquarterImportRow> rows = new ArrayList<>();
      int lastRow = sheet.getLastRowNum();
      for (int r = 1; r <= lastRow; r++) {
        Row row = sheet.getRow(r);
        if (row == null) {
          continue;
        }
        int excelRowNumber = r + 1;
        Long id = SpreadsheetCellReader.longAt(row,
            col.get(SpreadsheetCellReader.normalizeHeader(HeadquarterSpreadsheetColumn.ID.header())));
        String name = SpreadsheetCellReader.stringAt(row,
            col.get(SpreadsheetCellReader.normalizeHeader(HeadquarterSpreadsheetColumn.NAME.header())));
        String address = SpreadsheetCellReader.stringAt(row,
            col.get(SpreadsheetCellReader.normalizeHeader(HeadquarterSpreadsheetColumn.ADDRESS.header())));
        String description = SpreadsheetCellReader.stringAt(row,
            col.get(SpreadsheetCellReader.normalizeHeader(HeadquarterSpreadsheetColumn.DESCRIPTION.header())));
        if (id == null && name.isBlank() && address.isBlank() && description.isBlank()) {
          continue;
        }
        rows.add(new HeadquarterImportRow(excelRowNumber, id, name, address, description));
      }
      return rows;
    }
  }

  private static void requireColumn(Map<String, Integer> col, HeadquarterSpreadsheetColumn c) {
    if (!col.containsKey(SpreadsheetCellReader.normalizeHeader(c.header()))) {
      throw new IllegalArgumentException("Falta la columna obligatoria: " + c.header());
    }
  }

  private static Map<String, Integer> mapHeaders(Row headerRow) {
    Map<String, Integer> map = new HashMap<>();
    short lastCell = headerRow.getLastCellNum();
    for (int c = 0; c < lastCell; c++) {
      var cell = headerRow.getCell(c);
      String text = SpreadsheetCellReader.stringAt(headerRow, c);
      if (!text.isEmpty()) {
        map.put(SpreadsheetCellReader.normalizeHeader(text), c);
      }
    }
    return map;
  }
}
