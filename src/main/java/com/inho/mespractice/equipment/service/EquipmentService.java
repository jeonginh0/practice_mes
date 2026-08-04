package com.inho.mespractice.equipment.service;

import com.inho.mespractice.equipment.entity.Equipment;
import com.inho.mespractice.equipment.entity.EquipmentStatus;
import com.inho.mespractice.equipment.repository.EquipmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public Long create(Equipment equipment) {
        equipment.setStatus(EquipmentStatus.IDLE);
        return equipmentRepository.save(equipment);
    }

    public Equipment getById(Long equipmentId) {
        return equipmentRepository.findById(equipmentId).orElseThrow();
    }

    public List<Equipment> getAll() {
        return equipmentRepository.findAll();
    }

    public void changeStatus(Long equipmentId, EquipmentStatus status) {
        getById(equipmentId);
        equipmentRepository.updateStatus(equipmentId, status);
    }

    public void delete(Long equipmentId) {
        equipmentRepository.deleteById(equipmentId);
    }

}
