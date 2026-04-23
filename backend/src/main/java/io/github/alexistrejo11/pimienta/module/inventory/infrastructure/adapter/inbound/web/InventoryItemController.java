package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.inventory.core.domain.Item;
import io.github.alexistrejo11.pimienta.module.inventory.core.port.input.ItemManagementUseCases;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request.ItemCreateRequest;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request.ItemSearchRequest;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request.ItemUpdateRequest;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.response.ItemResponse;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.mapper.InventoryItemWebMapper;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimit;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimitProfile;
import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory/items")
@RateLimit(profile = RateLimitProfile.STANDARD)
public class InventoryItemController {

  private final ItemManagementUseCases itemManagementUseCases;

  public InventoryItemController(ItemManagementUseCases itemManagementUseCases) {
    this.itemManagementUseCases = itemManagementUseCases;
  }

  @GetMapping
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public PagedResponse<ItemResponse> searchItems(@ModelAttribute ItemSearchRequest filter) {
    Page<Item> page = itemManagementUseCases.search(filter.toCriteria(), filter.toPageable());
    return PagedResponse.map(page, InventoryItemWebMapper::toResponse);
  }

  @GetMapping("/lookup")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public ItemResponse getItemBySkuOrBarcode(@RequestParam("q") @NotBlank String skuOrBarcode) {
    Item item = itemManagementUseCases.getBySkuOrBarcode(skuOrBarcode.trim());
    return InventoryItemWebMapper.toResponse(item);
  }

  @GetMapping("/{id}")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public ItemResponse getItemById(@PathVariable Long id) {
    Item item = itemManagementUseCases.getById(id);
    return InventoryItemWebMapper.toResponse(item);
  }

  @PostMapping
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  @ResponseStatus(HttpStatus.CREATED)
  public ItemResponse createItem(@Valid @RequestBody ItemCreateRequest request) {
    Item created = itemManagementUseCases.create(InventoryItemWebMapper.toDomain(request));
    return InventoryItemWebMapper.toResponse(created);
  }

  @PutMapping("/{id}")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public ItemResponse updateItem(@PathVariable Long id, @Valid @RequestBody ItemUpdateRequest request) {
    Item merged = InventoryItemWebMapper.toMergedDomain(id, request);
    Item updated = itemManagementUseCases.update(id, merged);
    return InventoryItemWebMapper.toResponse(updated);
  }

  @PutMapping("/{id}/discontinue")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public ItemResponse discontinueItem(@PathVariable Long id) {
    Item item = itemManagementUseCases.discontinue(id);
    return InventoryItemWebMapper.toResponse(item);
  }

  @PutMapping("/{id}/activate")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public ItemResponse activateItem(@PathVariable Long id) {
    Item item = itemManagementUseCases.activate(id);
    return InventoryItemWebMapper.toResponse(item);
  }

  @DeleteMapping("/{id}")
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
    itemManagementUseCases.delete(id);
    return ResponseEntity.noContent().build();
  }
}
