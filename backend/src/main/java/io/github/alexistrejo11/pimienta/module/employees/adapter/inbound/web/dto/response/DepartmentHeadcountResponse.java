package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.response;

import lombok.Builder;

@Builder
public record DepartmentHeadcountResponse(String department, long headcount) {}
