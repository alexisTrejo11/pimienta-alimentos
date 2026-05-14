package io.github.alexistrejo11.pimienta.module.employees.core.application.storage;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.Employee;

/** Builds readable filename stems for S3 keys (employee photos and attendance evidence). */
public final class EmployeeStorageNaming {

  private EmployeeStorageNaming() {}

  public static String displayNameSegment(String firstName, String lastName) {
    String a = firstName != null ? firstName.strip() : "";
    String b = lastName != null ? lastName.strip() : "";
    if (a.isEmpty() && b.isEmpty()) {
      return "employee";
    }
    return (a + "_" + b).strip();
  }

  public static String displayNameSegment(Employee employee) {
    if (employee == null) {
      return "employee";
    }
    var p = employee.getPersonal();
    return displayNameSegment(p.firstName(), p.lastName());
  }
}
