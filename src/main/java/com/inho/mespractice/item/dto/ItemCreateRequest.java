package com.inho.mespractice.item.dto;

import com.inho.mespractice.item.entity.Item;
import jakarta.validation.constraints.NotBlank;

public record ItemCreateRequest(@NotBlank String itemCode, @NotBlank String itemName, @NotBlank String unit) {
    public Item toEntity() {
        Item item = new Item();
        item.setItemCode(itemCode);
        item.setItemName(itemName);
        item.setUnit(unit);
        return item;
    }
}
