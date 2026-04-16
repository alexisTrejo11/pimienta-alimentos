package io.github.alexistrejo11.pimienta.module.account.user.core.domain.entities;

import java.time.LocalDate;
import io.github.alexistrejo11.pimienta.module.account.user.core.domain.enums.Gender;

/**
 * Parameter object for {@link User#register(UserRegisterParams)}.
 *
 * <p>
 * Use the nested {@link Builder} to construct an instance before calling the
 * factory.
 *
 * <pre>{@code
 * User.register(
 *     UserRegisterParams.builder()
 *         .email(command.email())
 *         .passwordHash(hash)
 *         // ...
 *         .build());
 * }</pre>
 */
public record UserRegisterParams(
    String email,
    String passwordHash,
    String firstName,
    String lastName,
    Gender gender,
    String phone,
    LocalDate dateOfBirth) {

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String phone;
    private LocalDate dateOfBirth;

    private Builder() {
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder passwordHash(String passwordHash) {
      this.passwordHash = passwordHash;
      return this;
    }

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder gender(Gender gender) {
      this.gender = gender;
      return this;
    }

    public Builder phone(String phone) {
      this.phone = phone;
      return this;
    }

    public Builder dateOfBirth(LocalDate dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
      return this;
    }

    public UserRegisterParams build() {
      return new UserRegisterParams(email, passwordHash, firstName, lastName, gender, phone, dateOfBirth);
    }
  }
}
