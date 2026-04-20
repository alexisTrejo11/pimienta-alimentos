package io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.excel;

import io.github.alexistrejo11.pimienta.module.payroll.core.application.dto.PayrollExportRow;
import io.github.alexistrejo11.pimienta.module.payroll.core.port.input.PayrollSpreadsheetGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class PayrollSpreadsheetGeneratorImpl implements PayrollSpreadsheetGenerator {

  @Override
  public byte[] generate(List<PayrollExportRow> rows) throws IOException {
    try (XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("Payroll");
      Row header = sheet.createRow(0);
      int c = 0;
      for (PayrollSpreadsheetColumn col : PayrollSpreadsheetColumn.values()) {
        header.createCell(c++).setCellValue(col.header());
      }

      int r = 1;
      for (PayrollExportRow row : rows) {
        Row dataRow = sheet.createRow(r++);
        int colIdx = 0;
        if (row.id() != null) {
          dataRow.createCell(colIdx++).setCellValue(row.id());
        } else {
          dataRow.createCell(colIdx++).setCellValue("");
        }
        if (row.employeeId() != null) {
          dataRow.createCell(colIdx++).setCellValue(row.employeeId());
        } else {
          dataRow.createCell(colIdx++).setCellValue("");
        }
        if (row.periodId() != null) {
          dataRow.createCell(colIdx++).setCellValue(row.periodId());
        } else {
          dataRow.createCell(colIdx++).setCellValue("");
        }
        dataRow.createCell(colIdx++).setCellValue(row.workedDaysStart() != null ? row.workedDaysStart().toString() : "");
        dataRow.createCell(colIdx++).setCellValue(row.workedDaysEnd() != null ? row.workedDaysEnd().toString() : "");
        dataRow.createCell(colIdx++).setCellValue(row.grossAmount() != null ? row.grossAmount().toPlainString() : "");
        dataRow.createCell(colIdx++).setCellValue(row.totalDiscounts() != null ? row.totalDiscounts().toPlainString() : "");
        dataRow.createCell(colIdx++).setCellValue(row.totalBonuses() != null ? row.totalBonuses().toPlainString() : "");
        dataRow.createCell(colIdx++).setCellValue(row.netAmount() != null ? row.netAmount().toPlainString() : "");
        dataRow.createCell(colIdx).setCellValue(row.status() != null ? row.status().name() : "");
      }
      sheet.setColumnHidden(0, true);
      workbook.write(out);
      return out.toByteArray();
    }
  }
}
