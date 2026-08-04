package com.inho.mespractice.equipment.repository;

import com.inho.mespractice.equipment.entity.Equipment;
import com.inho.mespractice.equipment.entity.EquipmentStatus;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EquipmentRepositoryImpl implements EquipmentRepository {
    private final EquipmentMapper equipmentMapper;

    @Override
    public Long save(Equipment equipment) {
        equipmentMapper.insertEquipment(equipment);
        return equipment.getEquipmentId();
    }

    @Override
    public Optional<Equipment> findById(Long equipmentId) {
        return Optional.ofNullable(equipmentMapper.findById(equipmentId));
    }

    @Override
    public List<Equipment> findAll() {
        return equipmentMapper.findAll();
    }

    @Override
    public void updateStatus(Long equipmentId, EquipmentStatus status) {
        equipmentMapper.updateStatus(equipmentId, status);
    }

    @Override
    public void deleteById(Long equipmentId) {
        equipmentMapper.deleteById(equipmentId);
    }
}
