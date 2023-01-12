package com.vanna.mastery.integration.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductItem {

    public ProductItem() {
        this.id = UUID.randomUUID();
        this.name = RandomStringUtils.randomAlphabetic(5, 12);
        this.sku = RandomStringUtils.randomAlphabetic(4);
        this.activeInventoryCount = RandomUtils.nextInt();
        this.activeInventoryPrice = BigDecimal.valueOf(RandomUtils.nextDouble(5000.00, 4000000.00))
                .setScale(2, RoundingMode.DOWN);
    }

    private UUID id;

    private String name;

    private String sku;

    private int activeInventoryCount;

    private BigDecimal activeInventoryPrice;
}
