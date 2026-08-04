package com.inho.mespractice.item.dto;

import com.inho.mespractice.item.entity.Item;
import jakarta.validation.constraints.NotBlank;

public record ItemUpdateRequest(@NotBlank String itemName, @NotBlank String unit) {
    public Item toEntity(Long itemId) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setItemName(itemName);
        item.setUnit(unit);
        return item;
    }
}
