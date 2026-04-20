package io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.persistence.repository;

import io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.persistence.model.PayrollPaymentJpaEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PayrollPaymentJpaRepository extends JpaRepository<PayrollPaymentJpaEntity, Long> {

  @Query("""
      select p
      from PayrollPaymentJpaEntity p
      where p.deletedAt is null
        and (:employeeId is null or p.employeeId = :employeeId)
      order by p.createdAt desc
      """)
  Page<PayrollPaymentJpaEntity> searchByEmployee(@Param("employeeId") Long employeeId, Pageable pageable);

  Optional<PayrollPaymentJpaEntity> findTopByPayrollRecordIdAndDeletedAtIsNullOrderByCreatedAtDesc(
      Long payrollRecordId);

  @Query("""
      select count(distinct p.employeeId), coalesce(sum(p.grossAmount), 0), coalesce(sum(p.netAmount), 0)
      from PayrollPaymentJpaEntity p
      where p.deletedAt is null
        and (:from is null or p.workedDaysStart >= :from)
        and (:to is null or p.workedDaysEnd <= :to)
      """)
  Object[] summarizePayments(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
