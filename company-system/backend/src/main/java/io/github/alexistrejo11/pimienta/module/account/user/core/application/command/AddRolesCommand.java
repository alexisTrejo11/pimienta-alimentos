package io.github.alexistrejo11.pimienta.module.account.user.core.application.command;

import io.github.alexistrejo11.pimienta.module.account.user.core.domain.Role;
import java.util.List;

public record AddRolesCommand(List<Role> roles) {}
