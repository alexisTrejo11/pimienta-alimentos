package io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.persistence.repository;

import io.github.alexistrejo11.pimienta.module.payroll.adapter.outbound.persistence.model.PayrollAdjustmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollAdjustmentJpaRepository extends JpaRepository<PayrollAdjustmentJpaEntity, Long> {
}
