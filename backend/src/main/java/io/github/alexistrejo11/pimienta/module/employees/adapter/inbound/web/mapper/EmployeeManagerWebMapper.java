package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.mapper;

import io.github.alexistrejo11.pimienta.module.employees.core.domain.Employee;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.dto.HireEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.dto.UpdateEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.DepartmentHeadcountResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeListItemResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeStatisticsResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeSummaryResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.HireEmployeeRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.UpdateEmployeeRequest;
import java.util.ArrayList;
import java.util.List;

public final class EmployeeManagerWebMapper {

  private EmployeeManagerWebMapper() {}

  public static EmployeeListItemResponse toListItem(Employee employee) {
    return new EmployeeListItemResponse(
        employee.getId(),
        employee.getName(),
        employee.getEmail(),
        employee.getDepartment(),
        employee.getPosition(),
        employee.getStatus(),
        employee.getHireDate());
  }

  public static EmployeeResponse toResponse(Employee employee) {
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

  public static EmployeeStatisticsResponse toStatisticsResponse(EmployeeStatistics statistics) {
    return new EmployeeStatisticsResponse(
        statistics.total(), statistics.active(), statistics.notActive());
  }

  public static EmployeeSummaryResponse toSummaryResponse(EmployeeSummary summary) {
    List<DepartmentHeadcountResponse> rows = new ArrayList<>();
    summary.headcountByDepartment()
        .forEach(
            row ->
                rows.add(
                    new DepartmentHeadcountResponse(row.department(), row.headcount())));
    return new EmployeeSummaryResponse(summary.totalNotDeleted(), rows);
  }

  public static HireEmployeeParams toHireParams(HireEmployeeRequest request) {
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
