package com.inho.mespractice.workorder.service;

import com.inho.mespractice.equipment.repository.EquipmentRepository;
import com.inho.mespractice.item.repository.ItemRepository;
import com.inho.mespractice.workorder.dto.WorkOrderSearchCondition;
import com.inho.mespractice.workorder.entity.WorkOrder;
import com.inho.mespractice.workorder.entity.WorkOrderStatus;
import com.inho.mespractice.workorder.repository.WorkOrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkOrderService {
    private final WorkOrderRepository workOrderRepository;
    private final ItemRepository itemRepository;
    private final EquipmentRepository equipmentRepository;

    public Long create(WorkOrder workOrder) {
        itemRepository.findById(workOrder.getItemId()).orElseThrow();
        equipmentRepository.findById(workOrder.getEquipmentId()).orElseThrow();

        workOrder.setStatus(WorkOrderStatus.PLANNED);
        return workOrderRepository.save(workOrder);
    }

    public WorkOrder getById(Long workOrderId) {
        return workOrderRepository.findById(workOrderId).orElseThrow();
    }

    public List<WorkOrder> search(WorkOrderSearchCondition condition) {
        return workOrderRepository.search(condition);
    }

    public void changeStatus(Long workOrderId, WorkOrderStatus status) {
        workOrderRepository.updateStatus(workOrderId, status);
    }

    public void delete(Long workOrderId) {
        workOrderRepository.deleteById(workOrderId);
    }

}
