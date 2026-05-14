package io.github.alexistrejo11.pimienta.module.employees.core.port.input;

import io.github.alexistrejo11.pimienta.module.employees.core.application.command.EndWorkdayCommand;
import io.github.alexistrejo11.pimienta.module.employees.core.application.command.StartWorkdayCommand;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.Attendance;

public interface AttendanceTrackingUseCases {

  Attendance startWorkday(StartWorkdayCommand command);

  Attendance endWorkday(EndWorkdayCommand command);
}
