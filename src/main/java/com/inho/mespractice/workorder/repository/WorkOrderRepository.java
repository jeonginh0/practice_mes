package com.inho.mespractice.workorder.repository;

import com.inho.mespractice.workorder.dto.WorkOrderSearchCondition;
import com.inho.mespractice.workorder.entity.WorkOrder;
import com.inho.mespractice.workorder.entity.WorkOrderStatus;
import java.util.List;
import java.util.Optional;

public interface WorkOrderRepository {
    Long save(WorkOrder workOrder);
    Optional<WorkOrder> findById(Long workOrderId);
    List<WorkOrder> search(WorkOrderSearchCondition condition);
    void updateStatus(Long workOrderId, WorkOrderStatus status);
    void deleteById(Long workOrderId);


}
