package io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.persistence.repository;

import io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.persistence.model.PayrollDebtJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PayrollDebtJpaRepository extends JpaRepository<PayrollDebtJpaEntity, Long> {

  Page<PayrollDebtJpaEntity> findAllByDeletedAtIsNullOrderByCreatedAtDesc(Pageable pageable);

  @Query("""
      select coalesce(sum(d.amountOwed), 0)
      from PayrollDebtJpaEntity d
      where d.deletedAt is null and d.settled = false
      """)
  java.math.BigDecimal summarizeOpenDebt();
}
