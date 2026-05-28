package io.github.alexistrejo11.pimienta.module.files.core.domain.enums;

/**
 * Drives the S3 sub-folder and access-control rules for a stored file.
 *
 * <pre>
 * pimienta/sources/
 *   templates/   ← reusable Excel / Word / PDF templates
 *   company/     ← corporate assets: logos, policies, branding
 *   extras/      ← catch-all for files with no natural home
 *   resources/   ← module-specific; sub-keyed by module + optional entity
 * </pre>
 */
public enum FileCategory {
  TEMPLATE("templates"),
  COMPANY("company"),
  EXTRAS("extras"),
  RESOURCE("resources");

  private final String pathSegment;

  FileCategory(String pathSegment) {
    this.pathSegment = pathSegment;
  }

  public String getPathSegment() {
    return pathSegment;
  }
}
