package io.github.alexistrejo11.pimienta.module.files.core.application;

import io.github.alexistrejo11.pimienta.module.files.core.application.query.FileAssetSearchCriteria;
import io.github.alexistrejo11.pimienta.module.files.core.domain.FileAsset;
import io.github.alexistrejo11.pimienta.module.files.core.domain.exception.FileAssetNotFoundException;
import io.github.alexistrejo11.pimienta.module.files.core.port.input.FileAssetQueryUseCases;
import io.github.alexistrejo11.pimienta.module.files.core.port.output.FileAssetRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileAssetQueryUseCasesImpl implements FileAssetQueryUseCases {

  private final FileAssetRepository fileAssetRepository;

  public FileAssetQueryUseCasesImpl(FileAssetRepository fileAssetRepository) {
    this.fileAssetRepository = fileAssetRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<FileAsset> search(FileAssetSearchCriteria criteria, Pageable pageable) {
    return fileAssetRepository.search(criteria, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public FileAsset getById(UUID id) {
    return fileAssetRepository
        .findActiveById(id)
        .orElseThrow(() -> new FileAssetNotFoundException(id));
  }
}
