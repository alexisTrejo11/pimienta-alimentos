package io.github.alexistrejo11.pimienta.module.task.core.port;

import io.github.alexistrejo11.pimienta.module.task.core.application.dto.TaskImportRow;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface TaskSpreadsheetParser {

  /**
   * Lee la primera hoja de un .xlsx; fila 1 = cabeceras ({@code ID}, {@code Title}, {@code
   * Status}, {@code Checklist}).
   *
   * @param originalFilename opcional, para validar extensión
   */
  List<TaskImportRow> parse(InputStream xlsx, String originalFilename) throws IOException;
}
