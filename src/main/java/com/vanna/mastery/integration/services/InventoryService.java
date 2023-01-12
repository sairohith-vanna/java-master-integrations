package com.vanna.mastery.integration.services;

import com.vanna.mastery.integration.models.ProductItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryService implements InventoryManager{
    
    @Override
    public List<ProductItem> getAllInventoryProductItems() {
        return null;
    }

    @Override
    public Optional<ProductItem> getProductItemDetails(UUID productId) {
        return Optional.empty();
    }

    @Override
    public ProductItem addProductToInventory(ProductItem newProductItem) {
        return null;
    }

    @Override
    public UUID deleteProductItem(UUID deletableProductId) {
        return null;
    }

    @Override
    public ProductItem updateProductItem(UUID updatableProductId, ProductItem updateProductItemDetails) {
        return null;
    }
}
