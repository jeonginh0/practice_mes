package com.inho.mespractice.productionresult.service;

import com.inho.mespractice.productionresult.dto.DailySummary;
import com.inho.mespractice.productionresult.entity.ProductionResult;
import com.inho.mespractice.productionresult.repository.ProductionResultRepository;
import com.inho.mespractice.workorder.repository.WorkOrderRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductionResultService {
    private final ProductionResultRepository productionResultRepository;
    private final WorkOrderRepository workOrderRepository;

    public Long create(ProductionResult result) {
        workOrderRepository.findById(result.getWorkOrderId()).orElseThrow();
        return productionResultRepository.save(result);
    }

    public int createBatch(List<ProductionResult> results) {
        results.stream()
            .map(ProductionResult::getWorkOrderId)
            .distinct()
            .forEach(id -> workOrderRepository.findById(id).orElseThrow());

        return productionResultRepository.saveBatch(results);
    }

    public List<ProductionResult> getByWorkOrder(Long workOrderId) {
        return productionResultRepository.findByWorkOrderId(workOrderId);
    }

    public List<DailySummary> getDailySummary(LocalDate fromDate, LocalDate toDate) {
        return productionResultRepository.getDailySummary(fromDate, toDate);
    }


}
