package io.github.alexistrejo11.pimienta.module.account.user.core.domain.exceptions;

import io.github.alexistrejo11.pimienta.shared.exception.ConflictException;
import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import java.util.Map;

public class PhoneAlreadyExistsException extends ConflictException {
  public PhoneAlreadyExistsException(String phone) {
    super(
        ErrorCode.PHONE_ALREADY_EXISTS,
        "A user with this phone already exists.",
        Map.of("phone", phone),
        "Phone already registered: " + phone);
  }
}
