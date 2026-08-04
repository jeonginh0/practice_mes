package com.inho.mespractice.equipment.dto;

import com.inho.mespractice.equipment.entity.Equipment;
import com.inho.mespractice.equipment.entity.EquipmentStatus;

public record EquipmentResponse(Long equipmentId, String equipmentCode, String equipmentName, EquipmentStatus status) {
    public static EquipmentResponse from(Equipment equipment) {
        return new EquipmentResponse(equipment.getEquipmentId(), equipment.getEquipmentCode(), equipment.getEquipmentName(), equipment.getStatus());
    }

}
