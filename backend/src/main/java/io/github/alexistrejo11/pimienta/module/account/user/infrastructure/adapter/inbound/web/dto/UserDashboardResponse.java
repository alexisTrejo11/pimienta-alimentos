package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto;

public record UserDashboardResponse(
    int totalActiveEmployees,
    int totalActiveProjects,
    int totalActiveHeadquarters,
    int totalActivePersonalTasks,
    int totalPendingPersonalTasks,
    int totalActiveEmployeesTasks,
    int totalEmployeePending) {}
