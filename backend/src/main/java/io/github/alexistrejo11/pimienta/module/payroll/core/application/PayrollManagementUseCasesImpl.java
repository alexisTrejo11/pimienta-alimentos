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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayrollManagementUseCasesImpl implements PayrollManagementUseCases {

  private static final Logger log = LoggerFactory.getLogger(PayrollManagementUseCasesImpl.class);

  private static final BigDecimal ZERO = BigDecimal.ZERO;
  private final PayrollRepository payrollRepository;

  public PayrollManagementUseCasesImpl(PayrollRepository payrollRepository) {
    this.payrollRepository = payrollRepository;
  }

  @Override
  public Page<PayrollPayment> listPaymentsByEmployee(Long employeeId, Pageable pageable) {
    Objects.requireNonNull(pageable, "pageable");

    log.debug(
        "list payroll payments by employee query start employeeId={} page={} size={}",
        employeeId,
        pageable.getPageNumber(),
        pageable.getPageSize());

    Page<PayrollPayment> page = payrollRepository.findPayments(employeeId, pageable);

    log.debug(
        "list payroll payments by employee query complete totalElements={} numberOfElements={}",
        page.getTotalElements(),
        page.getNumberOfElements());
    return page;
  }

  @Override
  public Page<PayrollPeriod> listPeriods(Pageable pageable) {
    Objects.requireNonNull(pageable, "pageable");

    log.debug(
        "list payroll periods query start page={} size={}",
        pageable.getPageNumber(),
        pageable.getPageSize());

    Page<PayrollPeriod> page = payrollRepository.findPeriods(pageable);

    log.debug(
        "list payroll periods query complete totalElements={} numberOfElements={}",
        page.getTotalElements(),
        page.getNumberOfElements());
    return page;
  }

  @Override
  public Page<PayrollRecord> listRecords(Long employeeId, Long periodId, Pageable pageable) {
    Objects.requireNonNull(pageable, "pageable");

    log.debug(
        "list payroll records query start employeeId={} periodId={} page={} size={}",
        employeeId,
        periodId,
        pageable.getPageNumber(),
        pageable.getPageSize());

    Page<PayrollRecord> page = payrollRepository.findRecords(employeeId, periodId, pageable);

    log.debug(
        "list payroll records query complete totalElements={} numberOfElements={}",
        page.getTotalElements(),
        page.getNumberOfElements());
    return page;
  }

  @Override
  public PayrollSummary summary(PayrollSummaryQuery query) {
    Objects.requireNonNull(query, "query");

    log.debug("payroll summary query start from={} to={}", query.from(), query.to());

    PayrollSummary s = payrollRepository.summarize(query);

    log.debug("payroll summary query complete");
    return s;
  }

  @Override
  public Page<PayrollDebt> listDebts(Pageable pageable) {
    Objects.requireNonNull(pageable, "pageable");

    log.debug(
        "list payroll debts query start page={} size={}",
        pageable.getPageNumber(),
        pageable.getPageSize());

    Page<PayrollDebt> page = payrollRepository.findDebts(pageable);

    log.debug(
        "list payroll debts query complete totalElements={} numberOfElements={}",
        page.getTotalElements(),
        page.getNumberOfElements());
    return page;
  }

  @Override
  @Transactional
  public PayrollPeriod registerPeriod(RegisterPayrollPeriodCommand command) {
    Objects.requireNonNull(command, "command");

    log.info(
        "register payroll period start frequency={} startDate={} endDate={}",
        command.frequency(),
        command.startDate(),
        command.endDate());

    PayrollPeriod period =
        PayrollPeriod.builder()
            .withFrequency(command.frequency())
            .withStartDate(command.startDate())
            .withEndDate(command.endDate())
            .withStatus("OPEN")
            .register();

    PayrollPeriod saved = payrollRepository.savePeriod(period);

    log.info("register payroll period complete periodId={}", saved.getId());
    return saved;
  }

  @Override
  @Transactional
  public PayrollRecord registerRecord(RegisterPayrollRecordCommand command) {
    Objects.requireNonNull(command, "command");

    log.info(
        "register payroll record start employeeId={} periodId={} workedDaysStart={} workedDaysEnd={}",
        command.employeeId(),
        command.periodId(),
        command.workedDaysStart(),
        command.workedDaysEnd());

    BigDecimal gross = nz(command.grossAmount());
    PayrollRecord record =
        PayrollRecord.builder()
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

    PayrollRecord saved = payrollRepository.saveRecord(record);

    log.info("register payroll record complete recordId={} employeeId={}", saved.getId(), saved.getEmployeeId());
    return saved;
  }

  @Override
  @Transactional
  public PayrollPayment registerPayment(RegisterPayrollPaymentCommand command) {
    Objects.requireNonNull(command, "command");

    log.info(
        "register payroll payment start employeeId={} frequency={} transactionIdLen={} destinationAccountLen={}",
        command.employeeId(),
        command.frequency(),
        command.transactionId() == null ? 0 : command.transactionId().length(),
        command.destinationAccount() == null ? 0 : command.destinationAccount().length());

    BigDecimal gross = nz(command.grossAmount());
    BigDecimal net = nz(command.netAmount());
    BigDecimal pendingAmount = gross.subtract(net).max(ZERO);
    PayrollRecordStatus status =
        pendingAmount.compareTo(ZERO) > 0 ? PayrollRecordStatus.PARTIAL : PayrollRecordStatus.PAID;

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

    log.info(
        "register payroll payment complete paymentId={} employeeId={} status={} hasPendingDebt={}",
        saved.getId(),
        saved.getEmployeeId(),
        saved.getStatus(),
        pendingAmount.compareTo(ZERO) > 0);
    return saved;
  }

  @Override
  @Transactional
  public PayrollPayment registerAdjustment(RegisterPayrollAdjustmentCommand command) {
    Objects.requireNonNull(command, "command");

    log.info(
        "register payroll adjustment start payrollRecordId={} type={} reasonLen={}",
        command.payrollRecordId(),
        command.type(),
        command.reason() == null ? 0 : command.reason().length());

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

    PayrollRecordStatus status =
        netAmount.compareTo(ZERO) > 0 ? PayrollRecordStatus.PENDING : PayrollRecordStatus.DEFERRED;
    PayrollRecord updated =
        PayrollRecord.builder()
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

    PayrollPayment result =
        payrollRepository
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

    log.info(
        "register payroll adjustment complete payrollRecordId={} paymentIdOrSynthetic={} employeeId={}",
        record.getId(),
        result.getId(),
        result.getEmployeeId());
    return result;
  }

  private static BigDecimal nz(BigDecimal value) {
    return value != null ? value : ZERO;
  }
}
