package io.github.alexistrejo11.pimienta.module.payroll.core.port.input;

import io.github.alexistrejo11.pimienta.module.payroll.core.application.command.RegisterPayrollAdjustmentCommand;
import io.github.alexistrejo11.pimienta.module.payroll.core.application.command.RegisterPayrollPaymentCommand;
import io.github.alexistrejo11.pimienta.module.payroll.core.application.command.RegisterPayrollPeriodCommand;
import io.github.alexistrejo11.pimienta.module.payroll.core.application.command.RegisterPayrollRecordCommand;
import io.github.alexistrejo11.pimienta.module.payroll.core.application.query.PayrollSummaryQuery;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollDebt;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollPayment;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollPeriod;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollRecord;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayrollManagementUseCases {

  Page<PayrollPayment> listPaymentsByEmployee(Long employeeId, Pageable pageable);

  Page<PayrollPeriod> listPeriods(Pageable pageable);

  Page<PayrollRecord> listRecords(Long employeeId, Long periodId, Pageable pageable);

  PayrollSummary summary(PayrollSummaryQuery query);

  Page<PayrollDebt> listDebts(Pageable pageable);

  PayrollPeriod registerPeriod(RegisterPayrollPeriodCommand command);

  PayrollRecord registerRecord(RegisterPayrollRecordCommand command);

  PayrollPayment registerPayment(RegisterPayrollPaymentCommand command);

  PayrollPayment registerAdjustment(RegisterPayrollAdjustmentCommand command);
}
