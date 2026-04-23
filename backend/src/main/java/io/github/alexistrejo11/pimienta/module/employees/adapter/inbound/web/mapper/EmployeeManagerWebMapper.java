package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.mapper;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.RegisterEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.UpdateEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.DepartmentHeadcountResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeListItemResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeStatisticsResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeSummaryResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.RegisterEmployeeRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.UpdateEmployeeRequest;
import java.util.ArrayList;
import java.util.List;

public final class EmployeeManagerWebMapper {

  private EmployeeManagerWebMapper() {
  }

  public static EmployeeListItemResponse toListItem(Employee employee) {
    return new EmployeeListItemResponse(
        employee.getId(),
        employee.getPersonal().name(),
        employee.getPersonal().email(),
        employee.getEmployment().department(),
        employee.getEmployment().position(),
        employee.getStatus(),
        employee.getEmployment().hireDate());
  }

  public static EmployeeResponse toResponse(Employee employee) {
    return new EmployeeResponse(
        employee.getId(),
        employee.getPersonal().name(),
        employee.getPersonal().email(),
        employee.getPersonal().phone(),
        employee.getPersonal().address(),
        employee.getPersonal().birthDate(),
        employee.getPersonal().nationality(),
        employee.getOfficialIds().curp(),
        employee.getOfficialIds().rfc(),
        employee.getOfficialIds().nss(),
        employee.getOfficialIds().clabe(),
        employee.getOfficialIds().employeeNumber(),
        employee.getEmployment().position(),
        employee.getEmployment().department(),
        employee.getEmployment().contractType(),
        employee.getEmployment().workShift(),
        employee.getEmployment().hireDate(),
        employee.getEmployment().terminationDate(),
        employee.getStatus(),
        employee.getCompensation().salaryPerWeek(),
        employee.getCompensation().bonuses(),
        employee.getCompensation().foodVouchers(),
        employee.getBenefits().integrationFactor(),
        employee.getBenefits().imssWorkerType(),
        employee.getBenefits().imssSalaryType(),
        employee.getBenefits().christmasBonusDays(),
        employee.getBenefits().vacationDays(),
        employee.getBenefits().vacationPremiumPercent(),
        employee.getCreatedAt(),
        employee.getUpdatedAt());
  }

  public static EmployeeStatisticsResponse toStatisticsResponse(EmployeeStatistics statistics) {
    return new EmployeeStatisticsResponse(
        statistics.total(), statistics.active(), statistics.notActive());
  }

  public static EmployeeSummaryResponse toSummaryResponse(EmployeeSummary summary) {
    List<DepartmentHeadcountResponse> rows = new ArrayList<>();
    summary.headcountByDepartment()
        .forEach(
            row -> rows.add(
                new DepartmentHeadcountResponse(row.department(), row.headcount())));
    return new EmployeeSummaryResponse(summary.totalNotDeleted(), rows);
  }

  public static RegisterEmployeeParams toRegisterParams(RegisterEmployeeRequest request) {
    return new RegisterEmployeeParams(
        request.name(),
        request.email().trim().toLowerCase(),
        request.phone(),
        request.address(),
        request.curp(),
        request.rfc(),
        request.nss(),
        request.clabe(),
        request.employeeNumber(),
        request.position(),
        request.department(),
        request.contractType(),
        request.workShift(),
        request.salaryPerWeek(),
        request.birthDate(),
        request.onboardingPhase());
  }

  public static UpdateEmployeeParams toUpdateParams(Long id, UpdateEmployeeRequest request) {
    return new UpdateEmployeeParams(
        id,
        request.name(),
        request.email().trim().toLowerCase(),
        request.phone(),
        request.address(),
        request.curp(),
        request.rfc(),
        request.nss(),
        request.clabe(),
        request.position(),
        request.department(),
        request.contractType(),
        request.workShift(),
        request.salaryPerWeek(),
        request.bonuses(),
        request.foodVouchers(),
        request.integrationFactor());
  }
}
