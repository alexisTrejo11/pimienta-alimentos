package io.github.alexistrejo11.pimienta.module.headquarter.infrastructure.adapter.out.excel;

import io.github.alexistrejo11.pimienta.module.headquarter.core.application.dto.HeadquarterExportRow;
import io.github.alexistrejo11.pimienta.module.headquarter.core.port.input.HeadquarterSpreadsheetGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class HeadquarterSpreadsheetGeneratorImpl implements HeadquarterSpreadsheetGenerator {

  @Override
  public byte[] generate(List<HeadquarterExportRow> rows) throws IOException {
    try (XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("Headquarters");
      Row header = sheet.createRow(0);
      int c = 0;
      for (HeadquarterSpreadsheetColumn col : HeadquarterSpreadsheetColumn.values()) {
        header.createCell(c++).setCellValue(col.header());
      }
      int r = 1;
      for (HeadquarterExportRow row : rows) {
        Row dataRow = sheet.createRow(r++);
        int colIdx = 0;
        if (row.id() != null) {
          dataRow.createCell(colIdx++).setCellValue(row.id());
        } else {
          dataRow.createCell(colIdx++).setCellValue("");
        }
        dataRow.createCell(colIdx++).setCellValue(row.name() != null ? row.name() : "");
        dataRow.createCell(colIdx++).setCellValue(row.address() != null ? row.address() : "");
        dataRow.createCell(colIdx).setCellValue(row.description() != null ? row.description() : "");
      }
      sheet.setColumnHidden(0, true);
      workbook.write(out);
      return out.toByteArray();
    }
  }
}
