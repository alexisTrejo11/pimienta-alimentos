package io.github.alexistrejo11.pimienta.module.task.core.port;

import io.github.alexistrejo11.pimienta.module.task.core.application.dto.TaskExportRow;
import java.io.IOException;
import java.util.List;

public interface TaskSpreadsheetGenerator {

  byte[] generate(List<TaskExportRow> rows) throws IOException;
}
