package io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web;

import io.github.alexistrejo11.pimienta.module.inventory.core.application.InventoryManagementUseCases;
import io.github.alexistrejo11.pimienta.module.inventory.core.domain.Inventory;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request.CreateInitialStockRequest;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.request.InventoryStockSearchRequest;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.dto.response.InventoryStockResponse;
import io.github.alexistrejo11.pimienta.module.inventory.infrastructure.adapter.inbound.web.mapper.InventoryStockWebMapper;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimit;
import io.github.alexistrejo11.pimienta.shared.ratelimit.RateLimitProfile;
import io.github.alexistrejo11.pimienta.shared.web.PageableRequest;
import io.github.alexistrejo11.pimienta.shared.web.PagedResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory/stock")
@RateLimit(profile = RateLimitProfile.STANDARD)
public class InventoryStockController {

  private final InventoryManagementUseCases inventoryManagementUseCases;

  public InventoryStockController(InventoryManagementUseCases inventoryManagementUseCases) {
    this.inventoryManagementUseCases = inventoryManagementUseCases;
  }

  @GetMapping
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public PagedResponse<InventoryStockResponse> searchStock(@ModelAttribute InventoryStockSearchRequest filter) {
    Page<Inventory> page =
        inventoryManagementUseCases.search(filter.toCriteria(), filter.toPageable());
    return PagedResponse.map(page, InventoryStockWebMapper::toResponse);
  }

  @GetMapping("/low-stock")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public PagedResponse<InventoryStockResponse> findLowStock(@ModelAttribute PageableRequest pageable) {
    Page<Inventory> page = inventoryManagementUseCases.findLowStock(pageable.toPageable());
    return PagedResponse.map(page, InventoryStockWebMapper::toResponse);
  }

  @GetMapping("/out-of-stock")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public PagedResponse<InventoryStockResponse> findOutOfStock(@ModelAttribute PageableRequest pageable) {
    Page<Inventory> page = inventoryManagementUseCases.findOutOfStock(pageable.toPageable());
    return PagedResponse.map(page, InventoryStockWebMapper::toResponse);
  }

  @GetMapping("/item/{itemId}")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public List<InventoryStockResponse> listByItem(@PathVariable Long itemId) {
    List<Inventory> rows = inventoryManagementUseCases.findByItemId(itemId);
    return rows.stream().map(InventoryStockWebMapper::toResponse).toList();
  }

  @GetMapping("/location/{locationId}")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public List<InventoryStockResponse> listByLocation(@PathVariable Long locationId) {
    List<Inventory> rows = inventoryManagementUseCases.findByLocationId(locationId);
    return rows.stream().map(InventoryStockWebMapper::toResponse).toList();
  }

  @GetMapping("/{id}")
  @RateLimit(profile = RateLimitProfile.READ_HEAVY)
  public InventoryStockResponse getStockById(@PathVariable Long id) {
    Inventory inv = inventoryManagementUseCases.getById(id);
    return InventoryStockWebMapper.toResponse(inv);
  }

  @PostMapping
  @RateLimit(profile = RateLimitProfile.SENSITIVE_OPERATIONS)
  @ResponseStatus(HttpStatus.CREATED)
  public InventoryStockResponse createInitialStock(@Valid @RequestBody CreateInitialStockRequest request) {
    Inventory created =
        inventoryManagementUseCases.createInitialStock(
            request.itemId(), request.locationId(), request.initialQuantity());
    return InventoryStockWebMapper.toResponse(created);
  }
}
