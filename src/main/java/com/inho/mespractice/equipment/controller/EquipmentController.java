package com.inho.mespractice.equipment.controller;

import com.inho.mespractice.equipment.dto.EquipmentCreateRequest;
import com.inho.mespractice.equipment.dto.EquipmentResponse;
import com.inho.mespractice.equipment.dto.EquipmentStatusUpdateRequest;
import com.inho.mespractice.equipment.service.EquipmentService;
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
@RequestMapping("/api/equipments")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PostMapping
    public Long create(@Valid @RequestBody EquipmentCreateRequest request) {
        return equipmentService.create(request.toEntity());
    }

    @GetMapping("/{id}")
    public EquipmentResponse findById(@PathVariable Long id) {
        return EquipmentResponse.from(equipmentService.getById(id));
    }

    @GetMapping
    public List<EquipmentResponse> findAll() {
        return equipmentService.getAll().stream().map(EquipmentResponse::from).toList();
    }

    @PutMapping("/{id}")
    public void changeStatus(@PathVariable Long id, @Valid @RequestBody EquipmentStatusUpdateRequest request) {
        equipmentService.changeStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        equipmentService.delete(id);
    }
}
