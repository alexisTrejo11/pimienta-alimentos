package io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.HireEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.UpdateEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto.DepartmentHeadcountResponse;
import io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto.EmployeeListItemResponse;
import io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto.EmployeeResponse;
import io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto.EmployeeStatisticsResponse;
import io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto.EmployeeSummaryResponse;
import io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto.HireEmployeeRequest;
import io.github.alexistrejo11.pimienta.module.employees.infrastructure.adapter.inbound.web.dto.UpdateEmployeeRequest;
import java.util.ArrayList;
import java.util.List;

final class EmployeeManagerWebMapper {

  private EmployeeManagerWebMapper() {}

  static EmployeeListItemResponse toListItem(Employee employee) {
    return new EmployeeListItemResponse(
        employee.getId(),
        employee.getName(),
        employee.getEmail(),
        employee.getDepartment(),
        employee.getPosition(),
        employee.getStatus(),
        employee.getHireDate());
  }

  static EmployeeResponse toResponse(Employee employee) {
    return new EmployeeResponse(
        employee.getId(),
        employee.getName(),
        employee.getEmail(),
        employee.getPhone(),
        employee.getAddress(),
        employee.getBirthDate(),
        employee.getNationality(),
        employee.getCurp(),
        employee.getRfc(),
        employee.getNss(),
        employee.getClabe(),
        employee.getEmployeeNumber(),
        employee.getPosition(),
        employee.getDepartment(),
        employee.getContractType(),
        employee.getWorkShift(),
        employee.getHireDate(),
        employee.getTerminationDate(),
        employee.getStatus(),
        employee.getSalaryPerWeek(),
        employee.getBonuses(),
        employee.getFoodVouchers(),
        employee.getIntegrationFactor(),
        employee.getImssWorkerType(),
        employee.getImssSalaryType(),
        employee.getChristmasBonus(),
        employee.getVacationDays(),
        employee.getVacationPremiumPercent(),
        employee.getCreatedAt(),
        employee.getUpdatedAt());
  }

  static EmployeeStatisticsResponse toStatisticsResponse(EmployeeStatistics statistics) {
    return new EmployeeStatisticsResponse(
        statistics.total(), statistics.active(), statistics.notActive());
  }

  static EmployeeSummaryResponse toSummaryResponse(EmployeeSummary summary) {
    List<DepartmentHeadcountResponse> rows = new ArrayList<>();
    summary.headcountByDepartment()
        .forEach(
            row ->
                rows.add(
                    new DepartmentHeadcountResponse(row.department(), row.headcount())));
    return new EmployeeSummaryResponse(summary.totalNotDeleted(), rows);
  }

  static HireEmployeeParams toHireParams(HireEmployeeRequest request) {
    return new HireEmployeeParams(
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
        request.birthDate());
  }

  static UpdateEmployeeParams toUpdateParams(Long id, UpdateEmployeeRequest request) {
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
