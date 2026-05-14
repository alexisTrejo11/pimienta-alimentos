package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.excel;

import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.EmployeeImportRow;
import io.github.alexistrejo11.pimienta.module.employees.core.port.input.EmployeeSpreadsheetParser;
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
public class EmployeeSpreadsheetParserImpl implements EmployeeSpreadsheetParser {

  @Override
  public List<EmployeeImportRow> parse(InputStream xlsx, String originalFilename) throws IOException {
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
      for (EmployeeSpreadsheetColumn c : EmployeeSpreadsheetColumn.values()) {
        requireColumn(col, c);
      }

      List<EmployeeImportRow> rows = new ArrayList<>();
      int lastRow = sheet.getLastRowNum();
      for (int r = 1; r <= lastRow; r++) {
        Row row = sheet.getRow(r);
        if (row == null) {
          continue;
        }
        int excelRowNumber = r + 1;
        Long id = SpreadsheetCellReader.longAt(row, idx(col, EmployeeSpreadsheetColumn.ID));
        String firstName = SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.FIRST_NAME));
        String lastName = SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.LAST_NAME));
        String email = SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.EMAIL));
        if (id == null && firstName.isBlank() && lastName.isBlank() && email.isBlank()) {
          continue;
        }
        rows.add(
            new EmployeeImportRow(
                excelRowNumber,
                id,
                firstName,
                lastName,
                email,
                SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.PHONE)),
                SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.ADDRESS)),
                SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.CURP)),
                SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.RFC)),
                SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.NSS)),
                SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.CLABE)),
                SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.POSITION)),
                SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.DEPARTMENT)),
                SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.STATUS)),
                SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.CONTRACT_TYPE)),
                SpreadsheetCellReader.stringAt(row, idx(col, EmployeeSpreadsheetColumn.WORK_SHIFT)),
                SpreadsheetCellReader.bigDecimalAt(row, idx(col, EmployeeSpreadsheetColumn.SALARY_PER_WEEK)),
                SpreadsheetCellReader.bigDecimalAt(row, idx(col, EmployeeSpreadsheetColumn.BONUSES)),
                SpreadsheetCellReader.bigDecimalAt(row, idx(col, EmployeeSpreadsheetColumn.FOOD_VOUCHERS)),
                SpreadsheetCellReader.bigDecimalAt(row, idx(col, EmployeeSpreadsheetColumn.INTEGRATION_FACTOR))));
      }
      return rows;
    }
  }

  private static Integer idx(Map<String, Integer> col, EmployeeSpreadsheetColumn c) {
    return col.get(SpreadsheetCellReader.normalizeHeader(c.header()));
  }

  private static void requireColumn(Map<String, Integer> col, EmployeeSpreadsheetColumn c) {
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
