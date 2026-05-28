package io.github.alexistrejo11.pimienta.module.files.core.application;

import io.github.alexistrejo11.pimienta.module.files.core.domain.enums.FileCategory;
import io.github.alexistrejo11.pimienta.shared.storage.StorageFileNaming;

/**
 * Builds the S3 object key for a new file asset.
 *
 * <pre>
 * {sourcesRoot}/templates/{filename}
 * {sourcesRoot}/company/{filename}
 * {sourcesRoot}/extras/{filename}
 * {sourcesRoot}/resources/{module}/{entityId}/{filename}   (entityId optional)
 * {sourcesRoot}/resources/{module}/{filename}              (no entity)
 * </pre>
 */
final class FileAssetKeyBuilder {

  private FileAssetKeyBuilder() {}

  static String buildKey(
      String sourcesRoot,
      FileCategory category,
      String module,
      Long entityId,
      String originalFilename,
      String displayStem) {

    String root = sourcesRoot == null ? "pimienta/sources" : sourcesRoot.strip().replaceAll("/+$", "");
    String filename = StorageFileNaming.buildFilename(displayStem, originalFilename);

    return switch (category) {
      case TEMPLATE, COMPANY, EXTRAS ->
          root + "/" + category.getPathSegment() + "/" + filename;
      case RESOURCE -> {
        String modSlug = StorageFileNaming.sanitize(module);
        if (entityId != null) {
          yield root + "/resources/" + modSlug + "/" + entityId + "/" + filename;
        }
        yield root + "/resources/" + modSlug + "/" + filename;
      }
    };
  }
}
