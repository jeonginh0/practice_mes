package com.inho.mespractice.equipment.dto;

import com.inho.mespractice.equipment.entity.Equipment;
import jakarta.validation.constraints.NotBlank;

public record EquipmentCreateRequest(@NotBlank String equipmentCode, @NotBlank String equipmentName) {
    public Equipment toEntity() {
        Equipment equipment = new Equipment();
        equipment.setEquipmentCode(equipmentCode);
        equipment.setEquipmentName(equipmentName);
        return equipment;
    }
}
