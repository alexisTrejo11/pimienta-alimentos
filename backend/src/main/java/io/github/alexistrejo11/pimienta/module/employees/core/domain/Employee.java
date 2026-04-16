package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.dto.HireEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.dto.ReconstructEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.dto.UpdateEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.*;
import io.github.alexistrejo11.pimienta.shared.BaseDomain;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Agregado empleado: compone perfiles de valor (personales, identificadores, empleo, compensación,
 * prestaciones/IMSS).
 */
public class Employee extends BaseDomain<Long> {

  private PersonalProfile personal;
  private OfficialIdentifiers officialIds;
  private Employment employment;
  private Compensation compensation;
  private BenefitsProfile benefits;

  public Employee() {
    this.id = 0L;
    this.personal = PersonalProfile.empty();
    this.officialIds = OfficialIdentifiers.empty();
    this.employment =
        new Employment(
            "",
            "",
            ContractType.INDEFINITE,
            WorkShift.MORNING,
            null,
            null,
            EmployeeStatus.ACTIVE);
    this.compensation = Compensation.baseline(BigDecimal.ZERO);
    this.benefits = BenefitsProfile.legalDefaults();
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
    this.deletedAt = null;
    this.version = 0L;
  }

  /** Alta de empleado: validación implícita vía value objects. */
  public static Employee hire(HireEmployeeParams params) {
    LocalDateTime now = LocalDateTime.now();
    Employee employee = new Employee();
    employee.personal =
        new PersonalProfile(
            params.name(),
            params.email(),
            params.phone(),
            params.address(),
            params.birthDate(),
            "Mexicana");
    employee.officialIds =
        new OfficialIdentifiers(
            params.curp(),
            params.rfc(),
            params.nss(),
            params.clabe(),
            params.employeeNumber());
    employee.employment =
        Employment.hired(
            new EmploymentHiredParams(
                params.position(),
                params.department(),
                params.contractType(),
                params.workShift(),
                now.toLocalDate()));
    employee.compensation = Compensation.baseline(params.salaryPerWeek());
    employee.benefits = BenefitsProfile.legalDefaults();
    employee.createdAt = now;
    employee.updatedAt = now;
    return employee;
  }

  public static Employee update(UpdateEmployeeParams params) {
    LocalDateTime now = LocalDateTime.now();
    Employee employee = new Employee();
    employee.id = params.id();
    employee.personal =
        new PersonalProfile(
            params.name(),
            params.email(),
            params.phone(),
            params.address(),
            null,
            "Mexicana");
    employee.officialIds =
        new OfficialIdentifiers(params.curp(), params.rfc(), params.nss(), params.clabe(), "");
    employee.employment =
        new Employment(
            params.position(),
            params.department(),
            params.contractType(),
            params.workShift(),
            null,
            null,
            EmployeeStatus.ACTIVE);
    employee.compensation =
        new Compensation(params.salaryPerWeek(), params.bonuses(), params.foodVouchers());
    employee.benefits =
        new BenefitsProfile(
            params.integrationFactor(),
            ImssWorkerType.PERMANENT_URBAN,
            ImssSalaryType.FIXED,
            15,
            12,
            BenefitsProfile.DEFAULT_VACATION_PREMIUM_PERCENT);
    employee.updatedAt = now;
    return employee;
  }

  /**
   * Reconstrucción desde persistencia: sin validación de negocio; el mapper de repositorio arma
   * {@link ReconstructEmployeeParams}.
   */
  public static Employee reconstruct(ReconstructEmployeeParams params) {
    Employee employee = new Employee();
    employee.id = params.id() != null ? params.id() : 0L;
    employee.personal =
        params.personal() != null ? params.personal() : PersonalProfile.empty();
    employee.officialIds =
        params.officialIds() != null ? params.officialIds() : OfficialIdentifiers.empty();
    employee.employment =
        params.employment() != null
            ? params.employment()
            : new Employment(
                "",
                "",
                ContractType.INDEFINITE,
                WorkShift.MORNING,
                null,
                null,
                EmployeeStatus.ACTIVE);
    employee.compensation =
        params.compensation() != null
            ? params.compensation()
            : Compensation.baseline(BigDecimal.ZERO);
    employee.benefits =
        params.benefits() != null ? params.benefits() : BenefitsProfile.legalDefaults();
    employee.createdAt =
        params.createdAt() != null ? params.createdAt() : LocalDateTime.now();
    employee.updatedAt =
        params.updatedAt() != null ? params.updatedAt() : employee.createdAt;
    employee.deletedAt = params.deletedAt();
    employee.version = params.version() != null ? params.version() : 0L;
    return employee;
  }

  // ─── Payroll (delegación a value objects) ───

  public BigDecimal getDailyBaseSalary() {
    return compensation.dailyBaseSalary();
  }

  public BigDecimal getGrossSalaryPerWeek() {
    return compensation.grossWeekly();
  }

  public BigDecimal getGrossSalaryPer15Days() {
    return compensation.grossPer15Days();
  }

  public BigDecimal getGrossSalaryPerMonth() {
    return compensation.grossMonthly();
  }

  public BigDecimal getDailySBC() {
    return benefits.dailySbc(compensation);
  }

  public BigDecimal getImssEmployeeContribution() {
    return benefits.imssEmployeeContribution(compensation);
  }

  public BigDecimal getImssEmployerContribution() {
    return benefits.imssEmployerContribution(compensation);
  }

  public BigDecimal getInfonavitContribution() {
    return benefits.infonavitEmployerContribution(compensation);
  }

  public BigDecimal getSarContribution() {
    return benefits.sarEmployerContribution(compensation);
  }

  public BigDecimal getTotalMonthlyCostForEmployer() {
    return benefits.totalMonthlyEmployerCost(compensation);
  }

  public BigDecimal getAnnualChristmasBonus() {
    return benefits.annualChristmasBonus(compensation);
  }

  public BigDecimal getVacationPremium() {
    return benefits.vacationPremium(compensation);
  }

  // ─── Transiciones de estado ───

  public void terminate() {
    this.employment =
        employment.withTermination(EmployeeStatus.TERMINATED, LocalDate.now());
    touch();
  }

  public void fire() {
    this.employment = employment.withTermination(EmployeeStatus.FIRED, LocalDate.now());
    touch();
  }

  public void resign() {
    this.employment = employment.withTermination(EmployeeStatus.RESIGNED, LocalDate.now());
    touch();
  }

  public void sick() {
    this.employment = employment.withStatus(EmployeeStatus.SICK);
    touch();
  }

  public void onVacation() {
    this.employment = employment.withStatus(EmployeeStatus.ON_VACATION);
    touch();
  }

  public void onLeave() {
    this.employment = employment.withStatus(EmployeeStatus.ON_LEAVE);
    touch();
  }

  public void activate() {
    this.employment = employment.withStatus(EmployeeStatus.ACTIVE);
    touch();
  }

  /** Vuelve a contratación efectiva: estatus activo y sin fecha de baja. */
  public void rehire() {
    this.employment =
        new Employment(
            employment.position(),
            employment.department(),
            employment.contractType(),
            employment.workShift(),
            employment.hireDate(),
            null,
            EmployeeStatus.ACTIVE);
    touch();
  }

  /** Baja lógica del registro (no confundir con terminación laboral). */
  public void delete() {
    this.deletedAt = LocalDateTime.now();
    touch();
  }

  private void touch() {
    this.updatedAt = LocalDateTime.now();
    this.version = this.version != null ? this.version + 1 : 1L;
  }

  // ─── Accesos al agregado ───

  public PersonalProfile getPersonal() {
    return personal;
  }

  public OfficialIdentifiers getOfficialIds() {
    return officialIds;
  }

  public Employment getEmployment() {
    return employment;
  }

  public Compensation getCompensation() {
    return compensation;
  }

  public BenefitsProfile getBenefits() {
    return benefits;
  }

  public void setPersonal(PersonalProfile personal) {
    this.personal = personal != null ? personal : PersonalProfile.empty();
    touch();
  }

  public void setOfficialIds(OfficialIdentifiers officialIds) {
    this.officialIds = officialIds != null ? officialIds : OfficialIdentifiers.empty();
    touch();
  }

  public void setEmployment(Employment employment) {
    this.employment = employment;
    touch();
  }

  public void setCompensation(Compensation compensation) {
    this.compensation = compensation != null ? compensation : Compensation.baseline(BigDecimal.ZERO);
    touch();
  }

  public void setBenefits(BenefitsProfile benefits) {
    this.benefits = benefits != null ? benefits : BenefitsProfile.legalDefaults();
    touch();
  }

  // ─── Getters delegados (compatibilidad / mapeo) ───

  public String getName() {
    return personal.name();
  }

  public void setName(String name) {
    this.personal = personal.withName(name);
    touch();
  }

  public String getEmail() {
    return personal.email();
  }

  public void setEmail(String email) {
    this.personal = personal.withEmail(email);
    touch();
  }

  public String getPhone() {
    return personal.phone();
  }

  public void setPhone(String phone) {
    this.personal = personal.withPhone(phone);
    touch();
  }

  public String getAddress() {
    return personal.address();
  }

  public void setAddress(String address) {
    this.personal = personal.withAddress(address);
    touch();
  }

  public LocalDate getBirthDate() {
    return personal.birthDate();
  }

  public void setBirthDate(LocalDate birthDate) {
    this.personal = personal.withBirthDate(birthDate);
    touch();
  }

  public String getNationality() {
    return personal.nationality();
  }

  public void setNationality(String nationality) {
    this.personal = personal.withNationality(nationality);
    touch();
  }

  public String getCurp() {
    return officialIds.curp();
  }

  public void setCurp(String curp) {
    this.officialIds =
        new OfficialIdentifiers(curp, officialIds.rfc(), officialIds.nss(), officialIds.clabe(), officialIds.employeeNumber());
    touch();
  }

  public String getRfc() {
    return officialIds.rfc();
  }

  public void setRfc(String rfc) {
    this.officialIds =
        new OfficialIdentifiers(officialIds.curp(), rfc, officialIds.nss(), officialIds.clabe(), officialIds.employeeNumber());
    touch();
  }

  public String getNss() {
    return officialIds.nss();
  }

  public void setNss(String nss) {
    this.officialIds =
        new OfficialIdentifiers(officialIds.curp(), officialIds.rfc(), nss, officialIds.clabe(), officialIds.employeeNumber());
    touch();
  }

  public String getClabe() {
    return officialIds.clabe();
  }

  public void setClabe(String clabe) {
    this.officialIds =
        new OfficialIdentifiers(officialIds.curp(), officialIds.rfc(), officialIds.nss(), clabe, officialIds.employeeNumber());
    touch();
  }

  public String getEmployeeNumber() {
    return officialIds.employeeNumber();
  }

  public void setEmployeeNumber(String employeeNumber) {
    this.officialIds =
        new OfficialIdentifiers(
            officialIds.curp(), officialIds.rfc(), officialIds.nss(), officialIds.clabe(), employeeNumber);
    touch();
  }

  public String getPosition() {
    return employment.position();
  }

  public void setPosition(String position) {
    this.employment =
        new Employment(
            position,
            employment.department(),
            employment.contractType(),
            employment.workShift(),
            employment.hireDate(),
            employment.terminationDate(),
            employment.status());
    touch();
  }

  public String getDepartment() {
    return employment.department();
  }

  public void setDepartment(String department) {
    this.employment =
        new Employment(
            employment.position(),
            department,
            employment.contractType(),
            employment.workShift(),
            employment.hireDate(),
            employment.terminationDate(),
            employment.status());
    touch();
  }

  public ContractType getContractType() {
    return employment.contractType();
  }

  public void setContractType(ContractType contractType) {
    this.employment =
        new Employment(
            employment.position(),
            employment.department(),
            contractType,
            employment.workShift(),
            employment.hireDate(),
            employment.terminationDate(),
            employment.status());
    touch();
  }

  public WorkShift getWorkShift() {
    return employment.workShift();
  }

  public void setWorkShift(WorkShift workShift) {
    this.employment =
        new Employment(
            employment.position(),
            employment.department(),
            employment.contractType(),
            workShift,
            employment.hireDate(),
            employment.terminationDate(),
            employment.status());
    touch();
  }

  public LocalDate getHireDate() {
    return employment.hireDate();
  }

  public void setHireDate(LocalDate hireDate) {
    this.employment =
        new Employment(
            employment.position(),
            employment.department(),
            employment.contractType(),
            employment.workShift(),
            hireDate,
            employment.terminationDate(),
            employment.status());
    touch();
  }

  public LocalDate getTerminationDate() {
    return employment.terminationDate();
  }

  public void setTerminationDate(LocalDate terminationDate) {
    this.employment =
        new Employment(
            employment.position(),
            employment.department(),
            employment.contractType(),
            employment.workShift(),
            employment.hireDate(),
            terminationDate,
            employment.status());
    touch();
  }

  public EmployeeStatus getStatus() {
    return employment.status();
  }

  public void setStatus(EmployeeStatus status) {
    this.employment =
        new Employment(
            employment.position(),
            employment.department(),
            employment.contractType(),
            employment.workShift(),
            employment.hireDate(),
            employment.terminationDate(),
            status);
    touch();
  }

  public BigDecimal getSalaryPerWeek() {
    return compensation.salaryPerWeek();
  }

  public void setSalaryPerWeek(BigDecimal salaryPerWeek) {
    this.compensation =
        new Compensation(
            salaryPerWeek, compensation.bonuses(), compensation.foodVouchers());
    touch();
  }

  public BigDecimal getBonuses() {
    return compensation.bonuses();
  }

  public void setBonuses(BigDecimal bonuses) {
    this.compensation =
        new Compensation(compensation.salaryPerWeek(), bonuses, compensation.foodVouchers());
    touch();
  }

  public BigDecimal getFoodVouchers() {
    return compensation.foodVouchers();
  }

  public void setFoodVouchers(BigDecimal foodVouchers) {
    this.compensation =
        new Compensation(compensation.salaryPerWeek(), compensation.bonuses(), foodVouchers);
    touch();
  }

  public BigDecimal getIntegrationFactor() {
    return benefits.integrationFactor();
  }

  public void setIntegrationFactor(BigDecimal integrationFactor) {
    this.benefits =
        new BenefitsProfile(
            integrationFactor,
            benefits.imssWorkerType(),
            benefits.imssSalaryType(),
            benefits.christmasBonusDays(),
            benefits.vacationDays(),
            benefits.vacationPremiumPercent());
    touch();
  }

  public ImssWorkerType getImssWorkerType() {
    return benefits.imssWorkerType();
  }

  public void setImssWorkerType(ImssWorkerType imssWorkerType) {
    this.benefits =
        new BenefitsProfile(
            benefits.integrationFactor(),
            imssWorkerType,
            benefits.imssSalaryType(),
            benefits.christmasBonusDays(),
            benefits.vacationDays(),
            benefits.vacationPremiumPercent());
    touch();
  }

  public ImssSalaryType getImssSalaryType() {
    return benefits.imssSalaryType();
  }

  public void setImssSalaryType(ImssSalaryType imssSalaryType) {
    this.benefits =
        new BenefitsProfile(
            benefits.integrationFactor(),
            benefits.imssWorkerType(),
            imssSalaryType,
            benefits.christmasBonusDays(),
            benefits.vacationDays(),
            benefits.vacationPremiumPercent());
    touch();
  }

  public int getChristmasBonus() {
    return benefits.christmasBonusDays();
  }

  public void setChristmasBonus(int christmasBonus) {
    this.benefits =
        new BenefitsProfile(
            benefits.integrationFactor(),
            benefits.imssWorkerType(),
            benefits.imssSalaryType(),
            christmasBonus,
            benefits.vacationDays(),
            benefits.vacationPremiumPercent());
    touch();
  }

  public int getVacationDays() {
    return benefits.vacationDays();
  }

  public void setVacationDays(int vacationDays) {
    this.benefits =
        new BenefitsProfile(
            benefits.integrationFactor(),
            benefits.imssWorkerType(),
            benefits.imssSalaryType(),
            benefits.christmasBonusDays(),
            vacationDays,
            benefits.vacationPremiumPercent());
    touch();
  }

  public BigDecimal getVacationPremiumPercent() {
    return benefits.vacationPremiumPercent();
  }

  public void setVacationPremiumPercent(BigDecimal vacationPremiumPercent) {
    this.benefits =
        new BenefitsProfile(
            benefits.integrationFactor(),
            benefits.imssWorkerType(),
            benefits.imssSalaryType(),
            benefits.christmasBonusDays(),
            benefits.vacationDays(),
            vacationPremiumPercent);
    touch();
  }
}
