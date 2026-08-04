package com.inho.mespractice.workorder.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class WorkOrder {
    private Long workOrderId;
    private String workOrderNo;
    private Long itemId;
    private Long equipmentId;
    private Integer planQty;
    private LocalDate startDate;
    private LocalDate endDate;
    private WorkOrderStatus status;
    private LocalDateTime createdAt;

    // Join 결과로만 채워짐
    private String itemName;
    private String equipmentName;
}
