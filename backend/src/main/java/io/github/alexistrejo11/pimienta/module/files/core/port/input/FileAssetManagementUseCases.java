package io.github.alexistrejo11.pimienta.module.files.core.port.input;

import io.github.alexistrejo11.pimienta.module.files.core.application.command.UploadFileCommand;
import io.github.alexistrejo11.pimienta.module.files.core.domain.FileAsset;
import java.util.UUID;

public interface FileAssetManagementUseCases {

  FileAsset upload(UploadFileCommand command);

  void delete(UUID id);

  String generateDownloadUrl(UUID id);
}
