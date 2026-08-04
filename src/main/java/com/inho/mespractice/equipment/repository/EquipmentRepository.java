package com.inho.mespractice.equipment.repository;

import com.inho.mespractice.equipment.entity.Equipment;
import com.inho.mespractice.equipment.entity.EquipmentStatus;
import java.util.List;
import java.util.Optional;

public interface EquipmentRepository {
    Long save(Equipment equipment);
    Optional<Equipment> findById(Long equipmentId);
    List<Equipment> findAll();
    void updateStatus(Long equipmentId, EquipmentStatus status);
    void deleteById(Long equipmentId);
}
