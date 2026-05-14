package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response;

import lombok.Builder;

@Builder
public record EmployeeStatisticsResponse(long total, long active, long notActive) {}
