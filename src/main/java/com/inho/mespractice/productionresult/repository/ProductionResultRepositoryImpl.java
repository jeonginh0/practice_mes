package com.inho.mespractice.productionresult.repository;

import com.inho.mespractice.productionresult.dto.DailySummary;
import com.inho.mespractice.productionresult.entity.ProductionResult;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductionResultRepositoryImpl implements ProductionResultRepository {
    private final ProductionResultMapper productionResultMapper;


    @Override
    public Long save(ProductionResult result) {
        productionResultMapper.insertResult(result);
        return result.getResultId();
    }

    @Override
    public int saveBatch(List<ProductionResult> results) {
        return productionResultMapper.insertResultsBatch(results);
    }

    @Override
    public List<ProductionResult> findByWorkOrderId(Long workOrderId) {
        return productionResultMapper.findByWorkOrderId(workOrderId);
    }

    @Override
    public List<DailySummary> getDailySummary(LocalDate fromDate, LocalDate toDate) {
        return productionResultMapper.getDailySummary(fromDate, toDate);
    }
}
