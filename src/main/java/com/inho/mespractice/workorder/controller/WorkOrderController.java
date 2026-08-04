package com.inho.mespractice.workorder.controller;

import com.inho.mespractice.workorder.dto.WorkOrderSearchCondition;
import com.inho.mespractice.workorder.entity.WorkOrder;
import com.inho.mespractice.workorder.service.WorkOrderService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/work-orders")
@RequiredArgsConstructor
public class WorkOrderController {
    private final WorkOrderService workOrderService;

    @PostMapping
    public Long create(@RequestBody WorkOrder workOrder) {
        return workOrderService.create(workOrder);
    }

    @GetMapping("/{id}")
    public WorkOrder findById(@PathVariable Long id) {
        return workOrderService.getById(id);
    }

    @GetMapping
    public List<WorkOrder> search(
        @RequestParam(required = false) String status,
        @RequestParam(required = false) Long itemId,
        @RequestParam(required = false) Long equipmentId,
        @RequestParam(required = false) LocalDate fromDate,
        @RequestParam(required = false) LocalDate toDate
    ) {
        WorkOrderSearchCondition condition = new WorkOrderSearchCondition(status, itemId, equipmentId, fromDate, toDate);
        return workOrderService.search(condition);
    }

    @PutMapping("/{id}/status")
    public void changeStatus(@PathVariable Long id, @RequestParam String status) {
        workOrderService.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workOrderService.delete(id);
    }
}
