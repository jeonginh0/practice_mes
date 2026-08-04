package com.inho.mespractice.equipment.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.inho.mespractice.equipment.entity.Equipment;
import com.inho.mespractice.equipment.entity.EquipmentStatus;
import org.junit.jupiter.api.Test;

class EquipmentDtoTest {

    @Test
    void createRequestMapsToEntityWithoutStatus() {
        Equipment equipment = new EquipmentCreateRequest("EQ-001", "사출기 1호").toEntity();

        assertThat(equipment.getEquipmentCode()).isEqualTo("EQ-001");
        assertThat(equipment.getEquipmentName()).isEqualTo("사출기 1호");
        assertThat(equipment.getStatus()).isNull();
    }

    @Test
    void responseMapsStatusEnumFromEntity() {
        Equipment equipment = new Equipment();
        equipment.setEquipmentId(1L);
        equipment.setEquipmentCode("EQ-001");
        equipment.setEquipmentName("사출기 1호");
        equipment.setStatus(EquipmentStatus.RUNNING);

        EquipmentResponse response = EquipmentResponse.from(equipment);

        assertThat(response.status()).isEqualTo(EquipmentStatus.RUNNING);
    }
}
