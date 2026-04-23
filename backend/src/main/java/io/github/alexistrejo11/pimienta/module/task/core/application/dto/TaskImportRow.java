package io.github.alexistrejo11.pimienta.module.task.core.application.dto;

import io.github.alexistrejo11.pimienta.module.task.core.domain.ChecklistDraft;
import java.util.List;

/**
 * Row read from task import spreadsheet (1-based {@code excelRowNumber} matches the sheet). {@code
 * statusRaw} is the cell text; the use case parses the enum.
 */
public record TaskImportRow(
    int excelRowNumber,
    Long id,
    String title,
    String statusRaw,
    List<ChecklistDraft> checklistLines) {}
