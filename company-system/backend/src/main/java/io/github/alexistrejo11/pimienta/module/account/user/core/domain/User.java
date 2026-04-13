package io.github.alexistrejo11.pimienta.module.account.user.core.domain;

import io.github.alexistrejo11.pimienta.shared.BaseDomain;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class User extends BaseDomain<Long> {

  private String email;
  private String passwordHash;
  private String firstName;
  private String lastName;
  private Gender gender;
  private String phone;
  private LocalDate dateOfBirth;
  private boolean banned;
  private String bannedReason;
  private LocalDateTime bannedAt;
  private List<Role> roles;

  public String getEmail() {
    return email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Gender getGender() {
    return gender;
  }

  public String getPhone() {
    return phone;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public boolean isBanned() {
    return banned;
  }

  public String getBannedReason() {
    return bannedReason;
  }

  public LocalDateTime getBannedAt() {
    return bannedAt;
  }

  public List<Role> getRoles() {
    return List.copyOf(roles);
  }

  private User() {
    super();
  }

  /**
   * New registration: no roles until an administrator assigns them.
   */
  public static User register(
      String email,
      String passwordHash,
      String firstName,
      String lastName,
      Gender gender,
      String phone,
      LocalDate dateOfBirth) {
    Objects.requireNonNull(email, "email");
    Objects.requireNonNull(passwordHash, "passwordHash");
    Objects.requireNonNull(gender, "gender");
    Objects.requireNonNull(dateOfBirth, "dateOfBirth");
    if (email.isBlank()) {
      throw new IllegalArgumentException("email must not be blank");
    }
    if (firstName == null || firstName.isBlank()) {
      throw new IllegalArgumentException("firstName must not be blank");
    }
    if (lastName == null || lastName.isBlank()) {
      throw new IllegalArgumentException("lastName must not be blank");
    }
    if (phone == null || phone.isBlank()) {
      throw new IllegalArgumentException("phone must not be blank");
    }
    var now = LocalDateTime.now();
    var u = new User();
    u.email = email.trim().toLowerCase();
    u.passwordHash = passwordHash;
    u.firstName = firstName.trim();
    u.lastName = lastName.trim();
    u.gender = gender;
    u.phone = phone.trim();
    u.dateOfBirth = dateOfBirth;
    u.banned = false;
    u.bannedReason = null;
    u.bannedAt = null;
    u.roles = new ArrayList<>();
    u.createdAt = now;
    u.updatedAt = now;
    u.deletedAt = null;
    u.version = 0L;
    u.id = null;
    return u;
  }

  public static User reconstruct(
      Long id,
      String email,
      String passwordHash,
      String firstName,
      String lastName,
      Gender gender,
      String phone,
      LocalDate dateOfBirth,
      boolean banned,
      String bannedReason,
      LocalDateTime bannedAt,
      List<Role> roles,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime deletedAt,
      Long version) {
    var u = new User();
    u.id = id != null ? id : 0L;
    u.email = email != null ? email : "";
    u.passwordHash = passwordHash != null ? passwordHash : "";
    u.firstName = firstName != null ? firstName : "";
    u.lastName = lastName != null ? lastName : "";
    u.gender = gender;
    u.phone = phone != null ? phone : "";
    u.dateOfBirth = dateOfBirth;
    u.banned = banned;
    u.bannedReason = bannedReason;
    u.bannedAt = bannedAt;
    u.roles = roles != null ? new ArrayList<>(roles) : new ArrayList<>();
    u.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    u.updatedAt = updatedAt != null ? updatedAt : u.createdAt;
    u.deletedAt = deletedAt;
    u.version = version != null ? version : 0L;
    return u;
  }

  public void assignPasswordHash(String newHash) {
    this.passwordHash = Objects.requireNonNull(newHash);
    touch();
  }

  public void ban(String reason) {
    this.banned = true;
    this.bannedReason = reason;
    this.bannedAt = LocalDateTime.now();
    touch();
  }

  public void unban() {
    this.banned = false;
    this.bannedReason = null;
    this.bannedAt = null;
    touch();
  }

  /** Adds roles not already present; merges permissions via {@link Role#getPermissions()}. */
  public void addRoles(List<Role> incoming) {
    if (incoming == null || incoming.isEmpty()) {
      return;
    }
    var set = EnumSet.noneOf(Role.class);
    set.addAll(roles);
    for (Role r : incoming) {
      if (r != null) {
        set.add(r);
      }
    }
    this.roles = new ArrayList<>(set);
    touch();
  }

  public void replaceRoles(List<Role> newRoles) {
    this.roles = newRoles != null ? new ArrayList<>(newRoles) : new ArrayList<>();
    touch();
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
    touch();
  }

  private void touch() {
    this.updatedAt = LocalDateTime.now();
    this.version = this.version != null ? this.version + 1 : 1L;
  }
}
