package io.github.alexistrejo11.pimienta.module.payroll.adapter.inbound.web.dto.request;

import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PayrollRecordSearchRequest", description = "Filters for searching payroll records.")
public class PayrollRecordSearchRequest extends PageableRequest {

  @Schema(description = "Filter by employee id.", example = "101")
  private Long employeeId;

  @Schema(description = "Filter by payroll period id.", example = "22")
  private Long periodId;

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
}
