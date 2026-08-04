package com.inho.mespractice.equipment.dto;

import com.inho.mespractice.equipment.entity.EquipmentStatus;
import jakarta.validation.constraints.NotNull;

public record EquipmentStatusUpdateRequest(@NotNull EquipmentStatus status) {
}
