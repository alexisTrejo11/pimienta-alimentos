package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.repository;

import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model.AttendanceJpaEntity;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.AttendanceStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttendanceJpaRepository
    extends JpaRepository<AttendanceJpaEntity, Long>, JpaSpecificationExecutor<AttendanceJpaEntity> {

  Optional<AttendanceJpaEntity> findByIdAndDeletedAtIsNull(Long id);

  Optional<AttendanceJpaEntity> findByEmployeeIdAndWorkDateAndDeletedAtIsNullAndCheckOutTimeIsNull(
      Long employeeId, LocalDate workDate);

  List<AttendanceJpaEntity> findAllByHeadquarterIdAndWorkDateAndDeletedAtIsNullOrderByCheckInTimeAsc(
      Long headquarterId, LocalDate workDate);

  List<AttendanceJpaEntity> findAllByDeletedAtIsNullAndCheckOutTimeIsNullAndStatus(
      AttendanceStatus status);
}
