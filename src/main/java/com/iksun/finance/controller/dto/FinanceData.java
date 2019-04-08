package com.iksun.finance.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class FinanceData {
    String year;
    String month;
    Map<String, BigDecimal> instituteAmount;
}
