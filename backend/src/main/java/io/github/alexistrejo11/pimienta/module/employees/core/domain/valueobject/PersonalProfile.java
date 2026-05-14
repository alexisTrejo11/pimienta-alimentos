package io.github.alexistrejo11.pimienta.module.employees.core.domain.valueobject;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record PersonalProfile(
    String firstName,
    String lastName,
    String photoUrl,
    String email,
    String phone,
    String address,
    LocalDate birthDate,
    String nationality) {

  public PersonalProfile {
    firstName = blankToEmpty(firstName);
    lastName = blankToEmpty(lastName);
    email = blankToEmpty(email);
    phone = blankToEmpty(phone);
    address = blankToEmpty(address);
    nationality = nationality != null && !nationality.isBlank() ? nationality.trim() : "Mexicana";
  }

  public static PersonalProfile empty() {
    return PersonalProfile.builder()
        .firstName("")
        .lastName("")
        .photoUrl("")
        .email("")
        .phone("")
        .address("")
        .birthDate(null)
        .nationality("Mexicana")
        .build();
  }

  public PersonalProfile setFirstName(String firstName) {
    return PersonalProfile.builder()
        .firstName(firstName)
        .lastName(this.lastName)
        .photoUrl(this.photoUrl)
        .email(this.email)
        .phone(this.phone)
        .address(this.address)
        .birthDate(this.birthDate)
        .nationality(this.nationality)
        .build();
  }

  public PersonalProfile setLastName(String lastName) {
    return PersonalProfile.builder()
        .firstName(this.firstName)
        .lastName(lastName)
        .photoUrl(this.photoUrl)
        .email(this.email)
        .phone(this.phone)
        .address(this.address)
        .birthDate(this.birthDate)
        .nationality(this.nationality)
        .build();
  }

  public PersonalProfile setEmail(String email) {
    return PersonalProfile.builder()
        .firstName(this.firstName)
        .lastName(this.lastName)
        .photoUrl(this.photoUrl)
        .email(email)
        .phone(this.phone)
        .address(this.address)
        .birthDate(this.birthDate)
        .nationality(this.nationality)
        .build();
  }

  public PersonalProfile setPhone(String phone) {
    return PersonalProfile.builder()
        .firstName(this.firstName)
        .lastName(this.lastName)
        .photoUrl(this.photoUrl)
        .email(this.email)
        .phone(phone)
        .address(this.address)
        .birthDate(this.birthDate)
        .nationality(this.nationality)
        .build();
  }

  public PersonalProfile setAddress(String address) {
    return PersonalProfile.builder()
        .firstName(this.firstName)
        .lastName(this.lastName)
        .photoUrl(this.photoUrl)
        .email(this.email)
        .phone(this.phone)
        .address(blankToEmpty(address))
        .birthDate(this.birthDate)
        .nationality(this.nationality)
        .build();
  }

  public PersonalProfile setBirthDate(LocalDate birthDate) {
    return PersonalProfile.builder()
        .firstName(this.firstName)
        .lastName(this.lastName)
        .photoUrl(this.photoUrl)
        .email(this.email)
        .phone(this.phone)
        .address(this.address)
        .birthDate(birthDate)
        .nationality(this.nationality)
        .build();
  }

  public PersonalProfile setNationality(String nationality) {
    return PersonalProfile.builder()
        .firstName(this.firstName)
        .lastName(this.lastName)
        .photoUrl(this.photoUrl)
        .email(this.email)
        .phone(this.phone)
        .address(this.address)
        .birthDate(this.birthDate)
        .nationality(nationality)
        .build();
  }

  public PersonalProfile setPhotoUrl(String photoUrl) {
    return PersonalProfile.builder()
        .firstName(this.firstName)
        .lastName(this.lastName)
        .photoUrl(photoUrl)
        .email(this.email)
        .phone(this.phone)
        .address(this.address)
        .birthDate(this.birthDate)
        .nationality(this.nationality)
        .build();
  }

  private static String blankToEmpty(String s) {
    return s != null ? s.trim() : "";
  }
}
