package io.github.alexistrejo11.pimienta.module.payroll.core.port.output;

import io.github.alexistrejo11.pimienta.module.payroll.core.domain.enums.PayrollAdjustmentType;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollDebt;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollPayment;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollPeriod;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollRecord;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollSummary;
import io.github.alexistrejo11.pimienta.module.payroll.core.application.query.PayrollSummaryQuery;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayrollRepository {

  Page<PayrollPayment> findPayments(Long employeeId, Pageable pageable);

  Page<PayrollPeriod> findPeriods(Pageable pageable);

  Page<PayrollRecord> findRecords(Long employeeId, Long periodId, Pageable pageable);

  Page<PayrollDebt> findDebts(Pageable pageable);

  Optional<PayrollRecord> findRecordById(Long id);

  Optional<PayrollPayment> findLatestPaymentByRecordId(Long recordId);

  PayrollPeriod savePeriod(PayrollPeriod period);

  PayrollRecord saveRecord(PayrollRecord record);

  PayrollPayment savePayment(PayrollPayment payment);

  PayrollDebt saveDebt(PayrollDebt debt);

  void appendAdjustment(Long recordId, PayrollAdjustmentType type, java.math.BigDecimal amount, String reason);

  PayrollSummary summarize(PayrollSummaryQuery query);
}
