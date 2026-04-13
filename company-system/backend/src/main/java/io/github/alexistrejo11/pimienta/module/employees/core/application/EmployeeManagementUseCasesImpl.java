package io.github.alexistrejo11.pimienta.module.employees.core.application;

import io.github.alexistrejo11.pimienta.module.employees.core.application.query.EmployeeSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.HireEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.UpdateEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.port.EmployeeManagementUseCases;
import io.github.alexistrejo11.pimienta.module.employees.core.port.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeManagementUseCasesImpl implements EmployeeManagementUseCases {

  private final EmployeeRepository employeeRepository;

  public EmployeeManagementUseCasesImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public EmployeeStatistics statistics() {
    return employeeRepository.statistics();
  }

  @Override
  public EmployeeSummary summary() {
    return employeeRepository.summarize();
  }

  @Override
  public Page<Employee> search(EmployeeSearchCriteria criteria, Pageable pageable) {
    EmployeeSearchCriteria effective = criteria != null ? criteria : EmployeeSearchCriteria.empty();
    return employeeRepository.search(effective, pageable);
  }

  @Override
  public Employee getById(Long id) {
    return employeeRepository
        .findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException(id));
  }

  @Override
  public Employee hire(HireEmployeeParams params) {
    Employee created = Employee.hire(params);
    return employeeRepository.save(created);
  }

  @Override
  public Employee update(Long id, UpdateEmployeeParams params) {
    Employee employee = getById(id);
    employee.setName(params.name());
    employee.setEmail(params.email());
    employee.setPhone(params.phone());
    employee.setAddress(params.address());
    employee.setCurp(params.curp());
    employee.setRfc(params.rfc());
    employee.setNss(params.nss());
    employee.setClabe(params.clabe());
    employee.setPosition(params.position());
    employee.setDepartment(params.department());
    employee.setContractType(params.contractType());
    employee.setWorkShift(params.workShift());
    employee.setSalaryPerWeek(params.salaryPerWeek());
    employee.setBonuses(params.bonuses());
    employee.setFoodVouchers(params.foodVouchers());
    employee.setIntegrationFactor(params.integrationFactor());
    return employeeRepository.save(employee);
  }

  @Override
  public Employee terminate(Long id) {
    Employee employee = getById(id);
    employee.terminate();
    return employeeRepository.save(employee);
  }

  @Override
  public Employee rehire(Long id) {
    Employee employee = getById(id);
    employee.rehire();
    return employeeRepository.save(employee);
  }

  @Override
  public Employee changePosition(Long id, String newPosition) {
    Employee employee = getById(id);
    employee.setPosition(newPosition);
    return employeeRepository.save(employee);
  }

  @Override
  public void delete(Long id) {
    Employee employee = getById(id);
    employee.delete();
    employeeRepository.save(employee);
  }
}
