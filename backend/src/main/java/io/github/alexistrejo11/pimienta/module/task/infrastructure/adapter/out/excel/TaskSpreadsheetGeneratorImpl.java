package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.out.excel;

import io.github.alexistrejo11.pimienta.module.task.core.application.dto.TaskExportRow;
import io.github.alexistrejo11.pimienta.module.task.core.port.TaskSpreadsheetGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class TaskSpreadsheetGeneratorImpl implements TaskSpreadsheetGenerator {

  @Override
  public byte[] generate(List<TaskExportRow> rows) throws IOException {
    try (XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("Tasks");
      Row header = sheet.createRow(0);
      int c = 0;
      for (TaskSpreadsheetColumn col : TaskSpreadsheetColumn.values()) {
        Cell cell = header.createCell(c++);
        cell.setCellValue(col.header());
      }
      int r = 1;
      for (TaskExportRow row : rows) {
        Row dataRow = sheet.createRow(r++);
        int colIdx = 0;
        if (row.id() != null) {
          dataRow.createCell(colIdx++).setCellValue(row.id());
        } else {
          dataRow.createCell(colIdx++).setCellValue("");
        }
        dataRow.createCell(colIdx++).setCellValue(row.title() != null ? row.title() : "");
        dataRow
            .createCell(colIdx++)
            .setCellValue(row.status() != null ? row.status().name() : "");
        dataRow
            .createCell(colIdx++)
            .setCellValue(row.checklistCellText() != null ? row.checklistCellText() : "");
        dataRow
            .createCell(colIdx)
            .setCellValue(row.headquarterName() != null ? row.headquarterName() : "");
      }
      sheet.setColumnHidden(0, true);
      workbook.write(out);
      return out.toByteArray();
    }
  }
}
