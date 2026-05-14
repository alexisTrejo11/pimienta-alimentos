package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.mapper;

import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.param.RegisterEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.application.dto.param.UpdateEmployeeParams;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeStatistics;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.EmployeeSummary;
import io.github.alexistrejo11.pimienta.module.employees.core.domain.model.Employee;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.DepartmentHeadcountResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeListItemResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeStatisticsResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response.EmployeeSummaryResponse;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.RegisterEmployeeRequest;
import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.UpdateEmployeeRequest;
import java.util.List;
import java.util.function.Function;
import org.springframework.web.multipart.MultipartFile;

public final class EmployeeManagerWebMapper {

  private EmployeeManagerWebMapper() {
  }

  public static EmployeeListItemResponse toListItem(
      Employee employee, Function<String, String> mapPhotoUrl) {
    String rawPhoto = employee.getPersonal().photoUrl();
    return EmployeeListItemResponse.builder()
        .id(employee.getId())
        .fullName(employee.getPersonal().firstName() + " " + employee.getPersonal().lastName())
        .photoUrl(mapPhotoUrl.apply(rawPhoto != null ? rawPhoto : ""))
        .email(employee.getPersonal().email())
        .department(employee.getEmployment().department())
        .position(employee.getEmployment().position())
        .status(employee.getStatus())
        .hireDate(employee.getEmployment().hireDate())
        .build();
  }

  public static EmployeeResponse toResponse(Employee employee, Function<String, String> mapPhotoUrl) {
    String rawPhoto = employee.getPersonal().photoUrl();
    return EmployeeResponse.builder()
        .id(employee.getId())
        .firstName(employee.getPersonal().firstName())
        .lastName(employee.getPersonal().lastName())
        .photoUrl(mapPhotoUrl.apply(rawPhoto != null ? rawPhoto : ""))
        .email(employee.getPersonal().email())
        .phone(employee.getPersonal().phone())
        .address(employee.getPersonal().address())
        .birthDate(employee.getPersonal().birthDate())
        .nationality(employee.getPersonal().nationality())
        .curp(employee.getOfficialIds().curp())
        .rfc(employee.getOfficialIds().rfc())
        .nss(employee.getOfficialIds().nss())
        .clabe(employee.getOfficialIds().clabe())
        .employeeNumber(employee.getOfficialIds().employeeNumber())
        .position(employee.getEmployment().position())
        .department(employee.getEmployment().department())
        .contractType(employee.getEmployment().contractType())
        .workShift(employee.getEmployment().workShift())
        .hireDate(employee.getEmployment().hireDate())
        .terminationDate(employee.getEmployment().terminationDate())
        .status(employee.getStatus())
        .salaryPerWeek(employee.getCompensation().salaryPerWeek())
        .bonuses(employee.getCompensation().bonuses())
        .foodVouchers(employee.getCompensation().foodVouchers())
        .integrationFactor(employee.getBenefits().integrationFactor())
        .imssWorkerType(employee.getBenefits().imssWorkerType())
        .imssSalaryType(employee.getBenefits().imssSalaryType())
        .christmasBonusDays(employee.getBenefits().christmasBonusDays())
        .vacationDays(employee.getBenefits().vacationDays())
        .vacationPremiumPercent(employee.getBenefits().vacationPremiumPercent())
        .createdAt(employee.getCreatedAt())
        .updatedAt(employee.getUpdatedAt())
        .build();
  }

  public static EmployeeStatisticsResponse toStatisticsResponse(EmployeeStatistics statistics) {
    return EmployeeStatisticsResponse.builder()
        .total(statistics.total())
        .active(statistics.active())
        .notActive(statistics.notActive())
        .build();
  }

  public static EmployeeSummaryResponse toSummaryResponse(EmployeeSummary summary) {
    List<DepartmentHeadcountResponse> rows = summary.headcountByDepartment().stream()
        .map(
            row -> DepartmentHeadcountResponse.builder()
                .department(row.department())
                .headcount(row.headcount())
                .build())
        .toList();
    return EmployeeSummaryResponse.builder()
        .totalNotDeleted(summary.totalNotDeleted())
        .headcountByDepartment(rows)
        .build();
  }

  public static RegisterEmployeeParams toRegisterParams(RegisterEmployeeRequest request, MultipartFile photo) {
    return RegisterEmployeeParams.builder()
        .firstName(request.firstName())
        .lastName(request.lastName())
        .email(request.email().trim().toLowerCase())
        .phone(request.phone())
        .address(request.address())
        .curp(request.curp())
        .rfc(request.rfc())
        .nss(request.nss())
        .clabe(request.clabe())
        .employeeNumber(request.employeeNumber())
        .position(request.position())
        .department(request.department())
        .contractType(request.contractType())
        .workShift(request.workShift())
        .salaryPerWeek(request.salaryPerWeek())
        .birthDate(request.birthDate())
        .onboardingPhase(request.onboardingPhase())
        .photo(photo)
        .build();
  }

  public static UpdateEmployeeParams toUpdateParams(Long id, UpdateEmployeeRequest request, MultipartFile photo) {
    return UpdateEmployeeParams.builder()
        .id(id)
        .firstName(request.firstName())
        .lastName(request.lastName())
        .photo(photo)
        .email(request.email().trim().toLowerCase())
        .phone(request.phone())
        .address(request.address())
        .curp(request.curp())
        .rfc(request.rfc())
        .nss(request.nss())
        .clabe(request.clabe())
        .position(request.position())
        .department(request.department())
        .contractType(request.contractType())
        .workShift(request.workShift())
        .salaryPerWeek(request.salaryPerWeek())
        .bonuses(request.bonuses())
        .foodVouchers(request.foodVouchers())
        .integrationFactor(request.integrationFactor())
        .build();
  }
}
