package io.github.alexistrejo11.pimienta.module.employees.core.domain.valueobject;

import java.time.LocalDate;

import lombok.Builder;

/**
 * Datos personales del empleado (value object).
 */
@Builder
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

  public PersonalProfile setName(String name) {
    return new PersonalProfile(
        name,
        this.email,
        this.phone,
        this.address,
        this.birthDate,
        this.nationality);
  }

  public PersonalProfile setEmail(String email) {
    return PersonalProfile.builder()
        .name(this.name)
        .email(email)
        .phone(this.phone)
        .address(this.address)
        .birthDate(this.birthDate)
        .nationality(this.nationality)
        .build();
  }

  public PersonalProfile setPhone(String phone) {
    return PersonalProfile.builder()
        .name(this.name)
        .email(this.email)
        .phone(phone)
        .address(this.address)
        .birthDate(this.birthDate)
        .nationality(this.nationality)
        .build();
  }

  public PersonalProfile setAddress(String address) {
    return PersonalProfile.builder()
        .name(this.name)
        .email(this.email)
        .phone(this.phone)
        .address(blankToEmpty(address))
        .birthDate(this.birthDate)
        .nationality(this.nationality)
        .build();
  }

  public PersonalProfile setBirthDate(LocalDate birthDate) {
    return PersonalProfile.builder()
        .name(this.name)
        .email(this.email)
        .phone(this.phone)
        .address(this.address)
        .birthDate(birthDate)
        .nationality(this.nationality)
        .build();
  }

  public PersonalProfile setNationality(String nationality) {
    return PersonalProfile.builder()
        .name(this.name)
        .email(this.email)
        .phone(this.phone)
        .address(this.address)
        .birthDate(this.birthDate)
        .nationality(nationality)
        .build();
  }

  private static String blankToEmpty(String s) {
    return s != null ? s.trim() : "";
  }
}
