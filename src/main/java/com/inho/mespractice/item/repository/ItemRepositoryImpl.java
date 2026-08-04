package com.inho.mespractice.item.repository;

import com.inho.mespractice.item.entity.Item;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final ItemMapper itemMapper;

    @Override
    public Long save(Item item) {
        itemMapper.insertItem(item);
        return item.getItemId();
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        return Optional.ofNullable(itemMapper.findById(itemId));
    }

    @Override
    public List<Item> findAll() {
        return itemMapper.findAll();
    }

    @Override
    public void update(Item item) {
        itemMapper.updateItem(item);
    }

    @Override
    public void deleteById(Long itemId) {
        itemMapper.deleteById(itemId);
    }
}
