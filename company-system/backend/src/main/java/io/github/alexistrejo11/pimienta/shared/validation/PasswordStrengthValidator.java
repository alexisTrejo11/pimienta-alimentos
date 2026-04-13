package io.github.alexistrejo11.pimienta.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordStrength, String> {

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    if (password == null) {
      return false;
    }
    int len = password.length();
    if (len < 8 || len > 128) {
      return false;
    }
    boolean hasLetter = false;
    boolean hasDigit = false;
    for (int i = 0; i < len; i++) {
      char c = password.charAt(i);
      if (Character.isLetter(c)) {
        hasLetter = true;
      } else if (Character.isDigit(c)) {
        hasDigit = true;
      }
    }
    if (!hasLetter || !hasDigit) {
      return false;
    }
    long distinct = password.chars().distinct().count();
    return distinct >= 3;
  }
}
