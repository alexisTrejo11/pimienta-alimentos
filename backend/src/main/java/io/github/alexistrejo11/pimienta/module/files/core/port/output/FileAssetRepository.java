package io.github.alexistrejo11.pimienta.module.files.core.port.output;

import io.github.alexistrejo11.pimienta.module.files.core.application.query.FileAssetSearchCriteria;
import io.github.alexistrejo11.pimienta.module.files.core.domain.FileAsset;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FileAssetRepository {

  FileAsset save(FileAsset fileAsset);

  Optional<FileAsset> findById(UUID id);

  Optional<FileAsset> findActiveById(UUID id);

  Page<FileAsset> search(FileAssetSearchCriteria criteria, Pageable pageable);

  void softDelete(UUID id);
}
