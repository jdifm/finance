/*
 * Copyright (c) 2014 - 2016.  Balance Hero. All rights reserved.
 */

package com.iksun.finance.controller.common;


import com.iksun.finance.user.dto.User;
import com.iksun.finance.user.service.TokenService;
import com.iksun.finance.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 호출시 token 체크.
 */
@Slf4j
@Component
public class TokenAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("path : {} {}" ,request.getRemoteHost(), request.getRequestURL());

        if (isSignController(request)) {
            return super.preHandle(request, response, handler);
        }

        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) { // 토큰 자체가 없을 시
            throw new RuntimeException("NEED TO SIGN IN");
        }
        User user = userService.getUserByToken(token);
        if (StringUtils.isEmpty(user)) { // 해당 토큰의 유저가 없을 시
            throw new RuntimeException("USER can not found");
        }

        return super.preHandle(request, response, handler);
    }

    /**
     * Sign Controller 는 token 없어도 패스
     * @param request
     * @return
     */
    private boolean isSignController(HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        if(requestUrl.contains("/sign")) {
            return true;
        }

        return false;
    }
}
