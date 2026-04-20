package io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.persistence.repository;

import io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.persistence.model.PayrollPeriodJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollPeriodJpaRepository extends JpaRepository<PayrollPeriodJpaEntity, Long> {

  Page<PayrollPeriodJpaEntity> findAllByDeletedAtIsNull(Pageable pageable);
}
