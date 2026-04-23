package io.github.alexistrejo11.pimienta.module.crm.adapter.out.excel;

import io.github.alexistrejo11.pimienta.module.crm.core.application.dto.OpportunityImportRow;
import io.github.alexistrejo11.pimienta.module.crm.core.port.OpportunitySpreadsheetParser;
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
public class OpportunitySpreadsheetParserImpl implements OpportunitySpreadsheetParser {

  @Override
  public List<OpportunityImportRow> parse(InputStream xlsx, String originalFilename)
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
      for (OpportunitySpreadsheetColumn c : OpportunitySpreadsheetColumn.values()) {
        requireColumn(col, c);
      }

      List<OpportunityImportRow> rows = new ArrayList<>();
      int lastRow = sheet.getLastRowNum();
      for (int r = 1; r <= lastRow; r++) {
        Row row = sheet.getRow(r);
        if (row == null) {
          continue;
        }
        int excelRowNumber = r + 1;
        Long id = SpreadsheetCellReader.longAt(row, idx(col, OpportunitySpreadsheetColumn.ID));
        String title = SpreadsheetCellReader.stringAt(row, idx(col, OpportunitySpreadsheetColumn.TITLE));
        if (id == null && title.isBlank()) {
          continue;
        }
        rows.add(
            new OpportunityImportRow(
                excelRowNumber,
                id,
                title,
                SpreadsheetCellReader.stringAt(row, idx(col, OpportunitySpreadsheetColumn.DESCRIPTION)),
                SpreadsheetCellReader.stringAt(row, idx(col, OpportunitySpreadsheetColumn.CONTACT_NAME)),
                SpreadsheetCellReader.stringAt(row, idx(col, OpportunitySpreadsheetColumn.CONTACT_EMAIL)),
                SpreadsheetCellReader.stringAt(row, idx(col, OpportunitySpreadsheetColumn.CONTACT_PHONE)),
                SpreadsheetCellReader.stringAt(row, idx(col, OpportunitySpreadsheetColumn.COMPANY_NAME)),
                SpreadsheetCellReader.stringAt(row, idx(col, OpportunitySpreadsheetColumn.COMPANY_LOCATION)),
                SpreadsheetCellReader.stringAt(row, idx(col, OpportunitySpreadsheetColumn.INDUSTRY)),
                SpreadsheetCellReader.bigDecimalAt(row, idx(col, OpportunitySpreadsheetColumn.ESTIMATED_VALUE)),
                parseInteger(SpreadsheetCellReader.stringAt(row, idx(col, OpportunitySpreadsheetColumn.PROBABILITY_PERCENT))),
                SpreadsheetCellReader.stringAt(row, idx(col, OpportunitySpreadsheetColumn.SOURCE)),
                SpreadsheetCellReader.localDateAt(row, idx(col, OpportunitySpreadsheetColumn.EXPECTED_CLOSE_DATE)),
                SpreadsheetCellReader.longAt(row, idx(col, OpportunitySpreadsheetColumn.ASSIGNED_SALESMAN_ID))));
      }
      return rows;
    }
  }

  private static Integer parseInteger(String s) {
    if (s == null || s.isBlank()) {
      return null;
    }
    try {
      return Integer.parseInt(s.trim());
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private static Integer idx(Map<String, Integer> col, OpportunitySpreadsheetColumn c) {
    return col.get(SpreadsheetCellReader.normalizeHeader(c.header()));
  }

  private static void requireColumn(Map<String, Integer> col, OpportunitySpreadsheetColumn c) {
    if (!col.containsKey(SpreadsheetCellReader.normalizeHeader(c.header()))) {
      throw new IllegalArgumentException("Falta la columna obligatoria: " + c.header());
    }
  }

  private static Map<String, Integer> mapHeaders(Row headerRow) {
    Map<String, Integer> map = new HashMap<>();
    short lastCell = headerRow.getLastCellNum();
    for (int c = 0; c < lastCell; c++) {
      String text = SpreadsheetCellReader.stringAt(headerRow, c);
      if (!text.isEmpty()) {
        map.put(SpreadsheetCellReader.normalizeHeader(text), c);
      }
    }
    return map;
  }
}
