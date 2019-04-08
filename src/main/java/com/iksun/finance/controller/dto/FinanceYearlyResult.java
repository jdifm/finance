package com.iksun.finance.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class FinanceYearlyResult {
    private String year;
    private BigDecimal totalAmount;
    private Map<String, String> detailAmount;
}
