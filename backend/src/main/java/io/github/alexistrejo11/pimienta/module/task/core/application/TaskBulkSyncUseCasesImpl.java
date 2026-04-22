package io.github.alexistrejo11.pimienta.module.task.core.application;

import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.Headquarter;
import io.github.alexistrejo11.pimienta.module.headquarter.core.port.output.HeadquarterRepository;
import io.github.alexistrejo11.pimienta.module.task.core.application.command.CreateTaskCommand;
import io.github.alexistrejo11.pimienta.module.task.core.application.dto.TaskBulkImportResult;
import io.github.alexistrejo11.pimienta.module.task.core.application.dto.TaskBulkImportRowError;
import io.github.alexistrejo11.pimienta.module.task.core.application.dto.TaskExportRow;
import io.github.alexistrejo11.pimienta.module.task.core.application.dto.TaskImportRow;
import io.github.alexistrejo11.pimienta.module.task.core.application.query.TaskSearchCriteria;
import io.github.alexistrejo11.pimienta.module.task.core.domain.ChecklistDraft;
import io.github.alexistrejo11.pimienta.module.task.core.domain.Task;
import io.github.alexistrejo11.pimienta.module.task.core.port.TaskRepository;
import io.github.alexistrejo11.pimienta.module.task.core.port.TaskSpreadsheetGenerator;
import io.github.alexistrejo11.pimienta.module.task.core.port.TaskSpreadsheetParser;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TaskBulkSyncUseCasesImpl implements TaskBulkSyncUseCases {

  private static final Logger log = LoggerFactory.getLogger(TaskBulkSyncUseCasesImpl.class);

  private static final int EXPORT_PAGE_SIZE = 500;

  private final TaskRepository taskRepository;
  private final TaskManagementUseCases taskManagementUseCases;
  private final HeadquarterRepository headquarterRepository;
  private final TaskSpreadsheetParser spreadsheetParser;
  private final TaskSpreadsheetGenerator spreadsheetGenerator;

  public TaskBulkSyncUseCasesImpl(
      TaskRepository taskRepository,
      TaskManagementUseCases taskManagementUseCases,
      HeadquarterRepository headquarterRepository,
      TaskSpreadsheetParser spreadsheetParser,
      TaskSpreadsheetGenerator spreadsheetGenerator) {
    this.taskRepository = taskRepository;
    this.taskManagementUseCases = taskManagementUseCases;
    this.headquarterRepository = headquarterRepository;
    this.spreadsheetParser = spreadsheetParser;
    this.spreadsheetGenerator = spreadsheetGenerator;
  }

  @Override
  public byte[] exportTasks(TaskSearchCriteria criteria, Pageable pageable) throws IOException {
    TaskSearchCriteria effective = criteria != null ? criteria : TaskSearchCriteria.empty();

    log.debug(
        "export tasks start headquarterId={} projectId={} opportunityId={} assignedToId={} status={}",
        effective.headquarterId(),
        effective.projectId(),
        effective.opportunityId(),
        effective.assignedToId(),
        effective.status());

    List<TaskExportRow> rows = new ArrayList<>();
    int page = 0;
    Sort sort =
        pageable != null && pageable.getSort().isSorted()
            ? pageable.getSort()
            : Sort.by(Sort.Direction.ASC, "id");

    for (; ; ) {
      Page<Task> p = taskRepository.search(effective, PageRequest.of(page, EXPORT_PAGE_SIZE, sort));

      for (Task task : p.getContent()) {
        rows.add(toExportRow(task));
      }
      if (!p.hasNext()) {
        break;
      }
      page++;
    }

    byte[] bytes = spreadsheetGenerator.generate(rows);

    log.debug("export tasks complete rowCount={} bytesLen={}", rows.size(), bytes.length);
    return bytes;
  }

  private TaskExportRow toExportRow(Task task) {
    String hqName = "";
    Long hqId = task.getHeadquarterId();
    if (hqId != null) {
      Optional<Headquarter> hq = headquarterRepository.findById(hqId);
      hqName = hq.map(Headquarter::getName).orElse("");
    }
    StringBuilder checklist = new StringBuilder();
    int i = 0;
    for (Task.ChecklistItem item : task.getChecklist()) {
      if (i++ > 0) {
        checklist.append('\n');
      }
      checklist.append(item.description() != null ? item.description() : "");
    }
    return new TaskExportRow(
        task.getId(),
        task.getTitle(),
        task.getStatus(),
        checklist.toString(),
        hqName);
  }

  @Override
  public TaskBulkImportResult importTasks(InputStream file, String originalFilename)
      throws IOException {
    log.info(
        "import tasks start originalFilename={} filenameLen={}",
        originalFilename != null ? originalFilename : "null",
        originalFilename != null ? originalFilename.length() : 0);

    List<TaskImportRow> parsed = spreadsheetParser.parse(file, originalFilename);

    int updated = 0;
    int created = 0;
    int skipped = 0;
    List<TaskBulkImportRowError> errors = new ArrayList<>();

    for (TaskImportRow row : parsed) {
      Task.Status status;
      try {
        status = parseStatus(row.statusRaw());
      } catch (IllegalArgumentException ex) {
        errors.add(
            new TaskBulkImportRowError(
                row.excelRowNumber(), "Estado no válido: " + row.statusRaw()));
        continue;
      }
      if (row.title() == null || row.title().isBlank()) {
        if (row.id() == null) {
          skipped++;
        } else {
          errors.add(
              new TaskBulkImportRowError(row.excelRowNumber(), "El título es obligatorio"));
        }
        continue;
      }
      if (row.id() != null) {
        Optional<Task> opt = taskRepository.findById(row.id());
        if (opt.isEmpty()) {
          errors.add(
              new TaskBulkImportRowError(
                  row.excelRowNumber(), "No existe tarea con ID " + row.id()));
          continue;
        }
        Task task = opt.get();
        applyImportRowToTask(task, row.title(), status, row.checklistLines());
        taskRepository.save(task);
        updated++;
      } else {
        CreateTaskCommand cmd =
            new CreateTaskCommand(
                row.title().trim(),
                "",
                Task.Priority.MEDIUM,
                null,
                null,
                null,
                null,
                null,
                status,
                row.checklistLines() != null ? row.checklistLines() : List.of());
        taskManagementUseCases.create(cmd);
        created++;
      }
    }

    TaskBulkImportResult result = new TaskBulkImportResult(updated, created, skipped, List.copyOf(errors));

    log.info(
        "import tasks complete rowCount={} updated={} created={} skipped={} errorCount={}",
        parsed.size(),
        updated,
        created,
        skipped,
        errors.size());
    return result;
  }

  private static void applyImportRowToTask(
      Task task, String title, Task.Status status, List<ChecklistDraft> lines) {
    task.setTitle(title != null ? title.trim() : "");
    task.setChecklist(checklistFromImportLines(lines));
    task.setStatus(status);
    LocalDateTime when = LocalDateTime.now();
    if (status == Task.Status.COMPLETED) {
      task.setCompletedAt(when);
    } else {
      task.setCompletedAt(null);
    }
    task.touch();
  }

  private static List<Task.ChecklistItem> checklistFromImportLines(List<ChecklistDraft> lines) {
    List<Task.ChecklistItem> out = new ArrayList<>();
    if (lines == null) {
      return out;
    }
    int order = 0;
    for (ChecklistDraft line : lines) {
      String d = line.description() != null ? line.description().strip() : "";
      if (!d.isEmpty()) {
        out.add(new Task.ChecklistItem(d, false, null, order++));
      }
    }
    return out;
  }

  private static Task.Status parseStatus(String raw) {
    if (raw == null || raw.isBlank()) {
      return Task.Status.PENDING;
    }
    String s =
        raw.trim().toUpperCase(Locale.ROOT).replace(' ', '_').replace('-', '_');
    return Task.Status.valueOf(s);
  }
}
