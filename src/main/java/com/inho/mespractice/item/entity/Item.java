package com.inho.mespractice.item.entity;

import lombok.Data;

@Data
public class Item {
    private long itemId;
    private String itemCode;
    private String itemName;
    private String unit;
}
