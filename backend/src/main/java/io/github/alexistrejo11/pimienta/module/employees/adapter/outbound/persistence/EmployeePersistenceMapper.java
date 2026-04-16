package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence;

import io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model.EmployeeJpaEntity;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.BenefitsProfile;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.Compensation;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employment;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.OfficialIdentifiers;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.PersonalProfile;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.dto.ReconstructEmployeeParams;

public class EmployeePersistenceMapper {

  private EmployeePersistenceMapper() {}

  public static EmployeeJpaEntity toJpa(Employee domain) {
    EmployeeJpaEntity e = new EmployeeJpaEntity();
    if (domain.getId() != null && domain.getId() > 0) {
      e.setId(domain.getId());
    }
    e.setFullName(domain.getName());
    e.setEmail(domain.getEmail());
    e.setPhone(domain.getPhone());
    e.setAddress(domain.getAddress());
    e.setBirthDate(domain.getBirthDate());
    e.setNationality(domain.getNationality());
    e.setCurp(domain.getCurp());
    e.setRfc(domain.getRfc());
    e.setNss(domain.getNss());
    e.setClabe(domain.getClabe());
    e.setEmployeeNumber(domain.getEmployeeNumber());
    e.setPosition(domain.getPosition());
    e.setDepartment(domain.getDepartment());
    e.setContractType(domain.getContractType());
    e.setWorkShift(domain.getWorkShift());
    e.setHireDate(domain.getHireDate());
    e.setTerminationDate(domain.getTerminationDate());
    e.setStatus(domain.getStatus());
    e.setSalaryPerWeek(domain.getSalaryPerWeek());
    e.setBonuses(domain.getBonuses());
    e.setFoodVouchers(domain.getFoodVouchers());
    e.setIntegrationFactor(domain.getIntegrationFactor());
    e.setImssWorkerType(domain.getImssWorkerType());
    e.setImssSalaryType(domain.getImssSalaryType());
    e.setChristmasBonusDays(domain.getChristmasBonus());
    e.setVacationDays(domain.getVacationDays());
    e.setVacationPremiumPercent(domain.getVacationPremiumPercent());
    e.setCreatedAt(domain.getCreatedAt());
    e.setUpdatedAt(domain.getUpdatedAt());
    e.setDeletedAt(domain.getDeletedAt());
    e.setVersion(domain.getVersion() != null ? domain.getVersion() : 0L);
    return e;
  }

  public static Employee toDomain(EmployeeJpaEntity e) {
    PersonalProfile personal =
        new PersonalProfile(
            e.getFullName(),
            e.getEmail(),
            e.getPhone(),
            e.getAddress(),
            e.getBirthDate(),
            e.getNationality());
    OfficialIdentifiers official =
        new OfficialIdentifiers(
            e.getCurp(), e.getRfc(), e.getNss(), e.getClabe(), e.getEmployeeNumber());
    Employment employment =
        new Employment(
            e.getPosition(),
            e.getDepartment(),
            e.getContractType(),
            e.getWorkShift(),
            e.getHireDate(),
            e.getTerminationDate(),
            e.getStatus());
    Compensation compensation =
        new Compensation(e.getSalaryPerWeek(), e.getBonuses(), e.getFoodVouchers());
    BenefitsProfile benefits =
        new BenefitsProfile(
            e.getIntegrationFactor(),
            e.getImssWorkerType(),
            e.getImssSalaryType(),
            e.getChristmasBonusDays(),
            e.getVacationDays(),
            e.getVacationPremiumPercent());
    return Employee.reconstruct(
        new ReconstructEmployeeParams(
            e.getId(),
            personal,
            official,
            employment,
            compensation,
            benefits,
            e.getCreatedAt(),
            e.getUpdatedAt(),
            e.getDeletedAt(),
            e.getVersion()));
  }
}
