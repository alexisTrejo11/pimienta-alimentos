package io.github.alexistrejo11.pimienta.module.notification.core.application.dto;

/**
 * Contact snapshot for an administrator resolved from persistence (email and/or SMS).
 */
public record AdminRecipient(Long userId, String email, String phone, String displayName) {

  public AdminRecipient {
    if (userId == null) {
      throw new IllegalArgumentException("userId is required");
    }
  }

  public boolean hasEmail() {
    return email != null && !email.isBlank();
  }

  public boolean hasPhone() {
    return phone != null && !phone.isBlank();
  }
}
