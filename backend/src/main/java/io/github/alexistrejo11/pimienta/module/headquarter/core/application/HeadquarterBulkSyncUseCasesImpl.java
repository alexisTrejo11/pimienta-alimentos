package io.github.alexistrejo11.pimienta.module.headquarter.core.application;

import io.github.alexistrejo11.pimienta.module.headquarter.core.application.command.CreateHeadquarterCommand;
import io.github.alexistrejo11.pimienta.module.headquarter.core.application.command.UpdateHeadquarterCommand;
import io.github.alexistrejo11.pimienta.module.headquarter.core.application.dto.HeadquarterExportRow;
import io.github.alexistrejo11.pimienta.module.headquarter.core.application.dto.HeadquarterImportRow;
import io.github.alexistrejo11.pimienta.module.headquarter.core.domain.Headquarter;
import io.github.alexistrejo11.pimienta.module.headquarter.core.port.input.HeadquarterBulkSyncUseCases;
import io.github.alexistrejo11.pimienta.module.headquarter.core.port.input.HeadquarterSpreadsheetGenerator;
import io.github.alexistrejo11.pimienta.module.headquarter.core.port.input.HeadquarterSpreadsheetParser;
import io.github.alexistrejo11.pimienta.module.headquarter.core.port.input.HeadquarterUseCases;
import io.github.alexistrejo11.pimienta.shared.spreadsheet.SpreadsheetBulkImportResult;
import io.github.alexistrejo11.pimienta.shared.spreadsheet.SpreadsheetBulkImportRowError;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class HeadquarterBulkSyncUseCasesImpl implements HeadquarterBulkSyncUseCases {

  private static final int PAGE_SIZE = 500;

  private final HeadquarterUseCases headquarterUseCases;
  private final HeadquarterSpreadsheetParser spreadsheetParser;
  private final HeadquarterSpreadsheetGenerator spreadsheetGenerator;

  public HeadquarterBulkSyncUseCasesImpl(
      HeadquarterUseCases headquarterUseCases,
      HeadquarterSpreadsheetParser spreadsheetParser,
      HeadquarterSpreadsheetGenerator spreadsheetGenerator) {
    this.headquarterUseCases = headquarterUseCases;
    this.spreadsheetParser = spreadsheetParser;
    this.spreadsheetGenerator = spreadsheetGenerator;
  }

  @Override
  public byte[] exportHeadquarters(Pageable pageable) throws IOException {
    List<HeadquarterExportRow> rows = new ArrayList<>();
    int page = 0;
    Sort sort = pageable != null && pageable.getSort().isSorted()
        ? pageable.getSort()
        : Sort.by(Sort.Direction.ASC, "id");
    for (;;) {
      Page<Headquarter> p = headquarterUseCases.getBy(PageRequest.of(page, PAGE_SIZE, sort));
      for (Headquarter h : p.getContent()) {
        rows.add(
            new HeadquarterExportRow(
                h.getId(), h.getName(), h.getAddress(), h.getDescription()));
      }
      if (!p.hasNext()) {
        break;
      }
      page++;
    }
    return spreadsheetGenerator.generate(rows);
  }

  @Override
  public SpreadsheetBulkImportResult importHeadquarters(InputStream file, String originalFilename)
      throws IOException {
    List<HeadquarterImportRow> parsed = spreadsheetParser.parse(file, originalFilename);
    int updated = 0;
    int created = 0;
    int skipped = 0;
    List<SpreadsheetBulkImportRowError> errors = new ArrayList<>();
    for (HeadquarterImportRow row : parsed) {
      if (row.name() == null || row.name().isBlank()) {
        if (row.id() == null) {
          skipped++;
        } else {
          errors.add(
              new SpreadsheetBulkImportRowError(row.excelRowNumber(), "El nombre es obligatorio"));
        }
        continue;
      }
      String address = row.address() != null ? row.address() : "";
      String description = row.description() != null ? row.description() : "";
      if (row.id() != null) {
        try {
          headquarterUseCases.update(
              row.id(),
              new UpdateHeadquarterCommand(row.name().trim(), address, description));
          updated++;
        } catch (Exception ex) {
          errors.add(
              new SpreadsheetBulkImportRowError(
                  row.excelRowNumber(), ex.getMessage() != null ? ex.getMessage() : "Error al actualizar"));
        }
      } else {
        try {
          headquarterUseCases.create(
              new CreateHeadquarterCommand(row.name().trim(), address, description));
          created++;
        } catch (Exception ex) {
          errors.add(
              new SpreadsheetBulkImportRowError(
                  row.excelRowNumber(), ex.getMessage() != null ? ex.getMessage() : "Error al crear"));
        }
      }
    }
    return new SpreadsheetBulkImportResult(updated, created, skipped, List.copyOf(errors));
  }
}
