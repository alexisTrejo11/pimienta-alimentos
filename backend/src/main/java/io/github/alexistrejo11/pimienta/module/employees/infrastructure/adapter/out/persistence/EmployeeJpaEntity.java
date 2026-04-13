package io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.out.persistence;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.ContractType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatus;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.ImssSalaryType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.ImssWorkerType;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.WorkShift;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class EmployeeJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "full_name", nullable = false, length = 200)
  private String fullName;

  @Column(nullable = false, length = 320)
  private String email;

  @Column(nullable = false, length = 40)
  private String phone;

  @Column(nullable = false, length = 500)
  private String address;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(nullable = false, length = 80)
  private String nationality = "Mexicana";

  @Column(nullable = false, length = 18)
  private String curp;

  @Column(nullable = false, length = 13)
  private String rfc;

  @Column(nullable = false, length = 11)
  private String nss;

  @Column(length = 18)
  private String clabe;

  @Column(name = "employee_number", length = 32)
  private String employeeNumber;

  @Column(nullable = false, length = 120)
  private String position;

  @Column(nullable = false, length = 120)
  private String department;

  @Enumerated(EnumType.STRING)
  @Column(name = "contract_type", nullable = false, length = 32)
  private ContractType contractType;

  @Enumerated(EnumType.STRING)
  @Column(name = "work_shift", nullable = false, length = 32)
  private WorkShift workShift;

  @Column(name = "hire_date")
  private LocalDate hireDate;

  @Column(name = "termination_date")
  private LocalDate terminationDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private EmployeeStatus status;

  @Column(name = "salary_per_week", nullable = false, precision = 19, scale = 6)
  private BigDecimal salaryPerWeek;

  @Column(nullable = false, precision = 19, scale = 6)
  private BigDecimal bonuses;

  @Column(name = "food_vouchers", nullable = false, precision = 19, scale = 6)
  private BigDecimal foodVouchers;

  @Column(name = "integration_factor", nullable = false, precision = 19, scale = 6)
  private BigDecimal integrationFactor;

  @Enumerated(EnumType.STRING)
  @Column(name = "imss_worker_type", nullable = false, length = 32)
  private ImssWorkerType imssWorkerType;

  @Enumerated(EnumType.STRING)
  @Column(name = "imss_salary_type", nullable = false, length = 32)
  private ImssSalaryType imssSalaryType;

  @Column(name = "christmas_bonus_days", nullable = false)
  private int christmasBonusDays;

  @Column(name = "vacation_days", nullable = false)
  private int vacationDays;

  @Column(name = "vacation_premium_percent", nullable = false, precision = 19, scale = 6)
  private BigDecimal vacationPremiumPercent;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(nullable = false)
  private Long version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public String getCurp() {
    return curp;
  }

  public void setCurp(String curp) {
    this.curp = curp;
  }

  public String getRfc() {
    return rfc;
  }

  public void setRfc(String rfc) {
    this.rfc = rfc;
  }

  public String getNss() {
    return nss;
  }

  public void setNss(String nss) {
    this.nss = nss;
  }

  public String getClabe() {
    return clabe;
  }

  public void setClabe(String clabe) {
    this.clabe = clabe;
  }

  public String getEmployeeNumber() {
    return employeeNumber;
  }

  public void setEmployeeNumber(String employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public ContractType getContractType() {
    return contractType;
  }

  public void setContractType(ContractType contractType) {
    this.contractType = contractType;
  }

  public WorkShift getWorkShift() {
    return workShift;
  }

  public void setWorkShift(WorkShift workShift) {
    this.workShift = workShift;
  }

  public LocalDate getHireDate() {
    return hireDate;
  }

  public void setHireDate(LocalDate hireDate) {
    this.hireDate = hireDate;
  }

  public LocalDate getTerminationDate() {
    return terminationDate;
  }

  public void setTerminationDate(LocalDate terminationDate) {
    this.terminationDate = terminationDate;
  }

  public EmployeeStatus getStatus() {
    return status;
  }

  public void setStatus(EmployeeStatus status) {
    this.status = status;
  }

  public BigDecimal getSalaryPerWeek() {
    return salaryPerWeek;
  }

  public void setSalaryPerWeek(BigDecimal salaryPerWeek) {
    this.salaryPerWeek = salaryPerWeek;
  }

  public BigDecimal getBonuses() {
    return bonuses;
  }

  public void setBonuses(BigDecimal bonuses) {
    this.bonuses = bonuses;
  }

  public BigDecimal getFoodVouchers() {
    return foodVouchers;
  }

  public void setFoodVouchers(BigDecimal foodVouchers) {
    this.foodVouchers = foodVouchers;
  }

  public BigDecimal getIntegrationFactor() {
    return integrationFactor;
  }

  public void setIntegrationFactor(BigDecimal integrationFactor) {
    this.integrationFactor = integrationFactor;
  }

  public ImssWorkerType getImssWorkerType() {
    return imssWorkerType;
  }

  public void setImssWorkerType(ImssWorkerType imssWorkerType) {
    this.imssWorkerType = imssWorkerType;
  }

  public ImssSalaryType getImssSalaryType() {
    return imssSalaryType;
  }

  public void setImssSalaryType(ImssSalaryType imssSalaryType) {
    this.imssSalaryType = imssSalaryType;
  }

  public int getChristmasBonusDays() {
    return christmasBonusDays;
  }

  public void setChristmasBonusDays(int christmasBonusDays) {
    this.christmasBonusDays = christmasBonusDays;
  }

  public int getVacationDays() {
    return vacationDays;
  }

  public void setVacationDays(int vacationDays) {
    this.vacationDays = vacationDays;
  }

  public BigDecimal getVacationPremiumPercent() {
    return vacationPremiumPercent;
  }

  public void setVacationPremiumPercent(BigDecimal vacationPremiumPercent) {
    this.vacationPremiumPercent = vacationPremiumPercent;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
