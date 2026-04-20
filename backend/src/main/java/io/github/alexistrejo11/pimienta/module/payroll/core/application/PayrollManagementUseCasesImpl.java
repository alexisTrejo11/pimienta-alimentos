package io.github.alexistrejo11.pimienta.module.payroll.core.application;

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
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.enums.PayrollAdjustmentType;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.enums.PayrollRecordStatus;
import io.github.alexistrejo11.pimienta.module.payroll.core.port.input.PayrollManagementUseCases;
import io.github.alexistrejo11.pimienta.module.payroll.core.port.output.PayrollRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayrollManagementUseCasesImpl implements PayrollManagementUseCases {

  private static final BigDecimal ZERO = BigDecimal.ZERO;
  private final PayrollRepository payrollRepository;

  public PayrollManagementUseCasesImpl(PayrollRepository payrollRepository) {
    this.payrollRepository = payrollRepository;
  }

  @Override
  public Page<PayrollPayment> listPaymentsByEmployee(Long employeeId, Pageable pageable) {
    Objects.requireNonNull(pageable, "pageable");
    return payrollRepository.findPayments(employeeId, pageable);
  }

  @Override
  public Page<PayrollPeriod> listPeriods(Pageable pageable) {
    Objects.requireNonNull(pageable, "pageable");
    return payrollRepository.findPeriods(pageable);
  }

  @Override
  public Page<PayrollRecord> listRecords(Long employeeId, Long periodId, Pageable pageable) {
    Objects.requireNonNull(pageable, "pageable");
    return payrollRepository.findRecords(employeeId, periodId, pageable);
  }

  @Override
  public PayrollSummary summary(PayrollSummaryQuery query) {
    Objects.requireNonNull(query, "query");
    return payrollRepository.summarize(query);
  }

  @Override
  public Page<PayrollDebt> listDebts(Pageable pageable) {
    Objects.requireNonNull(pageable, "pageable");
    return payrollRepository.findDebts(pageable);
  }

  @Override
  @Transactional
  public PayrollPeriod registerPeriod(RegisterPayrollPeriodCommand command) {
    Objects.requireNonNull(command, "command");
    PayrollPeriod period = PayrollPeriod.builder()
        .withFrequency(command.frequency())
        .withStartDate(command.startDate())
        .withEndDate(command.endDate())
        .withStatus("OPEN")
        .register();
    return payrollRepository.savePeriod(period);
  }

  @Override
  @Transactional
  public PayrollRecord registerRecord(RegisterPayrollRecordCommand command) {
    Objects.requireNonNull(command, "command");
    BigDecimal gross = nz(command.grossAmount());
    PayrollRecord record = PayrollRecord.builder()
        .withEmployeeId(command.employeeId())
        .withPeriodId(command.periodId())
        .withWorkedDaysStart(command.workedDaysStart())
        .withWorkedDaysEnd(command.workedDaysEnd())
        .withGrossAmount(gross)
        .withTotalDiscounts(ZERO)
        .withTotalBonuses(ZERO)
        .withNetAmount(gross)
        .withStatus(PayrollRecordStatus.PENDING)
        .register();
    return payrollRepository.saveRecord(record);
  }

  @Override
  @Transactional
  public PayrollPayment registerPayment(RegisterPayrollPaymentCommand command) {
    Objects.requireNonNull(command, "command");
    BigDecimal gross = nz(command.grossAmount());
    BigDecimal net = nz(command.netAmount());
    BigDecimal pendingAmount = gross.subtract(net).max(ZERO);
    PayrollRecordStatus status = pendingAmount.compareTo(ZERO) > 0 ? PayrollRecordStatus.PARTIAL : PayrollRecordStatus.PAID;

    PayrollPayment payment =
        PayrollPayment.builder()
            .withEmployeeId(command.employeeId())
            .withFrequency(command.frequency())
            .withWorkedDaysStart(command.workedDaysStart())
            .withWorkedDaysEnd(command.workedDaysEnd())
            .withGrossAmount(gross)
            .withNetAmount(net)
            .withDestinationAccount(command.destinationAccount())
            .withTransactionId(command.transactionId())
            .withStatus(status)
            .withPendingAmount(pendingAmount)
            .withCreatedAt(LocalDateTime.now())
            .register();

    PayrollPayment saved = payrollRepository.savePayment(payment);
    if (pendingAmount.compareTo(ZERO) > 0) {
      payrollRepository.saveDebt(
          PayrollDebt.builder()
              .withEmployeeId(saved.getEmployeeId())
              .withPayrollRecordId(saved.getPayrollRecordId())
              .withAmountOwed(pendingAmount)
              .withReason("Saldo pendiente de pago de nomina")
              .withSettled(false)
              .withCreatedAt(LocalDateTime.now())
              .register());
    }
    return saved;
  }

  @Override
  @Transactional
  public PayrollPayment registerAdjustment(RegisterPayrollAdjustmentCommand command) {
    Objects.requireNonNull(command, "command");
    PayrollRecord record =
        payrollRepository
            .findRecordById(command.payrollRecordId())
            .orElseThrow(() -> new IllegalArgumentException("Payroll record not found: " + command.payrollRecordId()));

    BigDecimal amount = nz(command.amount());
    BigDecimal totalDiscounts = record.getTotalDiscounts();
    BigDecimal totalBonuses = record.getTotalBonuses();
    BigDecimal netAmount = record.getNetAmount();

    if (command.type() == PayrollAdjustmentType.DISCOUNT) {
      totalDiscounts = nz(totalDiscounts).add(amount);
      netAmount = nz(netAmount).subtract(amount);
    } else {
      totalBonuses = nz(totalBonuses).add(amount);
      netAmount = nz(netAmount).add(amount);
    }

    PayrollRecordStatus status = netAmount.compareTo(ZERO) > 0 ? PayrollRecordStatus.PENDING : PayrollRecordStatus.DEFERRED;
    PayrollRecord updated = PayrollRecord.builder()
        .withId(record.getId())
        .withEmployeeId(record.getEmployeeId())
        .withPeriodId(record.getPeriodId())
        .withWorkedDaysStart(record.getWorkedDaysStart())
        .withWorkedDaysEnd(record.getWorkedDaysEnd())
        .withGrossAmount(nz(record.getGrossAmount()))
        .withTotalDiscounts(totalDiscounts)
        .withTotalBonuses(totalBonuses)
        .withNetAmount(netAmount)
        .withStatus(status)
        .withCreatedAt(record.getCreatedAt())
        .withUpdatedAt(LocalDateTime.now())
        .withDeletedAt(record.getDeletedAt())
        .withVersion(record.getVersion())
        .reconstruct();
    payrollRepository.saveRecord(updated);
    payrollRepository.appendAdjustment(record.getId(), command.type(), amount, command.reason());

    return payrollRepository
        .findLatestPaymentByRecordId(record.getId())
        .orElse(
            PayrollPayment.builder()
                .withEmployeeId(updated.getEmployeeId())
                .withPayrollRecordId(updated.getId())
                .withFrequency(null)
                .withWorkedDaysStart(updated.getWorkedDaysStart())
                .withWorkedDaysEnd(updated.getWorkedDaysEnd())
                .withGrossAmount(updated.getGrossAmount())
                .withNetAmount(updated.getNetAmount())
                .withDestinationAccount("")
                .withTransactionId("N/A")
                .withStatus(updated.getStatus())
                .withPendingAmount(updated.getNetAmount().max(ZERO))
                .withCreatedAt(LocalDateTime.now())
                .register());
  }

  private static BigDecimal nz(BigDecimal value) {
    return value != null ? value : ZERO;
  }
}
