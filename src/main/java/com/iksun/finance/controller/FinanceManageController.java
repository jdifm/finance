package com.iksun.finance.controller;

import com.iksun.finance.service.FinanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class FinanceManageController implements SecurityController {

    @Autowired
    private FinanceService financeService;

    /**
     * 데이터 파일의 각 레코드 저장
     * @param csvData
     */
    @PostMapping("/finance/manage")
    public ResponseEntity<String> postFinanceRawData(@RequestBody String csvData) {
        try {
            financeService.insert(csvData);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            log.error("Exception Finance Manage post raw data ", e);
            return ResponseEntity.badRequest().body("fail");
        }

    }
}
