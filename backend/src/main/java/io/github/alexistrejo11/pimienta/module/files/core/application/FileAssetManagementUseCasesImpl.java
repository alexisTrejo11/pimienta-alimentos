package io.github.alexistrejo11.pimienta.module.files.core.application;

import io.github.alexistrejo11.pimienta.module.files.core.application.command.UploadFileCommand;
import io.github.alexistrejo11.pimienta.module.files.core.domain.FileAsset;
import io.github.alexistrejo11.pimienta.module.files.core.domain.enums.FileCategory;
import io.github.alexistrejo11.pimienta.module.files.core.domain.exception.FileAssetNotFoundException;
import io.github.alexistrejo11.pimienta.module.files.core.port.input.FileAssetManagementUseCases;
import io.github.alexistrejo11.pimienta.module.files.core.port.output.FileAssetRepository;
import io.github.alexistrejo11.pimienta.module.files.core.port.output.FileStoragePort;
import io.github.alexistrejo11.pimienta.shared.exception.BusinessValidationException;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileAssetManagementUseCasesImpl implements FileAssetManagementUseCases {

  private static final Logger log = LoggerFactory.getLogger(FileAssetManagementUseCasesImpl.class);

  private final FileAssetRepository fileAssetRepository;
  private final FileStoragePort fileStoragePort;
  private final String sourcesRoot;

  public FileAssetManagementUseCasesImpl(
      FileAssetRepository fileAssetRepository,
      FileStoragePort fileStoragePort,
      @Value("${aws.s3.sources-root:pimienta/sources}") String sourcesRoot) {
    this.fileAssetRepository = fileAssetRepository;
    this.fileStoragePort = fileStoragePort;
    this.sourcesRoot = sourcesRoot;
  }

  @Override
  @Transactional
  public FileAsset upload(UploadFileCommand command) {
    log.info(
        "file upload start category={} module={} entityId={} uploader={}",
        command.category(),
        command.module(),
        command.entityId(),
        command.uploadedByUserId());

    validateUploadCommand(command);

    String originalFilename =
        command.file().getOriginalFilename() != null ? command.file().getOriginalFilename() : "file";
    String displayStem = io.github.alexistrejo11.pimienta.shared.storage.StorageFileNaming.sanitize(
        stripExtension(originalFilename));

    String s3Key = FileAssetKeyBuilder.buildKey(
        sourcesRoot,
        command.category(),
        command.module(),
        command.entityId(),
        originalFilename,
        displayStem);

    fileStoragePort.store(command.file(), s3Key);
    log.debug("file stored s3Key={}", s3Key);

    FileAsset asset = FileAsset.builder()
        .category(command.category())
        .module(command.module())
        .entityType(command.entityType())
        .entityId(command.entityId())
        .s3Key(s3Key)
        .originalName(originalFilename)
        .contentType(resolveContentType(command.file()))
        .fileSizeBytes(command.file().getSize() > 0 ? command.file().getSize() : null)
        .description(command.description())
        .uploadedByUserId(command.uploadedByUserId())
        .register();

    FileAsset saved = fileAssetRepository.save(asset);
    log.info("file asset saved id={} s3Key={}", saved.getId(), saved.getS3Key());
    return saved;
  }

  @Override
  @Transactional
  public void delete(UUID id) {
    log.info("file delete start id={}", id);
    FileAsset asset = fileAssetRepository
        .findActiveById(id)
        .orElseThrow(() -> new FileAssetNotFoundException(id));
    fileStoragePort.delete(asset.getS3Key());
    fileAssetRepository.softDelete(id);
    log.info("file delete complete id={}", id);
  }

  @Override
  @Transactional(readOnly = true)
  public String generateDownloadUrl(UUID id) {
    FileAsset asset = fileAssetRepository
        .findActiveById(id)
        .orElseThrow(() -> new FileAssetNotFoundException(id));
    return fileStoragePort.presignedDownloadUrl(asset.getS3Key());
  }

  private void validateUploadCommand(UploadFileCommand command) {
    if (command.file() == null || command.file().isEmpty()) {
      throw new BusinessValidationException(
          "File must not be empty.", Map.of("field", "file"), "upload: empty file");
    }
    if (command.category() == null) {
      throw new BusinessValidationException(
          "Category is required.", Map.of("field", "category"), "upload: missing category");
    }
    if (command.category() == FileCategory.RESOURCE
        && (command.module() == null || command.module().isBlank())) {
      throw new BusinessValidationException(
          "Module is required for RESOURCE category.",
          Map.of("field", "module"),
          "upload: missing module for RESOURCE");
    }
    long maxBytes = 50L * 1024 * 1024;
    if (command.file().getSize() > maxBytes) {
      throw new BusinessValidationException(
          "File size exceeds maximum of 50 MB.",
          Map.of("maxBytes", maxBytes, "actualBytes", command.file().getSize()),
          "upload: file too large size=" + command.file().getSize());
    }
  }

  private static String resolveContentType(org.springframework.web.multipart.MultipartFile file) {
    if (file.getContentType() != null && !file.getContentType().isBlank()) {
      return file.getContentType();
    }
    return "application/octet-stream";
  }

  private static String stripExtension(String filename) {
    int dot = filename.lastIndexOf('.');
    return dot > 0 ? filename.substring(0, dot) : filename;
  }
}
