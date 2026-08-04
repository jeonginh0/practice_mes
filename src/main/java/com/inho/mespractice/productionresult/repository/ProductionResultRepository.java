package com.inho.mespractice.productionresult.repository;

import com.inho.mespractice.productionresult.dto.DailySummary;
import com.inho.mespractice.productionresult.entity.ProductionResult;
import java.time.LocalDate;
import java.util.List;

public interface ProductionResultRepository {
    Long save(ProductionResult result);
    int saveBatch(List<ProductionResult> results);
    List<ProductionResult> findByWorkOrderId(Long workOrderId);
    List<DailySummary> getDailySummary(LocalDate fromDate, LocalDate toDate);
}
