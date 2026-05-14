package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.employees.core.port.output.EmployeeStorageService;
import org.springframework.stereotype.Component;

/**
 * Persists S3 object keys on aggregates; exposes time-limited HTTPS URLs on API responses. Legacy
 * rows may still store a full {@code http(s)} URL. The same presentation applies to any stored key
 * (e.g. employee profile photo or attendance evidence).
 */
@Component
public class EmployeePhotoUrlPresenter {

  private final EmployeeStorageService employeeStorageService;

  public EmployeePhotoUrlPresenter(EmployeeStorageService employeeStorageService) {
    this.employeeStorageService = employeeStorageService;
  }

  public String present(String stored) {
    if (stored == null || stored.isBlank()) {
      return stored == null ? "" : stored;
    }
    String t = stored.strip();
    if (t.startsWith("http://") || t.startsWith("https://")) {
      return t;
    }
    return employeeStorageService.generatePresignedUrl(t);
  }
}
