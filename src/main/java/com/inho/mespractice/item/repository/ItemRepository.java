package com.inho.mespractice.item.repository;

import com.inho.mespractice.item.entity.Item;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

public interface ItemRepository {
    Long save(Item item);
    Optional<Item> findById(Long itemId);
    List<Item> findAll();
    void update(Item item);
    void deleteById(Long itemId);
}
