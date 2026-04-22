package io.github.alexistrejo11.pimienta.module.employees.core.application;

import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.EmployeeExportRow;
import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.EmployeeImportRow;
import io.github.alexistrejo11.pimienta.module.employees.core.application.query.EmployeeSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.RegisterEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.UpdateEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.ContractType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeOnboardingPhase;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeStatus;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.WorkShift;
import io.github.alexistrejo11.pimienta.module.employees.core.port.input.EmployeeManagementUseCases;
import io.github.alexistrejo11.pimienta.module.employees.core.port.input.EmployeeSpreadsheetGenerator;
import io.github.alexistrejo11.pimienta.module.employees.core.port.input.EmployeeSpreadsheetParser;
import io.github.alexistrejo11.pimienta.module.employees.core.port.output.EmployeeRepository;
import io.github.alexistrejo11.pimienta.shared.spreadsheet.SpreadsheetBulkImportResult;
import io.github.alexistrejo11.pimienta.shared.spreadsheet.SpreadsheetBulkImportRowError;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmployeeBulkSyncUseCasesImpl implements EmployeeBulkSyncUseCases {

  private static final Logger log = LoggerFactory.getLogger(EmployeeBulkSyncUseCasesImpl.class);

  private static final int PAGE_SIZE = 500;

  private final EmployeeRepository employeeRepository;
  private final EmployeeManagementUseCases employeeManagementUseCases;
  private final EmployeeSpreadsheetParser spreadsheetParser;
  private final EmployeeSpreadsheetGenerator spreadsheetGenerator;

  public EmployeeBulkSyncUseCasesImpl(
      EmployeeRepository employeeRepository,
      EmployeeManagementUseCases employeeManagementUseCases,
      EmployeeSpreadsheetParser spreadsheetParser,
      EmployeeSpreadsheetGenerator spreadsheetGenerator) {
    this.employeeRepository = employeeRepository;
    this.employeeManagementUseCases = employeeManagementUseCases;
    this.spreadsheetParser = spreadsheetParser;
    this.spreadsheetGenerator = spreadsheetGenerator;
  }

  @Override
  public byte[] exportEmployees(EmployeeSearchCriteria criteria, Pageable pageable) throws IOException {
    EmployeeSearchCriteria effective = criteria != null ? criteria : EmployeeSearchCriteria.empty();

    log.debug(
        "export employees start status={} department={} textLen={}",
        effective.status(),
        effective.department(),
        effective.text() != null ? effective.text().length() : 0);

    List<EmployeeExportRow> rows = new ArrayList<>();
    int page = 0;
    Sort sort =
        pageable != null && pageable.getSort().isSorted()
            ? pageable.getSort()
            : Sort.by(Sort.Direction.ASC, "id");

    for (;;) {
      Page<Employee> p = employeeRepository.search(effective, PageRequest.of(page, PAGE_SIZE, sort));

      for (Employee e : p.getContent()) {
        rows.add(toExportRow(e));
      }
      if (!p.hasNext()) {
        break;
      }
      page++;
    }

    byte[] bytes = spreadsheetGenerator.generate(rows);

    log.debug("export employees complete rowCount={} bytesLen={}", rows.size(), bytes.length);
    return bytes;
  }

  private static EmployeeExportRow toExportRow(Employee e) {
    var personal = e.getPersonal();
    var ids = e.getOfficialIds();
    var employment = e.getEmployment();
    var pay = e.getCompensation();
    var benefits = e.getBenefits();
    return new EmployeeExportRow(
        e.getId(),
        personal.name(),
        personal.email(),
        personal.phone(),
        personal.address(),
        ids.curp(),
        ids.rfc(),
        ids.nss(),
        ids.clabe(),
        employment.position(),
        employment.department(),
        e.getStatus(),
        employment.contractType().name(),
        employment.workShift().name(),
        pay.salaryPerWeek() != null ? pay.salaryPerWeek().toPlainString() : "",
        pay.bonuses() != null ? pay.bonuses().toPlainString() : "",
        pay.foodVouchers() != null ? pay.foodVouchers().toPlainString() : "",
        benefits.integrationFactor() != null ? benefits.integrationFactor().toPlainString() : "");
  }

  @Override
  public SpreadsheetBulkImportResult importEmployees(InputStream file, String originalFilename)
      throws IOException {
    log.info(
        "import employees start originalFilename={} filenameLen={}",
        originalFilename != null ? originalFilename : "null",
        originalFilename != null ? originalFilename.length() : 0);

    List<EmployeeImportRow> parsed = spreadsheetParser.parse(file, originalFilename);

    int updated = 0;
    int created = 0;
    int skipped = 0;
    List<SpreadsheetBulkImportRowError> errors = new ArrayList<>();

    for (EmployeeImportRow row : parsed) {
      try {
        if (row.id() == null && (row.name() == null || row.name().isBlank())) {
          skipped++;
          continue;
        }
        if (row.id() == null && (row.email() == null || row.email().isBlank())) {
          errors.add(
              new SpreadsheetBulkImportRowError(
                  row.excelRowNumber(), "Email obligatorio para altas sin ID"));
          continue;
        }
        ContractType contractType = parseEnum(ContractType.class, row.contractTypeRaw(), ContractType.INDEFINITE);
        WorkShift workShift = parseEnum(WorkShift.class, row.workShiftRaw(), WorkShift.MORNING);
        EmployeeStatus statusParsed = parseStatus(row.statusRaw());

        if (row.id() != null) {
          Employee existing = employeeRepository.findById(row.id()).orElse(null);
          if (existing == null) {
            errors.add(
                new SpreadsheetBulkImportRowError(
                    row.excelRowNumber(), "No existe empleado con ID " + row.id()));
            continue;
          }
          UpdateEmployeeParams merged = mergeUpdate(row, existing, contractType, workShift);
          employeeManagementUseCases.update(row.id(), merged);
          if (statusParsed != null) {
            Employee after = employeeManagementUseCases.getById(row.id());
            if (!statusParsed.equals(after.getStatus())) {
              after.setStatus(statusParsed);
              employeeRepository.save(after);
            }
          }
          updated++;
        } else {
          RegisterEmployeeParams reg = new RegisterEmployeeParams(
              row.name().trim(),
              row.email().trim(),
              nz(row.phone(), ""),
              nz(row.address(), ""),
              nz(row.curp(), ""),
              nz(row.rfc(), ""),
              nz(row.nss(), ""),
              nz(row.clabe(), ""),
              "",
              nz(row.position(), ""),
              nz(row.department(), ""),
              contractType,
              workShift,
              row.salaryPerWeek() != null ? row.salaryPerWeek() : BigDecimal.ZERO,
              null,
              EmployeeOnboardingPhase.DRAFT);
          Employee createdEmp = employeeManagementUseCases.register(reg);
          if (statusParsed != null && !statusParsed.equals(createdEmp.getStatus())) {
            createdEmp.setStatus(statusParsed);
            employeeRepository.save(createdEmp);
          }
          created++;
        }
      } catch (Exception ex) {
        errors.add(
            new SpreadsheetBulkImportRowError(
                row.excelRowNumber(),
                ex.getMessage() != null ? ex.getMessage() : "Error al procesar fila"));
      }
    }

    SpreadsheetBulkImportResult result =
        new SpreadsheetBulkImportResult(updated, created, skipped, List.copyOf(errors));

    log.info(
        "import employees complete rowCount={} updated={} created={} skipped={} errorCount={}",
        parsed.size(),
        updated,
        created,
        skipped,
        errors.size());
    return result;
  }

  private static UpdateEmployeeParams mergeUpdate(
      EmployeeImportRow row,
      Employee e,
      ContractType contractType,
      WorkShift workShift) {
    var pay = e.getCompensation();
    var benefits = e.getBenefits();
    BigDecimal salary = row.salaryPerWeek() != null ? row.salaryPerWeek() : pay.salaryPerWeek();
    BigDecimal bonuses = row.bonuses() != null ? row.bonuses() : pay.bonuses();
    BigDecimal food = row.foodVouchers() != null ? row.foodVouchers() : pay.foodVouchers();
    BigDecimal integration =
        row.integrationFactor() != null ? row.integrationFactor() : benefits.integrationFactor();
    return new UpdateEmployeeParams(
        row.id(),
        nz(row.name(), e.getPersonal().name()),
        nz(row.email(), e.getPersonal().email()),
        nz(row.phone(), e.getPersonal().phone()),
        nz(row.address(), e.getPersonal().address()),
        nz(row.curp(), e.getOfficialIds().curp()),
        nz(row.rfc(), e.getOfficialIds().rfc()),
        nz(row.nss(), e.getOfficialIds().nss()),
        nz(row.clabe(), e.getOfficialIds().clabe()),
        nz(row.position(), e.getEmployment().position()),
        nz(row.department(), e.getEmployment().department()),
        contractType,
        workShift,
        salary,
        bonuses,
        food,
        integration);
  }

  private static String nz(String fromRow, String existing) {
    if (fromRow == null || fromRow.isBlank()) {
      return existing != null ? existing : "";
    }
    return fromRow.trim();
  }

  private static <E extends Enum<E>> E parseEnum(Class<E> type, String raw, E defaultValue) {
    if (raw == null || raw.isBlank()) {
      return defaultValue;
    }
    String s = raw.trim()
        .toUpperCase(Locale.ROOT)
        .replace(' ', '_')
        .replace('-', '_');
    return Enum.valueOf(type, s);
  }

  private static EmployeeStatus parseStatus(String raw) {
    if (raw == null || raw.isBlank()) {
      return null;
    }
    String s = raw.trim()
        .toUpperCase(Locale.ROOT)
        .replace(' ', '_')
        .replace('-', '_');
    return EmployeeStatus.valueOf(s);
  }
}
