package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.enums.EmployeeStatus;
import io.github.alexistrejo11.pimienta.shared.jpa.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "employees")
public class EmployeeJpaEntity extends BaseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private EmployeePersonalEmbeddable personal = new EmployeePersonalEmbeddable();

  @Embedded
  private EmployeeOfficialIdsEmbeddable officialIds = new EmployeeOfficialIdsEmbeddable();

  @Embedded
  private EmployeeEmploymentEmbeddable employment = new EmployeeEmploymentEmbeddable();

  @Embedded
  private EmployeeCompensationEmbeddable compensation = new EmployeeCompensationEmbeddable();

  @Embedded
  private EmployeeBenefitsEmbeddable benefits = new EmployeeBenefitsEmbeddable();

  @Enumerated(EnumType.STRING)
  @Column(length = 32)
  private EmployeeStatus status;

  /** Stored as JSON; on PostgreSQL Hibernate maps this to a json/jsonb-compatible column. */
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "work_schedule")
  private EmployeeWorkScheduleJson workSchedule;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public EmployeePersonalEmbeddable getPersonal() {
    return personal;
  }

  public void setPersonal(EmployeePersonalEmbeddable personal) {
    this.personal = personal != null ? personal : new EmployeePersonalEmbeddable();
  }

  public EmployeeOfficialIdsEmbeddable getOfficialIds() {
    return officialIds;
  }

  public void setOfficialIds(EmployeeOfficialIdsEmbeddable officialIds) {
    this.officialIds = officialIds != null ? officialIds : new EmployeeOfficialIdsEmbeddable();
  }

  public EmployeeEmploymentEmbeddable getEmployment() {
    return employment;
  }

  public void setEmployment(EmployeeEmploymentEmbeddable employment) {
    this.employment = employment != null ? employment : new EmployeeEmploymentEmbeddable();
  }

  public EmployeeCompensationEmbeddable getCompensation() {
    return compensation;
  }

  public void setCompensation(EmployeeCompensationEmbeddable compensation) {
    this.compensation = compensation != null ? compensation : new EmployeeCompensationEmbeddable();
  }

  public EmployeeBenefitsEmbeddable getBenefits() {
    return benefits;
  }

  public void setBenefits(EmployeeBenefitsEmbeddable benefits) {
    this.benefits = benefits != null ? benefits : new EmployeeBenefitsEmbeddable();
  }

  public EmployeeStatus getStatus() {
    return status;
  }

  public void setStatus(EmployeeStatus status) {
    this.status = status;
  }

  public EmployeeWorkScheduleJson getWorkSchedule() {
    return workSchedule;
  }

  public void setWorkSchedule(EmployeeWorkScheduleJson workSchedule) {
    this.workSchedule = workSchedule;
  }
}
