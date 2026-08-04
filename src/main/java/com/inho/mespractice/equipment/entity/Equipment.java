package com.inho.mespractice.equipment.entity;

import lombok.Data;

@Data
public class Equipment {
    private Long equipmentId;
    private String equipmentCode;
    private String equipmentName;
    private EquipmentStatus status;
}
