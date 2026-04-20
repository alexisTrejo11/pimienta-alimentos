package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.request;

import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(name = "PayrollBulkScopeRequest", description = "Scope filters for payroll bulk import/export.")
public class PayrollBulkScopeRequest extends PageableRequest {

  @Schema(description = "Filter by employee id.", example = "101")
  private Long employeeId;

  @Schema(description = "Filter by payroll period id.", example = "22")
  private Long periodId;

  @Schema(
      description = "Worked range start date (inclusive).",
      type = "string",
      format = "date",
      example = "2026-04-01")
  private LocalDate from;

  @Schema(
      description = "Worked range end date (inclusive).",
      type = "string",
      format = "date",
      example = "2026-04-30")
  private LocalDate to;

  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public Long getPeriodId() {
    return periodId;
  }

  public void setPeriodId(Long periodId) {
    this.periodId = periodId;
  }

  public LocalDate getFrom() {
    return from;
  }

  public void setFrom(LocalDate from) {
    this.from = from;
  }

  public LocalDate getTo() {
    return to;
  }

  public void setTo(LocalDate to) {
    this.to = to;
  }
}
