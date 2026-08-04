package com.inho.mespractice.item.repository;

import com.inho.mespractice.item.entity.Item;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ItemMapper {

    int insertItem(Item item);

    Item findById(@Param("itemId") Long itemId);

    List<Item> findAll();

    int updateItem(Item item);

    int deleteById(@Param("itemId") Long itemId);

}
