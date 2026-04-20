package io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.persistence.repository;

import io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.persistence.model.PayrollRecordJpaEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PayrollRecordJpaRepository extends JpaRepository<PayrollRecordJpaEntity, Long> {

  @Query("""
      select r
      from PayrollRecordJpaEntity r
      where r.deletedAt is null
        and (:employeeId is null or r.employeeId = :employeeId)
        and (:periodId is null or r.periodId = :periodId)
      order by r.createdAt desc
      """)
  Page<PayrollRecordJpaEntity> search(
      @Param("employeeId") Long employeeId,
      @Param("periodId") Long periodId,
      Pageable pageable);

  Optional<PayrollRecordJpaEntity> findByIdAndDeletedAtIsNull(Long id);

  @Query("""
      select coalesce(sum(r.totalDiscounts), 0), coalesce(sum(r.totalBonuses), 0)
      from PayrollRecordJpaEntity r
      where r.deletedAt is null
        and (:from is null or r.workedDaysStart >= :from)
        and (:to is null or r.workedDaysEnd <= :to)
      """)
  Object[] summarizeAdjustments(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
