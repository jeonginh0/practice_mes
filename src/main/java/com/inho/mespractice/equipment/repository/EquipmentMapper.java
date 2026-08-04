package com.inho.mespractice.equipment.repository;

import com.inho.mespractice.equipment.entity.Equipment;
import com.inho.mespractice.equipment.entity.EquipmentStatus;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EquipmentMapper {
    int insertEquipment(Equipment equipment);

    Equipment findById(@Param("equipmentId") Long equipmentId);

    List<Equipment> findAll();

    int updateStatus(@Param("equipmentId") Long equipmentId, @Param("status") EquipmentStatus status);

    int deleteById(@Param("equipmentId") Long equipmentId);
}
