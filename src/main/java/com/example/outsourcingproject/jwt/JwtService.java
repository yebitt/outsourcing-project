package com.example.outsourcingproject.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // 비밀키의 경우 일반적으로 환경변수로 관리합니다.
    // 편의를 위해 속성으로 선언해 놓고 사용합니다.
    private String secret = "8kX&v9x$R1@lPzM#7qTfBzLdNwKwZ*Fa";

    /* 토큰 생성 */
    public String createJwt(Long memberId) {

        // 1. secretKey 생성
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        // 2. 데이터 준비
        String subject = memberId.toString();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 & 60); // 만료시간 설정 1시간 뒤

        // 3. 토큰(JWT) 만들기
        String jwt = Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                //.claim("key1", "value1") // 커스텀 Claim 추가할 수 있음
                .expiration(expiration)
                .signWith(secretKey)
                .compact();

        return jwt;
    }

    /* 토큰 검증 */
    public long verifyToken(String token) {

        // 1. secretKey 생성
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        // 2. 검증
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // 3. 사용자 추출
        String subject = claims.getSubject();
        // String value1  = (String) claims.get("key1"); // 커스텀하게 설정한 요소 추출

        // 4. 타입 변환
        long memberId = Long.parseLong(subject);

        // 5. 반환
        return memberId;
    }

}
