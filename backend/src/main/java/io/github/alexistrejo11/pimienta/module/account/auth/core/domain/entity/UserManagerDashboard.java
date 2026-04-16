package io.github.alexistrejo11.pimienta.module.account.auth.core.domain.entity;

public record UserManagerDashboard(
    int totalActiveEmployees,
    int totalActiveProjects,
    int totalActiveHeadquarters,
    int totalActivePersonalTasks,
    int totalPendingPersonalTasks,
    int totalActiveEmployeesTasks,
    int totalEmployeePending) {
}
