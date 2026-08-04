package com.inho.mespractice.workorder.dto;

import com.inho.mespractice.workorder.entity.WorkOrderStatus;
import java.time.LocalDate;

public record WorkOrderSearchCondition(WorkOrderStatus status, Long itemId, Long equipmentId, LocalDate fromDate, LocalDate toDate) {
}
