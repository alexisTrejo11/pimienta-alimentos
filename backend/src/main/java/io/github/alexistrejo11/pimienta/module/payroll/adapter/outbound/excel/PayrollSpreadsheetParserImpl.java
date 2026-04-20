package io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.excel;

import io.github.alexistrejo11.pimienta.module.payroll.core.application.dto.PayrollImportRow;
import io.github.alexistrejo11.pimienta.module.payroll.core.port.input.PayrollSpreadsheetParser;
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
public class PayrollSpreadsheetParserImpl implements PayrollSpreadsheetParser {

  @Override
  public List<PayrollImportRow> parse(InputStream xlsx, String originalFilename) throws IOException {
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
      requireColumn(col, PayrollSpreadsheetColumn.EMPLOYEE_ID);
      requireColumn(col, PayrollSpreadsheetColumn.WORKED_DAYS_START);
      requireColumn(col, PayrollSpreadsheetColumn.WORKED_DAYS_END);
      requireColumn(col, PayrollSpreadsheetColumn.GROSS_AMOUNT);

      List<PayrollImportRow> rows = new ArrayList<>();
      int lastRow = sheet.getLastRowNum();
      for (int r = 1; r <= lastRow; r++) {
        Row row = sheet.getRow(r);
        if (row == null) {
          continue;
        }
        int excelRowNumber = r + 1;
        Long id = SpreadsheetCellReader.longAt(row, idx(col, PayrollSpreadsheetColumn.ID));
        Long employeeId = SpreadsheetCellReader.longAt(row, idx(col, PayrollSpreadsheetColumn.EMPLOYEE_ID));
        Long periodId = SpreadsheetCellReader.longAt(row, idx(col, PayrollSpreadsheetColumn.PERIOD_ID));
        var workedStart = SpreadsheetCellReader.localDateAt(row, idx(col, PayrollSpreadsheetColumn.WORKED_DAYS_START));
        var workedEnd = SpreadsheetCellReader.localDateAt(row, idx(col, PayrollSpreadsheetColumn.WORKED_DAYS_END));
        var grossAmount = SpreadsheetCellReader.bigDecimalAt(row, idx(col, PayrollSpreadsheetColumn.GROSS_AMOUNT));
        var totalDiscounts = SpreadsheetCellReader.bigDecimalAt(row, idx(col, PayrollSpreadsheetColumn.TOTAL_DISCOUNTS));
        var totalBonuses = SpreadsheetCellReader.bigDecimalAt(row, idx(col, PayrollSpreadsheetColumn.TOTAL_BONUSES));
        var netAmount = SpreadsheetCellReader.bigDecimalAt(row, idx(col, PayrollSpreadsheetColumn.NET_AMOUNT));
        String statusRaw = SpreadsheetCellReader.stringAt(row, idx(col, PayrollSpreadsheetColumn.STATUS));

        if (id == null
            && employeeId == null
            && periodId == null
            && workedStart == null
            && workedEnd == null
            && grossAmount == null
            && netAmount == null
            && statusRaw.isBlank()) {
          continue;
        }
        rows.add(
            new PayrollImportRow(
                excelRowNumber,
                id,
                employeeId,
                periodId,
                workedStart,
                workedEnd,
                grossAmount,
                totalDiscounts,
                totalBonuses,
                netAmount,
                statusRaw));
      }
      return rows;
    }
  }

  private static Integer idx(Map<String, Integer> col, PayrollSpreadsheetColumn c) {
    return col.get(SpreadsheetCellReader.normalizeHeader(c.header()));
  }

  private static void requireColumn(Map<String, Integer> col, PayrollSpreadsheetColumn c) {
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
