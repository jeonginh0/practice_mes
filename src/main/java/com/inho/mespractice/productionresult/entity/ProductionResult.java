package com.inho.mespractice.productionresult.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductionResult {
    private Long resultId;
    private Long workOrderId;
    private Integer prodQty;
    private Integer defectQty;
    private LocalDate resultDate;
    private String workerName;
    private LocalDateTime createdAt;
}
