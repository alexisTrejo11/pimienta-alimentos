package io.github.alexistrejo11.pimienta.module.employees.core.application;

import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.RegisterEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.UpdateEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.application.query.EmployeeSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.exception.EmployeeNotFoundException;
import io.github.alexistrejo11.pimienta.module.employees.core.port.input.EmployeeManagementUseCases;
import io.github.alexistrejo11.pimienta.module.employees.core.port.output.EmployeeRepository;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeManagementUseCasesImpl implements EmployeeManagementUseCases {

  private static final Logger log = LoggerFactory.getLogger(EmployeeManagementUseCasesImpl.class);

  private final EmployeeRepository employeeRepository;

  public EmployeeManagementUseCasesImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public EmployeeStatistics statistics() {
    log.debug("employee statistics query start");

    EmployeeStatistics stats = employeeRepository.statistics();

    log.debug(
        "employee statistics query complete total={} active={} notActive={}",
        stats.total(),
        stats.active(),
        stats.notActive());
    return stats;
  }

  @Override
  public EmployeeSummary summary() {
    log.debug("employee summary query start");

    EmployeeSummary s = employeeRepository.summarize();

    log.debug(
        "employee summary query complete totalNotDeleted={} departmentBuckets={}",
        s.totalNotDeleted(),
        s.headcountByDepartment() != null ? s.headcountByDepartment().size() : 0);
    return s;
  }

  @Override
  public Page<Employee> search(EmployeeSearchCriteria criteria, Pageable pageable) {
    EmployeeSearchCriteria effective = criteria != null ? criteria : EmployeeSearchCriteria.empty();

    log.debug(
        "search employees query start page={} size={} status={} department={} textLen={}",
        pageable != null ? pageable.getPageNumber() : null,
        pageable != null ? pageable.getPageSize() : null,
        effective.status(),
        effective.department(),
        effective.text() != null ? effective.text().length() : 0);

    Page<Employee> page = employeeRepository.search(effective, pageable);

    log.debug(
        "search employees query complete totalElements={} numberOfElements={}",
        page.getTotalElements(),
        page.getNumberOfElements());
    return page;
  }

  @Override
  public Employee getById(Long id) {
    log.debug("get employee by id query start employeeId={}", id);

    Employee employee =
        employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

    log.debug("get employee by id query complete employeeId={}", employee.getId());
    return employee;
  }

  @Override
  public Employee register(RegisterEmployeeParams params) {
    log.info(
        "register employee start contractType={} workShift={} department={} onboardingPhase={}",
        params.contractType(),
        params.workShift(),
        params.department(),
        params.onboardingPhase());

    Employee created = params.toEmployee();

    Employee saved = employeeRepository.save(created);

    log.info("register employee complete employeeId={}", saved.getId());
    return saved;
  }

  @Override
  public Employee activateEmploymentAfterContract(Long employeeId, LocalDate hireDate) {
    log.info(
        "activate employment after contract start employeeId={} hireDate={}",
        employeeId,
        hireDate);

    Employee employee = getById(employeeId);
    employee.activateEmploymentAfterSignedContract(hireDate);

    Employee saved = employeeRepository.save(employee);

    log.info("activate employment after contract complete employeeId={}", saved.getId());
    return saved;
  }

  @Override
  public Employee submitForContract(Long id) {
    log.info("submit employee for contract start employeeId={}", id);

    Employee employee = getById(id);
    employee.submitForContractRegistration();

    Employee saved = employeeRepository.save(employee);

    log.info("submit employee for contract complete employeeId={}", saved.getId());
    return saved;
  }

  @Override
  public Employee update(Long id, UpdateEmployeeParams params) {
    log.info("update employee start employeeId={}", id);

    Employee employee = getById(id);
    params.applyPartialMerge(employee);
    employee.touch();

    Employee saved = employeeRepository.save(employee);

    log.info("update employee complete employeeId={}", saved.getId());
    return saved;
  }

  @Override
  public Employee terminate(Long id) {
    log.info("terminate employee start employeeId={}", id);

    Employee employee = getById(id);
    employee.terminate();

    Employee saved = employeeRepository.save(employee);

    log.info("terminate employee complete employeeId={}", saved.getId());
    return saved;
  }

  @Override
  public Employee rehire(Long id) {
    log.info("rehire employee start employeeId={}", id);

    Employee employee = getById(id);
    employee.rehire();

    Employee saved = employeeRepository.save(employee);

    log.info("rehire employee complete employeeId={}", saved.getId());
    return saved;
  }

  @Override
  public void delete(Long id) {
    log.info("delete employee start employeeId={}", id);

    Employee employee = getById(id);
    employee.delete();

    employeeRepository.save(employee);
    log.info("delete employee complete employeeId={}", id);
  }
}
