package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.employees.core.application.EmployeeBulkSyncUseCases;
import io.github.alexistrejo11.pimienta.module.employees.core.application.query.EmployeeSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.RegisterEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeStatus;
import io.github.alexistrejo11.pimienta.module.employees.core.port.input.EmployeeManagementUseCases;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeDelete;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeDemote;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeExport;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeGetById;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeImport;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeListActive;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeePromote;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeRegister;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeRehire;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeSearch;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeSubmitForContract;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeTerminate;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployeeUpdate;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.doc.DocEmployees;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.ChangePositionRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeListItemResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeStatisticsResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeSummaryResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.RegisterEmployeeRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.UpdateEmployeeRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.mapper.EmployeeManagerWebMapper;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimit;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimitProfile;
import io.github.alexistrejo11.pimienta.shared.spreadsheet.SpreadsheetBulkImportResult;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;
import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/employees")
@RateLimit(profile = RateLimitProfile.STANDARD)
@DocEmployees
public class EmployeeManagerController {

  private final EmployeeManagementUseCases employeeManagementUseCases;
  private final EmployeeBulkSyncUseCases employeeBulkSyncUseCases;

  public EmployeeManagerController(
      EmployeeManagementUseCases employeeManagementUseCases,
      EmployeeBulkSyncUseCases employeeBulkSyncUseCases) {
    this.employeeManagementUseCases = employeeManagementUseCases;
    this.employeeBulkSyncUseCases = employeeBulkSyncUseCases;
  }

  @GetMapping("/statistics")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  @DocEmployeeStatistics
  public EmployeeStatisticsResponse getStatistics() {
    EmployeeStatistics statistics = employeeManagementUseCases.statistics();
    return EmployeeManagerWebMapper.toStatisticsResponse(statistics);
  }

  @GetMapping("/summary")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  @DocEmployeeSummary
  public EmployeeSummaryResponse getSummary() {
    EmployeeSummary summary = employeeManagementUseCases.summary();
    return EmployeeManagerWebMapper.toSummaryResponse(summary);
  }

  @GetMapping
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  @DocEmployeeSearch
  public PagedResponse<EmployeeListItemResponse> searchEmployees(
      @RequestParam(required = false) EmployeeStatus status,
      @RequestParam(required = false) String department,
      @RequestParam(required = false) String q,
      @ModelAttribute PageableRequest pageable) {
    EmployeeSearchCriteria criteria = new EmployeeSearchCriteria(status, blankToNull(department), blankToNull(q));
    Page<Employee> employees = employeeManagementUseCases.search(criteria, pageable.toPageable());
    return PagedResponse.map(employees, EmployeeManagerWebMapper::toListItem);
  }

  @GetMapping("/active")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  @DocEmployeeListActive
  public PagedResponse<EmployeeListItemResponse> getActiveEmployees(@ModelAttribute PageableRequest pageable) {
    Page<Employee> employees = employeeManagementUseCases.search(
        EmployeeSearchCriteria.onlyActive(), pageable.toPageable());
    return PagedResponse.map(employees, EmployeeManagerWebMapper::toListItem);
  }

  @GetMapping("/export")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  @DocEmployeeExport
  public ResponseEntity<byte[]> exportEmployees(
      @RequestParam(required = false) EmployeeStatus status,
      @RequestParam(required = false) String department,
      @RequestParam(required = false) String q,
      @ModelAttribute PageableRequest pageable)
      throws IOException {
    EmployeeSearchCriteria criteria = new EmployeeSearchCriteria(status, blankToNull(department), blankToNull(q));
    byte[] bytes = employeeBulkSyncUseCases.exportEmployees(criteria, pageable.toPageable());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=empleados_reporte.xlsx")
        .contentType(
            MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .body(bytes);
  }

  @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  @DocEmployeeImport
  public SpreadsheetBulkImportResult importEmployees(@RequestParam("file") MultipartFile file)
      throws IOException {
    if (file.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Archivo vacío");
    }
    return employeeBulkSyncUseCases.importEmployees(
        file.getInputStream(), file.getOriginalFilename());
  }

  @PostMapping
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  @ResponseStatus(HttpStatus.CREATED)
  @DocEmployeeRegister
  public EmployeeResponse registerEmployee(@Valid @RequestBody RegisterEmployeeRequest request) {
    RegisterEmployeeParams params = EmployeeManagerWebMapper.toRegisterParams(request);
    Employee registered = employeeManagementUseCases.register(params);
    return EmployeeManagerWebMapper.toResponse(registered);
  }

  @PutMapping("/{id}/submit-for-contract")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  @DocEmployeeSubmitForContract
  public EmployeeResponse submitEmployeeForContract(@PathVariable Long id) {
    Employee employee = employeeManagementUseCases.submitForContract(id);
    return EmployeeManagerWebMapper.toResponse(employee);
  }

  @GetMapping("/{id}")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  @DocEmployeeGetById
  public EmployeeResponse getEmployeeDetailById(@PathVariable Long id) {
    Employee employee = employeeManagementUseCases.getById(id);
    return EmployeeManagerWebMapper.toResponse(employee);
  }

  @PutMapping("/{id}")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  @DocEmployeeUpdate
  public EmployeeResponse updateEmployeeDetails(
      @PathVariable Long id, @Valid @RequestBody UpdateEmployeeRequest request) {
    Employee updated = employeeManagementUseCases.update(id, EmployeeManagerWebMapper.toUpdateParams(id, request));
    return EmployeeManagerWebMapper.toResponse(updated);
  }

  @PutMapping("/{id}/terminate")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  @DocEmployeeTerminate
  public EmployeeResponse terminateEmployee(@PathVariable Long id) {
    Employee employee = employeeManagementUseCases.terminate(id);
    return EmployeeManagerWebMapper.toResponse(employee);
  }

  @PutMapping("/{id}/rehire")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  @DocEmployeeRehire
  public EmployeeResponse rehireEmployee(@PathVariable Long id) {
    Employee employee = employeeManagementUseCases.rehire(id);
    return EmployeeManagerWebMapper.toResponse(employee);
  }

  @DeleteMapping("/{id}")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  @DocEmployeeDelete
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
    employeeManagementUseCases.delete(id);
    return ResponseEntity.noContent().build();
  }

  private static String blankToNull(String value) {
    if (value == null) {
      return null;
    }
    String trimmed = value.trim();
    return trimmed.isEmpty() ? null : trimmed;
  }
}
