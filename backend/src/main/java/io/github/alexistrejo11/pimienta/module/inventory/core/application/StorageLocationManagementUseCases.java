package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.StorageLocationSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StorageLocationManagementUseCases {

  Page<StorageLocation> search(StorageLocationSearchCriteria criteria, Pageable pageable);

  StorageLocation getById(Long id);

  List<StorageLocation> getChildren(Long parentId);

  List<LocationTreeNode> getTree();

  StorageLocation create(StorageLocation location);

  StorageLocation update(Long id, StorageLocation merged);

  StorageLocation block(Long id);

  StorageLocation unblock(Long id);

  void delete(Long id);
}
