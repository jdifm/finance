package com.iksun.finance.user.controller;

import com.iksun.finance.user.dto.User;
import com.iksun.finance.user.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
public class SignController {

    @Autowired
    private SignService signService;

    @PostMapping("/signin")
    public ResponseEntity<Map<String ,Object>> signin(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();

        try {
            result.put("result", "success");
            String token = signService.signIn(user);

            if (StringUtils.isEmpty(token)) { // token 존재 시에만 정상 sign in
                throw new RuntimeException("SignIn Fail");
            }

            result.put("token", token);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Sign in Fail", e);

            result.put("result","fail");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> singup(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("result", "success");
            signService.signUp(user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("sign up fail", e);
            result.put("result", "fail");
        }

        return ResponseEntity.badRequest().body(result);
    }
}
