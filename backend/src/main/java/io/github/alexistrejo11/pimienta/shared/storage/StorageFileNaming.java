package io.github.alexistrejo11.pimienta.shared.storage;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Shared helpers for deriving safe, collision-resistant S3 filename stems.
 * Used by both the employee module and the general-purpose file module.
 */
public final class StorageFileNaming {

  private StorageFileNaming() {}

  /**
   * Returns the lowercase file extension without the leading dot (e.g. {@code "pdf"}).
   * Falls back to {@code "bin"} when the filename has no dot.
   */
  public static String extension(String originalFilename) {
    if (originalFilename == null || originalFilename.isBlank()) {
      return "bin";
    }
    int dot = originalFilename.lastIndexOf('.');
    if (dot < 0 || dot == originalFilename.length() - 1) {
      return "bin";
    }
    return originalFilename.substring(dot + 1).toLowerCase();
  }

  /**
   * Strips characters that are unsafe in S3 keys or URLs and collapses repeated underscores.
   * Returns {@code "unknown"} for blank input.
   */
  public static String sanitize(String segment) {
    if (segment == null || segment.isBlank()) {
      return "unknown";
    }
    return segment.strip()
        .replaceAll("[^a-zA-Z0-9._-]", "_")
        .replaceAll("_{2,}", "_");
  }

  /**
   * Builds a unique suffix: {@code {millis}_{random4digits}}.
   * Append to a stem to guarantee no overwrite collisions.
   */
  public static String uniqueSuffix() {
    return System.currentTimeMillis() + "_" + ThreadLocalRandom.current().nextInt(10_000);
  }

  /**
   * Combines {@link #sanitize(String)} + {@link #uniqueSuffix()} + extension into a filename.
   * E.g. {@code "invoice_template_1716916800000_3742.xlsx"}.
   */
  public static String buildFilename(String displayStem, String originalFilename) {
    return sanitize(displayStem) + "_" + uniqueSuffix() + "." + extension(originalFilename);
  }
}
