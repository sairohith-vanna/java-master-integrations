package com.vanna.mastery.integration.controllers;

import com.vanna.mastery.integration.models.ProductItem;
import com.vanna.mastery.integration.services.InventoryManager;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/inventory")
public class InventoryController {

    private final InventoryManager inventoryService;

    public InventoryController(InventoryManager inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/")
    @Operation(summary = "Get all the product items from inventory")
    public ResponseEntity<List<ProductItem>> getProducts() {
        var allProducts = inventoryService.getAllInventoryProductItems();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping("/")
    @Operation(summary = "Add a new product item to the inventory")
    public ResponseEntity<ProductItem> addProduct(@RequestBody ProductItem newProductItem) {
        var addedProduct = inventoryService.addProductToInventory(newProductItem);
        return ResponseEntity.ok(addedProduct);
    }

    @GetMapping("{productId}")
    @Operation(summary = "Get a specific product item from inventory")
    public ResponseEntity<ProductItem> getProducts(@PathVariable("productId") UUID productId) {
        var productItem = inventoryService.getProductItemDetails(productId);
        return productItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
