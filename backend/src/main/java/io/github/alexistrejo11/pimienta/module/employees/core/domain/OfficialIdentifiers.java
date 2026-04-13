package io.github.alexistrejo11.pimienta.module.employees.core.domain;

/**
 * Identificadores oficiales (CURP, RFC, NSS, CLABE, número interno).
 */
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
}
