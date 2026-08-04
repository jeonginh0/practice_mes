package com.inho.mespractice.item.dto;

import com.inho.mespractice.item.entity.Item;

public record ItemResponse(Long itemId, String itemCode, String itemName, String unit) {
    public static ItemResponse from(Item item) {
        return new ItemResponse(item.getItemId(), item.getItemCode(), item.getItemName(), item.getUnit());
    }
}
