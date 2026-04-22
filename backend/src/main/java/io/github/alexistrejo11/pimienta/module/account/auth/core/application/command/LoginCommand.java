package io.github.alexistrejo11.pimienta.module.account.auth.core.application.command;

public record LoginCommand(String email, String password) {

  /** Safe for logs: never includes the password. */
  public String describeForLog() {
    String e = email != null ? email.trim().toLowerCase() : "";
    return "LoginCommand(email=" + e + ", password=***)";
  }
}
