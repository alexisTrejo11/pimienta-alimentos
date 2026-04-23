package io.github.alexistrejo11.pimienta.module.employees.adapter.outbound.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EmployeeOfficialIdsEmbeddable {

  @Column(length = 18)
  private String curp;

  @Column(length = 13)
  private String rfc;

  @Column(length = 11)
  private String nss;

  @Column(length = 18)
  private String clabe;

  @Column(name = "employee_number", length = 32)
  private String employeeNumber;

  public String getCurp() {
    return curp;
  }

  public void setCurp(String curp) {
    this.curp = curp;
  }

  public String getRfc() {
    return rfc;
  }

  public void setRfc(String rfc) {
    this.rfc = rfc;
  }

  public String getNss() {
    return nss;
  }

  public void setNss(String nss) {
    this.nss = nss;
  }

  public String getClabe() {
    return clabe;
  }

  public void setClabe(String clabe) {
    this.clabe = clabe;
  }

  public String getEmployeeNumber() {
    return employeeNumber;
  }

  public void setEmployeeNumber(String employeeNumber) {
    this.employeeNumber = employeeNumber;
  }
}
