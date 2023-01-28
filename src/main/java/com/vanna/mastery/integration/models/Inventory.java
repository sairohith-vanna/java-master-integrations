package com.vanna.mastery.integration.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    private UUID inventoryUnitIdentifier;

    private String location;

    private int inventoryProducts;

    private int totalInventoryStockItems;
}
