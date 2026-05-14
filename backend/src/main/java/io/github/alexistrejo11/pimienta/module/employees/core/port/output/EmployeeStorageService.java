package io.github.alexistrejo11.pimienta.module.employees.core.port.output;

import org.springframework.web.multipart.MultipartFile;

public interface EmployeeStorageService {
  /** Persists under {@code …/employee-photos/}; returns the S3 object key (store in DB for delete/presign). */
  String uploadEmployeePhoto(MultipartFile file, String displayNameSegment);

  /** Persists under {@code …/attendance-evidence/}; {@code evidenceKind} e.g. {@code check-in}. */
  String uploadAttendanceEvidencePhoto(
      MultipartFile file, long employeeId, String displayNameSegment, String evidenceKind);

  void delete(String fileUrlOrKey);

  String generatePresignedUrl(String key);
}
