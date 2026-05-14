package io.github.alexistrejo11.pimienta.module.employees.core.application.command;

import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

public record EndWorkdayCommand(long employeeId, LocalDate workDate, MultipartFile checkOutEvidencePhoto) {

  public EndWorkdayCommand {
    if (workDate == null) {
      throw new IllegalArgumentException("workDate must not be null");
    }
  }
}
