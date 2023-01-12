package com.vanna.mastery.integration.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {

    private UUID id;

    private String name;

    private String sku;

    private int activeInventoryCount;

    private BigDecimal activeInventoryPrice;
}
