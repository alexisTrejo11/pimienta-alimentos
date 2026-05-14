package io.github.alexistrejo11.pimienta.module.employees.core.application.command;

import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

public record StartWorkdayCommand(
    long employeeId, long headquarterId, LocalDate workDate, MultipartFile checkInEvidencePhoto) {

  public StartWorkdayCommand {
    if (workDate == null) {
      throw new IllegalArgumentException("workDate must not be null");
    }
  }
}
