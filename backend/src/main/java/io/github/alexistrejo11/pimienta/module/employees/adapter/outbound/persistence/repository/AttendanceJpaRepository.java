package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.repository;

import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model.AttendanceJpaEntity;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.AttendanceStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttendanceJpaRepository
    extends JpaRepository<AttendanceJpaEntity, Long>, JpaSpecificationExecutor<AttendanceJpaEntity> {

  Optional<AttendanceJpaEntity> findByIdAndDeletedAtIsNull(Long id);

  Optional<AttendanceJpaEntity> findByEmployeeIdAndWorkDateAndDeletedAtIsNullAndCheckOutTimeIsNull(
      Long employeeId, LocalDate workDate);

  Page<AttendanceJpaEntity> findAllByWorkDateAndDeletedAtIsNullOrderByCheckInTimeAsc(
      LocalDate workDate, Pageable pageable);

  Page<AttendanceJpaEntity> findAllByHeadquarterIdAndWorkDateAndDeletedAtIsNullOrderByCheckInTimeAsc(
      Long headquarterId, LocalDate workDate, Pageable pageable);

  List<AttendanceJpaEntity> findAllByDeletedAtIsNullAndCheckOutTimeIsNullAndStatus(
      AttendanceStatus status);
}
