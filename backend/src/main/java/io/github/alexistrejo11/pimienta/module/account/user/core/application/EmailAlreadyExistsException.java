package io.github.alexistrejo11.pimienta.module.account.user.core.application;

import io.github.alexistrejo11.pimienta.shared.exception.ConflictException;
import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import java.util.Map;

public class EmailAlreadyExistsException extends ConflictException {

  public EmailAlreadyExistsException(String email) {
    super(
        ErrorCode.EMAIL_ALREADY_EXISTS,
        "An account with this email already exists.",
        Map.of("email", email),
        "Email already registered: " + email);
  }
}
