package com.iksun.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iksun.finance.service.FinanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FinanceController implements SecurityController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FinanceService financeService;

    /**
     * 연도별 각 금융기관의 지원 합계 api
     * @return
     */
    @GetMapping("/finance/yearly")
    public ResponseEntity<String> getTotalAmountForAllInstituteYearly() {
        try {
            Map<String, Object> result = new HashMap<>();
            result.put("name", "주택금융 공급현황");
            result.put("detail", financeService.findAllTotalAmountRecordByYear()); // TODO: 2019-04-07 이거 리턴 형식 확인
            return ResponseEntity.ok(objectMapper.writeValueAsString(result));
        } catch (Exception e) {
            log.error("exception ", e);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/finance/yearly/max/{bankCode}")
    public ResponseEntity<String> getMaxAmountForAllInstituteYearly(@PathVariable("bankCode") String bankCode) {

        try {
            return ResponseEntity.ok(objectMapper.writeValueAsString(financeService.findMaxTotalAmountYearByInstitute(bankCode)));
        } catch (Exception e) {
            log.error("Exception occur", e);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/finance/yearly/minmax/{bankCode}")
    public ResponseEntity<String> getMinMax(@PathVariable("bankCode") String bankCode) {
        try {
            return ResponseEntity.ok(objectMapper.writeValueAsString(financeService.findMinMaxAmountYearByinstitute(bankCode)));
        } catch (Exception e) {
            log.error("Exception ", e);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/finance/predict")
    public ResponseEntity<String> getPredictAmount(@RequestParam(value = "bankCode") String bankCode,
                                                   @RequestParam(value = "year", required = false, defaultValue = "2018") String year,
                                                   @RequestParam("month") String month) {
        try {
            return ResponseEntity.ok(objectMapper.writeValueAsString(financeService.predictAmount(bankCode, year, month)));
        } catch (Exception e) {
            log.error("Exception ", e);
        }
        return ResponseEntity.notFound().build();
    }
}
