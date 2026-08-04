package com.inho.mespractice.productionresult.dto;

import java.time.LocalDate;

public record DailySummary(LocalDate resultDate, Long totalProdQty, Long totalDefectQty) {
}
