package io.github.alexistrejo11.pimienta.module.account.auth.core.application.command;

public record RegisterCommand(String email, String password, String displayName) {}
