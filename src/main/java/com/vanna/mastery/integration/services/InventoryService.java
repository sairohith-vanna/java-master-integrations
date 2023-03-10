package com.vanna.mastery.integration.services;

import com.vanna.mastery.integration.models.Inventory;
import com.vanna.mastery.integration.models.ProductItem;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InventoryService implements InventoryManager {
    
    @Override
    public List<ProductItem> getAllInventoryProductItems() {
        return this.generateRandomData();
    }

    @Override
    public Optional<ProductItem> getProductItemDetails(UUID productId) {
        ProductItem item = this.generateRandomData().get(0);
        item.setId(productId);
        return Optional.of(item);
    }

    @Override
    public ProductItem addProductToInventory(ProductItem newProductItem) {
        newProductItem.setId(UUID.randomUUID());
        return newProductItem;
    }

    @Override
    public UUID deleteProductItem(UUID deletableProductId) {
        return UUID.randomUUID();
    }

    @Override
    public ProductItem updateProductItem(UUID updatableProductId, ProductItem updateProductItemDetails) {
        return updateProductItemDetails;
    }

    @Override
    public Inventory getInventoryDetails() {
        return new Inventory(
                UUID.randomUUID(),
                RandomStringUtils.randomAlphabetic(10, 15),
                new Random().nextInt(),
                new Random().nextInt()
        );
    }

    private List<ProductItem> generateRandomData() {
        List<ProductItem> productItems = new ArrayList<>();
        for(int i=0;i<5;i++) {
            productItems.add(new ProductItem());
        }
        return productItems;
    }
}
