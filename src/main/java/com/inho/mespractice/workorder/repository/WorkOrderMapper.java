package com.inho.mespractice.workorder.repository;

import com.inho.mespractice.workorder.dto.WorkOrderSearchCondition;
import com.inho.mespractice.workorder.entity.WorkOrder;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WorkOrderMapper {
    int insertWorkOrder(WorkOrder workOrder);

    WorkOrder findById(@Param("workOrderId") Long workOrderId);

    List<WorkOrder> search(WorkOrderSearchCondition condition);

    int updateStatus(@Param("workOrderId") Long workOrderId, @Param("status") String status);

    int deleteById(@Param("workOrderId") Long workOrderId);
}
