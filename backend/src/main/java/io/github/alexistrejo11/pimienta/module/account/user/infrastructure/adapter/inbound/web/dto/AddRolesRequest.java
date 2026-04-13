package io.github.alexistrejo11.pimienta.module.account.user.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record AddRolesRequest(@NotEmpty List<String> roles) {}
