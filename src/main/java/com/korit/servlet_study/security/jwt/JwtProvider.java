package com.korit.servlet_study.security.jwt;

import com.korit.servlet_study.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtProvider {
    private Key key;

    private static JwtProvider instance;

    private JwtProvider() {
        final String SECRET = "4b69584c51a318306759b8b63a611e3feecfb2700cabf0e38cea9dbd10c9fddc8e904d9a8f95344f33e85cfa2649cb6b7d512846ba05fc3097ee1059e5cd58aaa76dce1513a90be6c11ed483536748353f22e7a9e3d51f3d453ef6b8357516d3ccf9f7b26f18ab019153a1878bcea718b41c4575d8fc15aa13953b346575e6fc1833f542f8cd6d06e9fa9dbf708a647548e4359be0f96856d44cc243b526a1c17d826d1f40d5d30840780524bd3187a08d6b5657790cf3f0c893a756c291bfef3869ae02ce74925b6a03a3b58315422394f123a02586bf413099b18995c3a5511e0f5777d9171646ad01981f393d59185c69a3d5b32a7358207c61432cd8b2f0";
        // jwt key 를 만드는데에 있어 필요한 문법
        // token 인증 방식 >> key 값이 모든 서버에서 동일함
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public static JwtProvider getInstance() {
        if (instance == null) {
            instance = new JwtProvider();
        }
        return instance;
    }

    private Date getExpireDate() {
        return new Date(new Date().getTime() + (1000l * 60 * 60 * 24 * 365)); // 현 시간 + (1초(long타입)*60*60*24*365(1년))
    }

    public String generateToken(User user) { // 토큰 생성
        return Jwts.builder()
                .claim("userId", user.getUserId())
                .setExpiration(getExpireDate())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact(); // = build() >> jwt 에서는 compact 로 사용
    }
}
