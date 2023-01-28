package com.vanna.mastery.integration.services;

import com.vanna.mastery.integration.models.Inventory;
import com.vanna.mastery.integration.models.ProductItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryManager {

    List<ProductItem> getAllInventoryProductItems();
    Optional<ProductItem> getProductItemDetails(UUID productId);
    ProductItem addProductToInventory(ProductItem newProductItem);
    UUID deleteProductItem(UUID deletableProductId);
    ProductItem updateProductItem(UUID updatableProductId, ProductItem updateProductItemDetails);
    Inventory getInventoryDetails();
}
