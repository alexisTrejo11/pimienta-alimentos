package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.AttendanceStatus;
import io.github.alexistrejo11.pimienta.shared.jpa.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_attendances")
public class AttendanceJpaEntity extends BaseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "employee_id", nullable = false)
  private Long employeeId;

  @Column(name = "headquarter_id", nullable = false)
  private Long headquarterId;

  @Column(name = "work_date", nullable = false)
  private LocalDate workDate;

  @Column(name = "check_in_time")
  private LocalDateTime checkInTime;

  @Column(name = "check_out_time")
  private LocalDateTime checkOutTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 48)
  private AttendanceStatus status;

  @Column(name = "check_in_evidence_photo_url", length = 2000)
  private String checkInEvidencePhotoUrl;

  @Column(name = "check_out_evidence_photo_url", length = 2000)
  private String checkOutEvidencePhotoUrl;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Long employeeId) {
    this.employeeId = employeeId;
  }

  public Long getHeadquarterId() {
    return headquarterId;
  }

  public void setHeadquarterId(Long headquarterId) {
    this.headquarterId = headquarterId;
  }

  public LocalDate getWorkDate() {
    return workDate;
  }

  public void setWorkDate(LocalDate workDate) {
    this.workDate = workDate;
  }

  public LocalDateTime getCheckInTime() {
    return checkInTime;
  }

  public void setCheckInTime(LocalDateTime checkInTime) {
    this.checkInTime = checkInTime;
  }

  public LocalDateTime getCheckOutTime() {
    return checkOutTime;
  }

  public void setCheckOutTime(LocalDateTime checkOutTime) {
    this.checkOutTime = checkOutTime;
  }

  public AttendanceStatus getStatus() {
    return status;
  }

  public void setStatus(AttendanceStatus status) {
    this.status = status;
  }

  public String getCheckInEvidencePhotoUrl() {
    return checkInEvidencePhotoUrl;
  }

  public void setCheckInEvidencePhotoUrl(String checkInEvidencePhotoUrl) {
    this.checkInEvidencePhotoUrl = checkInEvidencePhotoUrl;
  }

  public String getCheckOutEvidencePhotoUrl() {
    return checkOutEvidencePhotoUrl;
  }

  public void setCheckOutEvidencePhotoUrl(String checkOutEvidencePhotoUrl) {
    this.checkOutEvidencePhotoUrl = checkOutEvidencePhotoUrl;
  }
}
