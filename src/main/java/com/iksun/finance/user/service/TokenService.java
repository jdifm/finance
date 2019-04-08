package com.iksun.finance.user.service;

import com.iksun.finance.user.dto.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenService {

    public static String makeToken(User user) {
        String token = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setHeaderParam("issueDate", System.currentTimeMillis())
                .setSubject(user.getPassword())
                .signWith(SignatureAlgorithm.HS512, "IWANTJOIN")
                .compact();
        return token;
    }
}
