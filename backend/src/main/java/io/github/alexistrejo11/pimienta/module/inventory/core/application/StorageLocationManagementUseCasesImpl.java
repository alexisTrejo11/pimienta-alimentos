package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.StorageLocationSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.StorageLocationRepository;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.StorageLocationNotFoundException;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.StorageLocationNotEmptyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StorageLocationManagementUseCasesImpl implements StorageLocationManagementUseCases {

  private final StorageLocationRepository storageLocationRepository;

  public StorageLocationManagementUseCasesImpl(StorageLocationRepository storageLocationRepository) {
    this.storageLocationRepository = storageLocationRepository;
  }

  @Override
  public Page<StorageLocation> search(StorageLocationSearchCriteria criteria, Pageable pageable) {
    StorageLocationSearchCriteria effective =
        criteria != null ? criteria : StorageLocationSearchCriteria.empty();
    return storageLocationRepository.search(effective, pageable);
  }

  @Override
  public StorageLocation getById(Long id) {
    return storageLocationRepository
        .findById(id)
        .orElseThrow(() -> new StorageLocationNotFoundException(id));
  }

  @Override
  public List<StorageLocation> getChildren(Long parentId) {
    return storageLocationRepository.findByParentId(parentId);
  }

  @Override
  public List<LocationTreeNode> getTree() {
    List<StorageLocation> all = storageLocationRepository.findAllNonDeleted();
    Map<Long, List<StorageLocation>> byParent = new HashMap<>();
    for (StorageLocation loc : all) {
      Long key = loc.getParentId() != null ? loc.getParentId() : Long.MIN_VALUE;
      byParent.computeIfAbsent(key, k -> new ArrayList<>()).add(loc);
    }
    List<StorageLocation> roots =
        byParent.getOrDefault(Long.MIN_VALUE, new ArrayList<>());
    List<LocationTreeNode> nodes = new ArrayList<>();
    for (StorageLocation root : roots) {
      nodes.add(buildNode(root, byParent));
    }
    return nodes;
  }

  private LocationTreeNode buildNode(StorageLocation loc, Map<Long, List<StorageLocation>> byParent) {
    List<StorageLocation> children = byParent.getOrDefault(loc.getId(), new ArrayList<>());
    List<LocationTreeNode> childNodes = new ArrayList<>();
    for (StorageLocation ch : children) {
      childNodes.add(buildNode(ch, byParent));
    }
    return new LocationTreeNode(
        loc.getId(),
        loc.getCode(),
        loc.getName(),
        loc.getType(),
        loc.getStatus(),
        loc.getParentId(),
        loc.getMaxCapacity(),
        loc.getOccupiedCapacity(),
        childNodes);
  }

  @Override
  public StorageLocation create(StorageLocation location) {
    return storageLocationRepository.save(location);
  }

  @Override
  public StorageLocation update(Long id, StorageLocation merged) {
    StorageLocation existing = getById(id);
    existing.setCode(merged.getCode());
    existing.setName(merged.getName());
    existing.setDescription(merged.getDescription());
    existing.setType(merged.getType());
    existing.setParentId(merged.getParentId());
    existing.setMaxCapacity(merged.getMaxCapacity());
    existing.setOccupiedCapacity(merged.getOccupiedCapacity());
    existing.setStatus(merged.getStatus());
    return storageLocationRepository.save(existing);
  }

  @Override
  public StorageLocation block(Long id) {
    StorageLocation loc = getById(id);
    loc.block();
    return storageLocationRepository.save(loc);
  }

  @Override
  public StorageLocation unblock(Long id) {
    StorageLocation loc = getById(id);
    loc.unblock();
    return storageLocationRepository.save(loc);
  }

  @Override
  public void delete(Long id) {
    StorageLocation loc = getById(id);
    if (loc.getOccupiedCapacity() > 0) {
      throw new StorageLocationNotEmptyException(id);
    }
    if (storageLocationRepository.countInventoryRowsByLocationId(id) > 0) {
      throw new StorageLocationNotEmptyException(id);
    }
    loc.delete();
    storageLocationRepository.save(loc);
  }
}
