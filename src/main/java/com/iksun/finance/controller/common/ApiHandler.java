package com.iksun.finance.controller.common;

import com.iksun.finance.controller.SecurityController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception Catch용
 */
@Slf4j
@ControllerAdvice(assignableTypes = {SecurityController.class})
public class ApiHandler {

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Map<String, Object> customException(Exception customException, WebRequest request) {
        log.info("request : {} " , request.getContextPath());
        Map<String, Object> result = new HashMap<>();
        result.put("result", "fail");
        result.put("message", customException.getMessage());
        return result;
    }
}
