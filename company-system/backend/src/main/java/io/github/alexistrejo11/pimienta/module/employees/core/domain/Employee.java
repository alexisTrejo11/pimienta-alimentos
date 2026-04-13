package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Employee {
  private Long id;
  private String name;
  private String email;
  private String phone;
  private String address;
  private String position;
  private EmployeeStatus status;
  private BigDecimal salaryPerWeek;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;
  private Long version;

  public enum EmployeeStatus {
    ACTIVE,
    SICK,
    ON_VACATION,
    ON_LEAVE,
    TERMINATED,
    FIRED,
    RESIGNED
  }

  public Employee() {
    this.id = 0L;
    this.name = "";
    this.email = "";
    this.phone = "";
    this.address = "";
    this.position = "";
    this.salaryPerWeek = BigDecimal.ZERO;
    this.status = EmployeeStatus.ACTIVE;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.deletedAt = null;
    this.version = 0L;
  }

  public BigDecimal getSalaryPer15Days() {
    return salaryPerWeek.divide(new BigDecimal(2));
  }

  public BigDecimal getSalaryPerMonth() {
    return salaryPerWeek.multiply(new BigDecimal(4));
  }

  public static Employee hire(String name, String email, String phone, String address, String position,
      BigDecimal salaryPerWeek) {
    var now = LocalDateTime.now();
    var employee = new Employee();
    employee.name = name;
    employee.email = email;
    employee.phone = phone;
    employee.address = address;
    employee.position = position;
    employee.salaryPerWeek = salaryPerWeek;
    employee.status = EmployeeStatus.ACTIVE;
    employee.createdAt = now;
    employee.updatedAt = now;
    return employee;
  }

  public static Employee update(Long id, String name, String email, String phone, String address, String position,
      BigDecimal salaryPerWeek) {
    var now = LocalDateTime.now();
    var employee = new Employee();
    employee.id = id;
    employee.name = name;
    employee.email = email;
    employee.phone = phone;
    employee.address = address;
    employee.position = position;
    employee.salaryPerWeek = salaryPerWeek;
    employee.status = EmployeeStatus.ACTIVE;
    employee.updatedAt = now;
    return employee;
  }

  public void terminate() {
    this.status = EmployeeStatus.TERMINATED;
    this.updatedAt = LocalDateTime.now();
  }

  public void fire() {
    this.status = EmployeeStatus.FIRED;
    this.updatedAt = LocalDateTime.now();
  }

  public void resign() {
    this.status = EmployeeStatus.RESIGNED;
    this.updatedAt = LocalDateTime.now();
  }

  public void sick() {
    this.status = EmployeeStatus.SICK;
    this.updatedAt = LocalDateTime.now();
  }

  public void onVacation() {
    this.status = EmployeeStatus.ON_VACATION;
    this.updatedAt = LocalDateTime.now();
  }

  public void onLeave() {
    this.status = EmployeeStatus.ON_LEAVE;
    this.updatedAt = LocalDateTime.now();
  }

  public void activate() {
    this.status = EmployeeStatus.ACTIVE;
    this.updatedAt = LocalDateTime.now();
  }

  // Getters
  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public String getAddress() {
    return address;
  }

  public String getPosition() {
    return position;
  }

  public BigDecimal getSalaryPerWeek() {
    return salaryPerWeek;
  }

  public EmployeeStatus getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public Long getVersion() {
    return version;
  }
}
