package com.inho.mespractice.workorder.repository;

import com.inho.mespractice.workorder.dto.WorkOrderSearchCondition;
import com.inho.mespractice.workorder.entity.WorkOrder;
import com.inho.mespractice.workorder.entity.WorkOrderStatus;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WorkOrderRepositoryImpl implements WorkOrderRepository {
    private final WorkOrderMapper workOrderMapper;

    @Override
    public Long save(WorkOrder workOrder) {
        workOrderMapper.insertWorkOrder(workOrder);
        return workOrder.getWorkOrderId();
    }

    @Override
    public Optional<WorkOrder> findById(Long workOrderId) {
        return Optional.ofNullable(workOrderMapper.findById(workOrderId));
    }

    @Override
    public List<WorkOrder> search(WorkOrderSearchCondition condition) {
        return workOrderMapper.search(condition);
    }

    @Override
    public void updateStatus(Long workOrderId, WorkOrderStatus status) {
        workOrderMapper.updateStatus(workOrderId, status);
    }

    @Override
    public void deleteById(Long workOrderId) {
        workOrderMapper.deleteById(workOrderId);
    }
}
