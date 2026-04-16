package io.github.alexistrejo11.pimienta.module.account.user.core.domain.exceptions;

import io.github.alexistrejo11.pimienta.shared.exception.ErrorCode;
import io.github.alexistrejo11.pimienta.shared.exception.ResourceNotFoundException;
import java.util.Map;

public class UserNotFoundException extends ResourceNotFoundException {

  public UserNotFoundException(Long id) {
    super(
        ErrorCode.USER_NOT_FOUND,
        "The requested user was not found.",
        Map.of("userId", id),
        "User not found: id=" + id);
  }

  public UserNotFoundException(String email) {
    super(
        ErrorCode.USER_NOT_FOUND,
        "The requested user was not found.",
        Map.of("email", email),
        "User not found: email=" + email);
  }
}
