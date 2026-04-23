package io.github.alexistrejo11.pimienta.module.employees.core.domain;

import lombok.Builder;

/**
 * Identificadores oficiales (CURP, RFC, NSS, CLABE, número interno).
 */
@Builder
public record OfficialIdentifiers(
    String curp,
    String rfc,
    String nss,
    String clabe,
    String employeeNumber) {

  public OfficialIdentifiers {
    curp = nullToEmpty(curp);
    rfc = nullToEmpty(rfc);
    nss = nullToEmpty(nss);
    clabe = nullToEmpty(clabe);
    employeeNumber = nullToEmpty(employeeNumber);
  }

  public static OfficialIdentifiers empty() {
    return new OfficialIdentifiers("", "", "", "", "");
  }

  private static String nullToEmpty(String s) {
    return s != null ? s.trim() : "";
  }

  public OfficialIdentifiers setCurp(String curp) {
    return new OfficialIdentifiers(nullToEmpty(curp), this.rfc, this.nss, this.clabe, this.employeeNumber);
  }

  public OfficialIdentifiers setRfc(String rfc) {
    return new OfficialIdentifiers(this.curp, nullToEmpty(rfc), this.nss, this.clabe, this.employeeNumber);
  }

  public OfficialIdentifiers setNss(String nss) {
    return new OfficialIdentifiers(this.curp, this.rfc, nullToEmpty(nss), this.clabe, this.employeeNumber);
  }

  public OfficialIdentifiers setClabe(String clabe) {
    return new OfficialIdentifiers(this.curp, this.rfc, this.nss, nullToEmpty(clabe), this.employeeNumber);
  }

  public OfficialIdentifiers setEmployeeNumber(String employeeNumber) {
    return new OfficialIdentifiers(this.curp, this.rfc, this.nss, this.clabe, nullToEmpty(employeeNumber));
  }
}
