package com.inho.mespractice.workorder.dto;

import java.time.LocalDate;

public record WorkOrderSearchCondition(String status, Long itemId, Long equipmentId, LocalDate fromDate, LocalDate toDate) {
}
