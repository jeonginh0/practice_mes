package com.inho.mespractice.item.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.inho.mespractice.item.entity.Item;
import org.junit.jupiter.api.Test;

class ItemDtoTest {

    @Test
    void createRequestMapsToEntity() {
        Item item = new ItemCreateRequest("IT-001", "볼트", "EA").toEntity();

        assertThat(item.getItemCode()).isEqualTo("IT-001");
        assertThat(item.getItemName()).isEqualTo("볼트");
        assertThat(item.getUnit()).isEqualTo("EA");
    }

    @Test
    void updateRequestMapsToEntityWithGivenId() {
        Item item = new ItemUpdateRequest("볼트", "EA").toEntity(5L);

        assertThat(item.getItemId()).isEqualTo(5L);
        assertThat(item.getItemName()).isEqualTo("볼트");
        assertThat(item.getUnit()).isEqualTo("EA");
    }

    @Test
    void responseMapsFromEntity() {
        Item item = new Item();
        item.setItemId(5L);
        item.setItemCode("IT-001");
        item.setItemName("볼트");
        item.setUnit("EA");

        ItemResponse response = ItemResponse.from(item);

        assertThat(response.itemId()).isEqualTo(5L);
        assertThat(response.itemCode()).isEqualTo("IT-001");
        assertThat(response.itemName()).isEqualTo("볼트");
        assertThat(response.unit()).isEqualTo("EA");
    }
}
