package com.inho.mespractice.item.service;

import com.inho.mespractice.item.entity.Item;
import com.inho.mespractice.item.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Long create(Item item) {
        return itemRepository.save(item);
    }

    public Item getById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow();
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public void update(Item item) {
        getById(item.getItemId());
        itemRepository.update(item);
    }

    public void delete(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
