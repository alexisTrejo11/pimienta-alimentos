package io.github.alexistrejo11.pimienta.module.account.auth.core.application.command;

import io.github.alexistrejo11.pimienta.module.account.user.core.domain.Gender;
import java.time.LocalDate;

public record RegisterCommand(
    String email,
    String password,
    String firstName,
    String lastName,
    Gender gender,
    String phone,
    LocalDate dateOfBirth) {}
