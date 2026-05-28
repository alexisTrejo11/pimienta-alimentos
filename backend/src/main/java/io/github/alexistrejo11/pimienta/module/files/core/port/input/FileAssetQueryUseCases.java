package io.github.alexistrejo11.pimienta.module.files.core.port.input;

import io.github.alexistrejo11.pimienta.module.files.core.application.query.FileAssetSearchCriteria;
import io.github.alexistrejo11.pimienta.module.files.core.domain.FileAsset;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FileAssetQueryUseCases {

  Page<FileAsset> search(FileAssetSearchCriteria criteria, Pageable pageable);

  FileAsset getById(UUID id);
}
