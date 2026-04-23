package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class EmployeePersonalEmbeddable {

  @Column(name = "full_name", nullable = false, length = 200)
  private String fullName;

  @Column(length = 320)
  private String email;

  @Column(length = 40)
  private String phone;

  @Column(length = 500)
  private String address;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(length = 80)
  private String nationality;

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }
}
