package io.github.alexistrejo11.pimienta.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Moderate password rules: length 8–128, at least one letter and one digit, not fewer than three
 * distinct characters (avoids {@code aaaaaaa1}).
 */
@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordStrength {

  String message() default "Password must be 8–128 characters and include letters and numbers.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
