package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.repository;

import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model.EmployeeJpaEntity;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeJpaRepository
    extends JpaRepository<EmployeeJpaEntity, Long>, JpaSpecificationExecutor<EmployeeJpaEntity> {

  Optional<EmployeeJpaEntity> findByIdAndDeletedAtIsNull(Long id);

  long countByDeletedAtIsNull();

  long countByDeletedAtIsNullAndStatus(EmployeeStatus status);

  @Query(
      """
      select coalesce(e.department, ''), count(e)
      from EmployeeJpaEntity e
      where e.deletedAt is null
      group by coalesce(e.department, '')
      order by coalesce(e.department, '')
      """)
  List<Object[]> countByDepartmentRows();
}
