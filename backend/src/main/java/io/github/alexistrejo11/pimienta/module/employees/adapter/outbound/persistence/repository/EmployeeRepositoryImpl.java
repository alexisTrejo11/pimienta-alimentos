package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.repository;

import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model.EmployeeJpaEntity;
import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.EmployeePersistenceMapper;
import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model.EmployeeSpecifications;
import io.github.alexistrejo11.pimienta.module.employees.core.application.query.EmployeeSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.DepartmentHeadcount;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeStatus;
import io.github.alexistrejo11.pimienta.module.employees.core.port.output.EmployeeRepository;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

  private final EmployeeJpaRepository jpaRepository;

  public EmployeeRepositoryImpl(EmployeeJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Optional<Employee> findById(long id) {
    return jpaRepository.findByIdAndDeletedAtIsNull(id).map(EmployeePersistenceMapper::toDomain);
  }

  @Override
  public Page<Employee> search(EmployeeSearchCriteria criteria, Pageable pageable) {
    Specification<EmployeeJpaEntity> spec = EmployeeSpecifications.fromCriteria(criteria);
    return jpaRepository.findAll(spec, pageable).map(EmployeePersistenceMapper::toDomain);
  }

  @Override
  public Employee save(Employee employee) {
    EmployeeJpaEntity entity = EmployeePersistenceMapper.toJpa(employee);
    EmployeeJpaEntity saved = jpaRepository.save(entity);
    return EmployeePersistenceMapper.toDomain(saved);
  }

  @Override
  public long countActive() {
    return jpaRepository.countByDeletedAtIsNullAndStatus(EmployeeStatus.ACTIVE);
  }

  @Override
  public EmployeeStatistics statistics() {
    long total = jpaRepository.countByDeletedAtIsNull();
    long active = jpaRepository.countByDeletedAtIsNullAndStatus(EmployeeStatus.ACTIVE);
    long notActive = total - active;
    return new EmployeeStatistics(total, active, notActive);
  }

  @Override
  public EmployeeSummary summarize() {
    long total = jpaRepository.countByDeletedAtIsNull();
    List<Object[]> rows = jpaRepository.countByDepartmentRows();
    List<DepartmentHeadcount> byDepartment = new ArrayList<>(rows.size());
    for (Object[] row : rows) {
      String dept = row[0] != null ? row[0].toString() : "";
      long cnt = row[1] instanceof Long longCount
          ? longCount
          : row[1] != null ? ((Number) row[1]).longValue() : 0L;
      byDepartment.add(new DepartmentHeadcount(dept, cnt));
    }
    return new EmployeeSummary(total, byDepartment);
  }
}
