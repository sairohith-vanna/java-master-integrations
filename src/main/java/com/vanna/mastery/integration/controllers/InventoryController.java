package com.vanna.mastery.integration.controllers;

import com.vanna.mastery.integration.models.ProductItem;
import com.vanna.mastery.integration.services.InventoryManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @GetMapping
    @Operation(summary = "Get all the product items from inventory")
    @ApiResponse(
            description = "Responds with an array of product items from inventory",
            responseCode = "200"
    )
    public ResponseEntity<List<ProductItem>> getProducts() {
        var allProducts = inventoryService.getAllInventoryProductItems();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    @Operation(summary = "Add a new product item to the inventory")
    @ApiResponse(
            description = "Responds with the created product item",
            responseCode = "201"
    )
    public ResponseEntity<ProductItem> addProduct(@RequestBody ProductItem newProductItem) {
        var addedProduct = inventoryService.addProductToInventory(newProductItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    @GetMapping("{productId}")
    @Operation(summary = "Get a specific product item from inventory")
    @ApiResponses({
            @ApiResponse(
                    description = "Responds with the product item if there is one",
                    responseCode = "200"
            ),
            @ApiResponse(
                    description = "There is no product item with the requested id",
                    responseCode = "404"
            )}
    )
    public ResponseEntity<ProductItem> getProducts(@PathVariable("productId") UUID productId) {
        var productItem = inventoryService.getProductItemDetails(productId);
        return productItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("{productId}")
    @Operation(summary = "Delete a product item from the inventory")
    @ApiResponse(
            description = "The product item is deleted",
            responseCode = "204"
    )
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") UUID productId) {
        inventoryService.deleteProductItem(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("{productId}")
    @Operation(summary = "Update a product item")
    @ApiResponses({
            @ApiResponse(
                    description = "Updates and responds with the product item if there is one",
                    responseCode = "200"
            ),
            @ApiResponse(
                    description = "There is no product item with the provided id",
                    responseCode = "404"
            )}
    )
    public ResponseEntity<?> updateProduct(@PathVariable("productId") UUID productId, @RequestBody ProductItem productItem) {
        var updatedProductItem = inventoryService.updateProductItem(productId, productItem);
        return ResponseEntity.ok(updatedProductItem);
    }
}
