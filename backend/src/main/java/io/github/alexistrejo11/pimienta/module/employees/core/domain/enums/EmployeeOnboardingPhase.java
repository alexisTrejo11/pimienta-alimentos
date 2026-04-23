package io.github.alexistrejo11.pimienta.module.employees.core.domain.enums;

/** Where a person is in the hiring pipeline before an employment contract is filed. */
public enum EmployeeOnboardingPhase {
  DRAFT,
  PENDING_CONTRACT;

  public EmployeeStatus toEmploymentStatus() {
    return switch (this) {
      case DRAFT -> EmployeeStatus.DRAFT;
      case PENDING_CONTRACT -> EmployeeStatus.PENDING_CONTRACT;
    };
  }
}
