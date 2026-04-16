package io.github.alexistrejo11.pimienta.module.account.auth.core.application;

import org.springframework.stereotype.Service;

import io.github.alexistrejo11.pimienta.module.account.auth.core.domain.entity.UserManagerDashboard;
import io.github.alexistrejo11.pimienta.module.account.auth.core.port.input.ProfileUseCases;
import io.github.alexistrejo11.pimienta.module.account.user.core.application.command.UpdateProfileCommand;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.entities.User;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.exceptions.UserNotFoundException;
import io.github.alexistrejo11.pimienta.module.account.user.core.port.output.UserRepository;
import io.github.alexistrejo11.pimienta.module.crm.core.port.output.ProjectRepository;
import io.github.alexistrejo11.pimienta.module.employees.core.port.EmployeeRepository;
import io.github.alexistrejo11.pimienta.module.headquarter.core.port.HeadquarterRepository;
import io.github.alexistrejo11.pimienta.module.task.core.port.TaskRepository;

@Service
public class ProfileUseCasesImpl implements ProfileUseCases {
  private final UserRepository userRepository;
  private final EmployeeRepository employeeRepository;
  private final ProjectRepository projectRepository;
  private final HeadquarterRepository headquarterRepository;
  private final TaskRepository taskRepository;

  public ProfileUseCasesImpl(
      UserRepository userRepository,
      EmployeeRepository employeeRepository,
      ProjectRepository projectRepository,
      HeadquarterRepository headquarterRepository,
      TaskRepository taskRepository) {
    this.userRepository = userRepository;
    this.employeeRepository = employeeRepository;
    this.projectRepository = projectRepository;
    this.headquarterRepository = headquarterRepository;
    this.taskRepository = taskRepository;
  }

  @Override
  public UserManagerDashboard getDashboard(long userId) {
    userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    int totalActiveEmployees = safeInt(employeeRepository.countActive());
    int totalActiveProjects = safeInt(projectRepository.countActive());
    int totalActiveHeadquarters = safeInt(headquarterRepository.countActive());
    int totalActivePersonalTasks = safeInt(taskRepository.countOpenPersonalTasks());
    int totalPendingPersonalTasks = safeInt(taskRepository.countPendingPersonalTasks());
    int totalActiveEmployeesTasks = safeInt(taskRepository.countOpenWorkTasks());
    int totalEmployeePending = safeInt(taskRepository.countPendingWorkTasks());
    return new UserManagerDashboard(
        totalActiveEmployees,
        totalActiveProjects,
        totalActiveHeadquarters,
        totalActivePersonalTasks,
        totalPendingPersonalTasks,
        totalActiveEmployeesTasks,
        totalEmployeePending);
  }

  private static int safeInt(long value) {
    if (value > Integer.MAX_VALUE) {
      return Integer.MAX_VALUE;
    }
    return (int) value;
  }

  @Override
  public User getProfile(long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    return user;
  }

  @Override
  public User updateProfile(long userId, UpdateProfileCommand command) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    user.updateProfile(
        command.firstName(),
        command.lastName(),
        command.gender(),
        command.phone(),
        command.dateOfBirth());
    return userRepository.save(user);
  }
}