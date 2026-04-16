package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.employees.core.application.query.EmployeeSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeStatus;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.core.port.EmployeeManagementUseCases;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.ChangePositionRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeListItemResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeStatisticsResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeSummaryResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.HireEmployeeRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.UpdateEmployeeRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.mapper.EmployeeManagerWebMapper;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimit;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimitProfile;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;
import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/v1/employees")
@RateLimit(profile = RateLimitProfile.STANDARD)
public class EmployeeManagerController {

  private final EmployeeManagementUseCases employeeManagementUseCases;

  public EmployeeManagerController(EmployeeManagementUseCases employeeManagementUseCases) {
    this.employeeManagementUseCases = employeeManagementUseCases;
  }

  @GetMapping("/statistics")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public EmployeeStatisticsResponse getStatistics() {
    EmployeeStatistics statistics = employeeManagementUseCases.statistics();
    return EmployeeManagerWebMapper.toStatisticsResponse(statistics);
  }

  @GetMapping("/summary")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public EmployeeSummaryResponse getSummary() {
    EmployeeSummary summary = employeeManagementUseCases.summary();
    return EmployeeManagerWebMapper.toSummaryResponse(summary);
  }

  @GetMapping
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
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
  public PagedResponse<EmployeeListItemResponse> getActiveEmployees(@ModelAttribute PageableRequest pageable) {
    Page<Employee> employees = employeeManagementUseCases.search(
        EmployeeSearchCriteria.onlyActive(), pageable.toPageable());
    return PagedResponse.map(employees, EmployeeManagerWebMapper::toListItem);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EmployeeResponse createEmployee(@Valid @RequestBody HireEmployeeRequest request) {
    Employee hired = employeeManagementUseCases.hire(EmployeeManagerWebMapper.toHireParams(request));
    return EmployeeManagerWebMapper.toResponse(hired);
  }

  @GetMapping("/{id}")
  public EmployeeResponse getEmployeeDetailById(@PathVariable Long id) {
    Employee employee = employeeManagementUseCases.getById(id);
    return EmployeeManagerWebMapper.toResponse(employee);
  }

  @PutMapping("/{id}")
  public EmployeeResponse updateEmployeeDetails(
      @PathVariable Long id, @Valid @RequestBody UpdateEmployeeRequest request) {
    Employee updated = employeeManagementUseCases.update(id, EmployeeManagerWebMapper.toUpdateParams(id, request));
    return EmployeeManagerWebMapper.toResponse(updated);
  }

  @PutMapping("/{id}/terminate")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public EmployeeResponse terminateEmployee(@PathVariable Long id) {
    Employee employee = employeeManagementUseCases.terminate(id);
    return EmployeeManagerWebMapper.toResponse(employee);
  }

  @PutMapping("/{id}/rehire")
  public EmployeeResponse rehireEmployee(@PathVariable Long id) {
    Employee employee = employeeManagementUseCases.rehire(id);
    return EmployeeManagerWebMapper.toResponse(employee);
  }

  @PutMapping("/{id}/promote")
  public EmployeeResponse promoteEmployee(
      @PathVariable Long id, @Valid @RequestBody ChangePositionRequest request) {
    Employee updated = employeeManagementUseCases.changePosition(id, request.position().trim());
    return EmployeeManagerWebMapper.toResponse(updated);
  }

  @PutMapping("/{id}/demote")
  public EmployeeResponse demoteEmployee(
      @PathVariable Long id, @Valid @RequestBody ChangePositionRequest request) {
    Employee updated = employeeManagementUseCases.changePosition(id, request.position().trim());
    return EmployeeManagerWebMapper.toResponse(updated);
  }

  @DeleteMapping("/{id}")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
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
