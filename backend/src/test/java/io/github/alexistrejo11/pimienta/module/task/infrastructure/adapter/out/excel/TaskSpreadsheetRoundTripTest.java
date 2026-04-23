package io.github.alexistrejo11.pimienta.module.task.infrastructure.adapter.out.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.alexistrejo11.pimienta.module.task.core.application.dto.TaskExportRow;
import io.github.alexistrejo11.pimienta.module.task.core.application.dto.TaskImportRow;
import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import java.io.ByteArrayInputStream;
import java.util.List;
import org.junit.jupiter.api.Test;

class TaskSpreadsheetRoundTripTest {

  @Test
  void generatorThenParser_preservesRows() throws Exception {
    TaskSpreadsheetParserImpl parser = new TaskSpreadsheetParserImpl();
    TaskSpreadsheetGeneratorImpl generator = new TaskSpreadsheetGeneratorImpl();
    List<TaskExportRow> rows =
        List.of(
            new TaskExportRow(
                42L, "Demo", Task.Status.IN_PROGRESS, "uno\ndos", "Sede Norte"),
            new TaskExportRow(null, "Nueva", Task.Status.PENDING, "", ""));
    byte[] bytes = generator.generate(rows);
    List<TaskImportRow> parsed = parser.parse(new ByteArrayInputStream(bytes), "tasks.xlsx");
    assertEquals(2, parsed.size());
    TaskImportRow first = parsed.get(0);
    assertEquals(2, first.excelRowNumber());
    assertEquals(42L, first.id());
    assertEquals("Demo", first.title());
    assertEquals("IN_PROGRESS", first.statusRaw());
    assertEquals(2, first.checklistLines().size());
    assertEquals("uno", first.checklistLines().get(0).description());
    assertEquals("dos", first.checklistLines().get(1).description());
    TaskImportRow second = parsed.get(1);
    assertEquals(3, second.excelRowNumber());
    assertEquals(null, second.id());
    assertEquals("Nueva", second.title());
  }

  @Test
  void parser_rejectsNonXlsxExtensionWhenProvided() {
    TaskSpreadsheetParserImpl parser = new TaskSpreadsheetParserImpl();
    assertThrows(IllegalArgumentException.class, () -> parser.parse(new ByteArrayInputStream(new byte[0]), "a.csv"));
  }
}
