package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import java.time.LocalDate;

/**
 * Datos personales del empleado (value object).
 */
public record PersonalProfile(
    String name,
    String email,
    String phone,
    String address,
    LocalDate birthDate,
    String nationality) {

  public PersonalProfile {
    name = blankToEmpty(name);
    email = blankToEmpty(email);
    phone = blankToEmpty(phone);
    address = blankToEmpty(address);
    nationality = nationality != null && !nationality.isBlank() ? nationality.trim() : "Mexicana";
  }

  public static PersonalProfile empty() {
    return new PersonalProfile("", "", "", "", null, "Mexicana");
  }

  public PersonalProfile withName(String name) {
    return new PersonalProfile(name, email, phone, address, birthDate, nationality);
  }

  public PersonalProfile withEmail(String email) {
    return new PersonalProfile(name, email, phone, address, birthDate, nationality);
  }

  public PersonalProfile withPhone(String phone) {
    return new PersonalProfile(name, email, phone, address, birthDate, nationality);
  }

  public PersonalProfile withAddress(String address) {
    return new PersonalProfile(name, email, phone, address, birthDate, nationality);
  }

  public PersonalProfile withBirthDate(LocalDate birthDate) {
    return new PersonalProfile(name, email, phone, address, birthDate, nationality);
  }

  public PersonalProfile withNationality(String nationality) {
    return new PersonalProfile(name, email, phone, address, birthDate, nationality);
  }

  private static String blankToEmpty(String s) {
    return s != null ? s.trim() : "";
  }
}
