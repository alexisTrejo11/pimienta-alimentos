package io.github.alexistrejo11.pimienta.module.employees.core.domain.enums;

public enum EmployeeStatus {
  /** Registro iniciado; datos o documentación incompletos. */
  DRAFT,
  /** Listo para contrato; esperando contrato firmado/archivado. */
  PENDING_CONTRACT,
  ACTIVE,
  SICK,
  ON_VACATION,
  ON_LEAVE,
  TERMINATED,
  FIRED,
  RESIGNED,
  UNDEFINED
}
