package com.inho.mespractice.productionresult.repository;

import com.inho.mespractice.productionresult.dto.DailySummary;
import com.inho.mespractice.productionresult.entity.ProductionResult;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductionResultMapper {

    int insertResult(ProductionResult result);

    int insertResultsBatch(@Param("results")List<ProductionResult> results);

    List<ProductionResult> findByWorkOrderId(@Param("workOrderId") Long workOrderId);

    List<DailySummary> getDailySummary(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
