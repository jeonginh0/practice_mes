package com.inho.mespractice.productionresult.controller;

import com.inho.mespractice.productionresult.dto.DailySummary;
import com.inho.mespractice.productionresult.entity.ProductionResult;
import com.inho.mespractice.productionresult.service.ProductionResultService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/production-result")
@RequiredArgsConstructor
public class ProductionResultController {
    private final ProductionResultService productionResultService;

    @PostMapping
    public Long create(@RequestBody ProductionResult result) {
        return productionResultService.create(result);
    }

    @PostMapping("/batch")
    public int createBatch(@RequestBody List<ProductionResult> results) {
        return productionResultService.createBatch(results);
    }

    @GetMapping("/work-order/{workOrderId}")
    public List<ProductionResult> getByWorkOrderId(@PathVariable Long workOrderId) {
        return productionResultService.getByWorkOrder(workOrderId);
    }

    @GetMapping("/daily-summary")
    public List<DailySummary> getDailySummary(LocalDate fromDate, LocalDate toDate) {
        return productionResultService.getDailySummary(fromDate, toDate);
    }
}
