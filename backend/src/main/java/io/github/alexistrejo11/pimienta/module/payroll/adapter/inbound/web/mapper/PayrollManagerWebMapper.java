package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.mapper;

import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.request.RegisterPayrollAdjustmentRequest;
import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.request.RegisterPayrollPaymentRequest;
import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.request.RegisterPayrollPeriodRequest;
import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.request.RegisterPayrollRecordRequest;
import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.response.PayrollDebtResponse;
import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.response.PayrollPaymentResponse;
import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.response.PayrollPeriodResponse;
import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.response.PayrollRecordResponse;
import io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.response.PayrollSummaryResponse;
import io.github.alexistrejo11.pimienta.module.payroll.core.application.command.RegisterPayrollAdjustmentCommand;
import io.github.alexistrejo11.pimienta.module.payroll.core.application.command.RegisterPayrollPaymentCommand;
import io.github.alexistrejo11.pimienta.module.payroll.core.application.command.RegisterPayrollPeriodCommand;
import io.github.alexistrejo11.pimienta.module.payroll.core.application.command.RegisterPayrollRecordCommand;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollDebt;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollPayment;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollPeriod;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollRecord;
import io.github.alexistrejo11.pimienta.module.payroll.core.domain.PayrollSummary;
import java.time.ZoneOffset;

public final class PayrollManagerWebMapper {

  private PayrollManagerWebMapper() {}

  public static RegisterPayrollPaymentCommand toCommand(RegisterPayrollPaymentRequest request) {
    return RegisterPayrollPaymentCommand.builder()
        .employeeId(request.employeeId())
        .frequency(request.frequency())
        .workedDaysStart(request.workedDaysStart())
        .workedDaysEnd(request.workedDaysEnd())
        .grossAmount(request.grossAmount())
        .netAmount(request.netAmount())
        .destinationAccount(request.destinationAccount())
        .transactionId(request.transactionId())
        .build();
  }

  public static RegisterPayrollAdjustmentCommand toCommand(
      Long recordId, RegisterPayrollAdjustmentRequest request) {
    return new RegisterPayrollAdjustmentCommand(recordId, request.type(), request.amount(), request.reason());
  }

  public static RegisterPayrollPeriodCommand toCommand(RegisterPayrollPeriodRequest request) {
    return new RegisterPayrollPeriodCommand(request.frequency(), request.startDate(), request.endDate());
  }

  public static RegisterPayrollRecordCommand toCommand(RegisterPayrollRecordRequest request) {
    return RegisterPayrollRecordCommand.builder()
        .employeeId(request.employeeId())
        .periodId(request.periodId())
        .workedDaysStart(request.workedDaysStart())
        .workedDaysEnd(request.workedDaysEnd())
        .grossAmount(request.grossAmount())
        .build();
  }

  public static PayrollPaymentResponse toPaymentResponse(PayrollPayment payment) {
    return PayrollPaymentResponse.builder()
        .id(payment.getId())
        .employeeId(payment.getEmployeeId())
        .frequency(payment.getFrequency())
        .workedDaysStart(payment.getWorkedDaysStart())
        .workedDaysEnd(payment.getWorkedDaysEnd())
        .grossAmount(payment.getGrossAmount())
        .netAmount(payment.getNetAmount())
        .destinationAccount(payment.getDestinationAccount())
        .transactionId(payment.getTransactionId())
        .status(payment.getStatus())
        .pendingAmount(payment.getPendingAmount())
        .createdAt(payment.getCreatedAt() != null ? payment.getCreatedAt().toInstant(ZoneOffset.UTC) : null)
        .build();
  }

  public static PayrollSummaryResponse toSummaryResponse(PayrollSummary summary) {
    return PayrollSummaryResponse.builder()
        .from(summary.getFrom())
        .to(summary.getTo())
        .employeesPaid(summary.getEmployeesPaid())
        .totalGross(summary.getTotalGross())
        .totalNet(summary.getTotalNet())
        .totalDiscounts(summary.getTotalDiscounts())
        .totalBonuses(summary.getTotalBonuses())
        .totalDebt(summary.getTotalDebt())
        .build();
  }

  public static PayrollDebtResponse toDebtResponse(PayrollDebt debt) {
    return PayrollDebtResponse.builder()
        .id(debt.getId())
        .employeeId(debt.getEmployeeId())
        .payrollRecordId(debt.getPayrollRecordId())
        .amountOwed(debt.getAmountOwed())
        .reason(debt.getReason())
        .settled(debt.isSettled())
        .createdAt(debt.getCreatedAt() != null ? debt.getCreatedAt().toInstant(ZoneOffset.UTC) : null)
        .settledAt(debt.getSettledAt() != null ? debt.getSettledAt().toInstant(ZoneOffset.UTC) : null)
        .build();
  }

  public static PayrollPeriodResponse toPeriodResponse(PayrollPeriod period) {
    return PayrollPeriodResponse.builder()
        .id(period.getId())
        .frequency(period.getFrequency())
        .startDate(period.getStartDate())
        .endDate(period.getEndDate())
        .status(period.getStatus())
        .build();
  }

  public static PayrollRecordResponse toRecordResponse(PayrollRecord record) {
    return PayrollRecordResponse.builder()
        .id(record.getId())
        .employeeId(record.getEmployeeId())
        .periodId(record.getPeriodId())
        .workedDaysStart(record.getWorkedDaysStart())
        .workedDaysEnd(record.getWorkedDaysEnd())
        .grossAmount(record.getGrossAmount())
        .totalDiscounts(record.getTotalDiscounts())
        .totalBonuses(record.getTotalBonuses())
        .netAmount(record.getNetAmount())
        .status(record.getStatus())
        .build();
  }
}
