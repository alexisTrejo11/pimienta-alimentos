package io.github.alexistrejo11.pimienta.module.employees.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.ConflictException;
import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import java.time.LocalDate;
import java.util.Map;

public class AttendanceAlreadyOpenException extends ConflictException {

  public AttendanceAlreadyOpenException(Long employeeId, LocalDate workDate) {
    super(
        ErrorCode.ATTENDANCE_ALREADY_OPEN,
        "This employee already has an open attendance for that work date.",
        Map.of("employeeId", employeeId, "workDate", workDate.toString()),
        "Open attendance exists: employeeId=" + employeeId + " workDate=" + workDate);
  }
}
