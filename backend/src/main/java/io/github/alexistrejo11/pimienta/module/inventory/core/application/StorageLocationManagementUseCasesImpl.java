package io.github.alexistrejo11.pimienta.module.inventory.core.application;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.query.StorageLocationSearchCriteria;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.StorageLocation;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.StorageLocationNotEmptyException;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.exception.StorageLocationNotFoundException;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.StorageLocationRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StorageLocationManagementUseCasesImpl implements StorageLocationManagementUseCases {

  private static final Logger log = LoggerFactory.getLogger(StorageLocationManagementUseCasesImpl.class);

  private final StorageLocationRepository storageLocationRepository;

  public StorageLocationManagementUseCasesImpl(StorageLocationRepository storageLocationRepository) {
    this.storageLocationRepository = storageLocationRepository;
  }

  @Override
  public Page<StorageLocation> search(StorageLocationSearchCriteria criteria, Pageable pageable) {
    StorageLocationSearchCriteria effective =
        criteria != null ? criteria : StorageLocationSearchCriteria.empty();

    log.debug(
        "search storage locations query start page={} size={} type={} status={}",
        pageable != null ? pageable.getPageNumber() : null,
        pageable != null ? pageable.getPageSize() : null,
        effective.type(),
        effective.status());

    Page<StorageLocation> page = storageLocationRepository.search(effective, pageable);

    log.debug(
        "search storage locations query complete totalElements={} numberOfElements={}",
        page.getTotalElements(),
        page.getNumberOfElements());
    return page;
  }

  @Override
  public StorageLocation getById(Long id) {
    log.debug("get storage location by id query start locationId={}", id);

    StorageLocation loc =
        storageLocationRepository
            .findById(id)
            .orElseThrow(() -> new StorageLocationNotFoundException(id));

    log.debug("get storage location by id query complete locationId={}", loc.getId());
    return loc;
  }

  @Override
  public List<StorageLocation> getChildren(Long parentId) {
    log.debug("get storage location children query start parentId={}", parentId);

    List<StorageLocation> children = storageLocationRepository.findByParentId(parentId);

    log.debug(
        "get storage location children query complete parentId={} childCount={}",
        parentId,
        children.size());
    return children;
  }

  @Override
  public List<LocationTreeNode> getTree() {
    log.debug("get storage location tree query start");

    List<StorageLocation> all = storageLocationRepository.findAllNonDeleted();
    Map<Long, List<StorageLocation>> byParent = new HashMap<>();
    for (StorageLocation loc : all) {
      Long key = loc.getParentId() != null ? loc.getParentId() : Long.MIN_VALUE;
      byParent.computeIfAbsent(key, k -> new ArrayList<>()).add(loc);
    }
    List<StorageLocation> roots = byParent.getOrDefault(Long.MIN_VALUE, new ArrayList<>());
    List<LocationTreeNode> nodes = new ArrayList<>();
    for (StorageLocation root : roots) {
      nodes.add(buildNode(root, byParent));
    }

    log.debug(
        "get storage location tree query complete totalLocations={} rootCount={}",
        all.size(),
        nodes.size());
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
    log.info(
        "create storage location start code={} type={} status={} parentId={}",
        location.getCode(),
        location.getType(),
        location.getStatus(),
        location.getParentId());

    StorageLocation saved = storageLocationRepository.save(location);

    log.info("create storage location complete locationId={} code={}", saved.getId(), saved.getCode());
    return saved;
  }

  @Override
  public StorageLocation update(Long id, StorageLocation merged) {
    log.info("update storage location start locationId={} code={}", id, merged.getCode());

    StorageLocation existing = getById(id);
    existing.setCode(merged.getCode());
    existing.setName(merged.getName());
    existing.setDescription(merged.getDescription());
    existing.setType(merged.getType());
    existing.setParentId(merged.getParentId());
    existing.setMaxCapacity(merged.getMaxCapacity());
    existing.setOccupiedCapacity(merged.getOccupiedCapacity());
    existing.setStatus(merged.getStatus());

    StorageLocation saved = storageLocationRepository.save(existing);

    log.info("update storage location complete locationId={}", saved.getId());
    return saved;
  }

  @Override
  public StorageLocation block(Long id) {
    log.info("block storage location start locationId={}", id);

    StorageLocation loc = getById(id);
    loc.block();

    StorageLocation saved = storageLocationRepository.save(loc);
    log.info("block storage location complete locationId={}", saved.getId());
    return saved;
  }

  @Override
  public StorageLocation unblock(Long id) {
    log.info("unblock storage location start locationId={}", id);

    StorageLocation loc = getById(id);
    loc.unblock();

    StorageLocation saved = storageLocationRepository.save(loc);
    log.info("unblock storage location complete locationId={}", saved.getId());
    return saved;
  }

  @Override
  public void delete(Long id) {
    log.info("delete storage location start locationId={}", id);

    StorageLocation loc = getById(id);
    if (loc.getOccupiedCapacity() > 0) {
      throw new StorageLocationNotEmptyException(id);
    }
    if (storageLocationRepository.countInventoryRowsByLocationId(id) > 0) {
      throw new StorageLocationNotEmptyException(id);
    }
    loc.delete();

    storageLocationRepository.save(loc);
    log.info("delete storage location complete locationId={}", id);
  }
}
