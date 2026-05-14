package io.github.alexistrejo11.pimienta.module.employees.core.domain.exception;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class AttendanceNotFoundException extends ResourceNotFoundException {

  public AttendanceNotFoundException(Long id) {
    super(
        ErrorCode.ATTENDANCE_NOT_FOUND,
        "The requested attendance record was not found.",
        Map.of("attendanceId", id),
        "Attendance not found: id=" + id);
  }
}
