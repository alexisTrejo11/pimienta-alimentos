package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.out.excel;

import io.github.alexistrejo11.pimienta.module.task.core.application.dto.TaskImportRow;
import io.github.alexistrejo11.pimienta.module.task.core.domain.ChecklistDraft;
import io.github.alexistrejo11.pimienta.module.task.core.port.TaskSpreadsheetParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

@Component
public class TaskSpreadsheetParserImpl implements TaskSpreadsheetParser {

  private static final DataFormatter DATA_FORMATTER = new DataFormatter();

  @Override
  public List<TaskImportRow> parse(InputStream xlsx, String originalFilename) throws IOException {
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
      requireColumn(col, TaskSpreadsheetColumn.ID);
      requireColumn(col, TaskSpreadsheetColumn.TITLE);
      requireColumn(col, TaskSpreadsheetColumn.STATUS);
      requireColumn(col, TaskSpreadsheetColumn.CHECKLIST);

      List<TaskImportRow> rows = new ArrayList<>();
      int lastRow = sheet.getLastRowNum();
      for (int r = 1; r <= lastRow; r++) {
        Row row = sheet.getRow(r);
        if (row == null) {
          continue;
        }
        int excelRowNumber = r + 1;
        String title = cellString(row, col, TaskSpreadsheetColumn.TITLE);
        Long id = cellLong(row, col, TaskSpreadsheetColumn.ID);
        String statusRaw = cellString(row, col, TaskSpreadsheetColumn.STATUS);
        String checklistRaw = cellString(row, col, TaskSpreadsheetColumn.CHECKLIST);

        if (isBlankRow(id, title, statusRaw, checklistRaw)) {
          continue;
        }

        List<ChecklistDraft> lines = parseChecklistLines(checklistRaw);
        rows.add(
            new TaskImportRow(
                excelRowNumber, id, title != null ? title : "", statusRaw != null ? statusRaw : "", lines));
      }
      return rows;
    }
  }

  private static void requireColumn(Map<String, Integer> col, TaskSpreadsheetColumn c) {
    if (!col.containsKey(normalizeHeader(c.header()))) {
      throw new IllegalArgumentException("Falta la columna obligatoria: " + c.header());
    }
  }

  private static Map<String, Integer> mapHeaders(Row headerRow) {
    Map<String, Integer> map = new HashMap<>();
    short lastCell = headerRow.getLastCellNum();
    for (int c = 0; c < lastCell; c++) {
      Cell cell = headerRow.getCell(c);
      String text = DATA_FORMATTER.formatCellValue(cell).trim();
      if (!text.isEmpty()) {
        map.put(normalizeHeader(text), c);
      }
    }
    return map;
  }

  private static String normalizeHeader(String h) {
    return h.trim().toLowerCase(Locale.ROOT);
  }

  private static String cellString(Row row, Map<String, Integer> col, TaskSpreadsheetColumn key) {
    Integer idx = col.get(normalizeHeader(key.header()));
    if (idx == null) {
      return "";
    }
    Cell cell = row.getCell(idx);
    return cell == null ? "" : DATA_FORMATTER.formatCellValue(cell).trim();
  }

  private static Long cellLong(Row row, Map<String, Integer> col, TaskSpreadsheetColumn key) {
    Integer idx = col.get(normalizeHeader(key.header()));
    if (idx == null) {
      return null;
    }
    Cell cell = row.getCell(idx);
    if (cell == null) {
      return null;
    }
    try {
      CellType type = cell.getCellType();
      if (type == CellType.FORMULA) {
        type = cell.getCachedFormulaResultType();
      }
      switch (type) {
        case NUMERIC:
          double n = cell.getNumericCellValue();
          return Math.round(n);
        case STRING:
          String s = cell.getStringCellValue().trim();
          if (s.isEmpty() || "-".equals(s)) {
            return null;
          }
          return Long.parseLong(s);
        case BLANK:
          return null;
        default:
          String t = DATA_FORMATTER.formatCellValue(cell).trim();
          if (t.isEmpty() || "-".equals(t)) {
            return null;
          }
          return Long.parseLong(t);
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("ID inválido en fila " + (row.getRowNum() + 1));
    }
  }

  private static boolean isBlankRow(Long id, String title, String status, String checklist) {
    boolean noId = id == null;
    boolean noTitle = title == null || title.isBlank();
    boolean noStatus = status == null || status.isBlank();
    boolean noCheck = checklist == null || checklist.isBlank();
    return noId && noTitle && noStatus && noCheck;
  }

  private static List<ChecklistDraft> parseChecklistLines(String raw) {
    List<ChecklistDraft> lines = new ArrayList<>();
    if (raw == null || raw.isBlank()) {
      return lines;
    }
    String[] parts = raw.split("\\R");
    int order = 0;
    for (String part : parts) {
      String desc = part.trim();
      if (!desc.isEmpty()) {
        lines.add(new ChecklistDraft(desc, order++));
      }
    }
    return lines;
  }
}
