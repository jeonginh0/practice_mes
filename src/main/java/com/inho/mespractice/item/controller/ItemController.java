package com.inho.mespractice.item.controller;

import com.inho.mespractice.item.dto.ItemCreateRequest;
import com.inho.mespractice.item.dto.ItemResponse;
import com.inho.mespractice.item.dto.ItemUpdateRequest;
import com.inho.mespractice.item.service.ItemService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Long create(@Valid @RequestBody ItemCreateRequest request) {
        return itemService.create(request.toEntity());
    }

    @GetMapping("/{id}")
    public ItemResponse findById(@PathVariable Long id) {
        return ItemResponse.from(itemService.getById(id));
    }

    @GetMapping
    public List<ItemResponse> findAll() {
        return itemService.getAll().stream().map(ItemResponse::from).toList();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody ItemUpdateRequest request) {
        itemService.update(request.toEntity(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

}
