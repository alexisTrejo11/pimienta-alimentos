package io.github.alexistrejo11.pimienta.module.employees.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.ConflictException;
import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import java.time.LocalDate;
import java.util.Map;

public class AttendanceNotOpenForCheckoutException extends ConflictException {

  public AttendanceNotOpenForCheckoutException(Long employeeId, LocalDate workDate) {
    super(
        ErrorCode.ATTENDANCE_NOT_OPEN_FOR_CHECKOUT,
        "No open check-in was found for this employee on the given work date.",
        Map.of("employeeId", employeeId, "workDate", workDate.toString()),
        "No open attendance for checkout: employeeId=" + employeeId + " workDate=" + workDate);
  }
}
