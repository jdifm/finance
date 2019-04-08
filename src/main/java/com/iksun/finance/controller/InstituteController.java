package com.iksun.finance.controller;

import com.iksun.finance.service.InstituteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
public class InstituteController implements SecurityController {

    @Autowired
    private InstituteService instituteService;

    @GetMapping("/institute")
    public ResponseEntity<Map<String, Object>> getInstitute() {

        Map<String, Object> result = new HashMap<>();
        try {
            result.put("result", "success");
            result.put("institute",instituteService.getAll());
        } catch (Exception e) {
            log.error("exception ", e);
            result.put("result", "fail");
        }
        return ResponseEntity.ok(result);
    }
}
