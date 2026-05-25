package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.repository;

import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.AttendancePersistenceMapper;
import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model.AttendanceJpaEntity;
import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model.AttendanceSpecifications;
import io.github.alexistrejo11.pimienta.module.employees.core.application.query.AttendanceSearchCriteria;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.AttendanceStatus;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.Attendance;
import io.github.alexistrejo11.pimienta.module.employees.core.port.output.AttendanceRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceRepositoryImpl implements AttendanceRepository {

  private final AttendanceJpaRepository jpaRepository;

  public AttendanceRepositoryImpl(AttendanceJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public Optional<Attendance> findById(long id) {
    return jpaRepository.findByIdAndDeletedAtIsNull(id).map(AttendancePersistenceMapper::toDomain);
  }

  @Override
  public Attendance save(Attendance attendance) {
    AttendanceJpaEntity entity = AttendancePersistenceMapper.toJpa(attendance);
    AttendanceJpaEntity saved = jpaRepository.save(entity);
    return AttendancePersistenceMapper.toDomain(saved);
  }

  @Override
  public Optional<Attendance> findOpenForEmployeeOnWorkDate(long employeeId, LocalDate workDate) {
    return jpaRepository
        .findByEmployeeIdAndWorkDateAndDeletedAtIsNullAndCheckOutTimeIsNull(employeeId, workDate)
        .map(AttendancePersistenceMapper::toDomain);
  }

  @Override
  public Page<Attendance> findPageForWorkDate(LocalDate workDate, Long headquarterId, Pageable pageable) {
    Page<AttendanceJpaEntity> page =
        headquarterId == null
            ? jpaRepository.findAllByWorkDateAndDeletedAtIsNullOrderByCheckInTimeAsc(workDate, pageable)
            : jpaRepository.findAllByHeadquarterIdAndWorkDateAndDeletedAtIsNullOrderByCheckInTimeAsc(
                headquarterId, workDate, pageable);
    return page.map(AttendancePersistenceMapper::toDomain);
  }

  @Override
  public List<Attendance> findAllOpenCheckedIn() {
    return jpaRepository.findAllByDeletedAtIsNullAndCheckOutTimeIsNullAndStatus(AttendanceStatus.CHECKED_IN).stream()
        .map(AttendancePersistenceMapper::toDomain)
        .toList();
  }

  @Override
  public Page<Attendance> search(AttendanceSearchCriteria criteria, Pageable pageable) {
    Specification<AttendanceJpaEntity> spec = AttendanceSpecifications.fromCriteria(criteria);
    return jpaRepository.findAll(spec, pageable).map(AttendancePersistenceMapper::toDomain);
  }
}
