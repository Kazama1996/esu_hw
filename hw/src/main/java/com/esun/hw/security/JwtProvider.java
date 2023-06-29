package com.esun.hw.security;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.esun.hw.dto.request.JwtPayload;
import com.esun.hw.exception.AppException;
import com.esun.hw.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtScret;
    @Value("${jwt.expire-offset}")
    private long expireOffset;

    @Autowired
    private ObjectMapper objectMapper;

    public String genJWT(User user) {

        String userId = user.getUserId().toString();

        Date current = new Date();

        Date expireDate = new Date(current.getTime() + expireOffset);

        Algorithm algorithm = Algorithm.HMAC256(this.jwtScret);
        String jwt = JWT.create().withClaim("userId", userId).withExpiresAt(expireDate).sign(algorithm);
        System.out.println(jwt);
        return jwt;
    }

    public JwtPayload decodedJwt(String jwt) throws JsonMappingException, JsonProcessingException {
        Algorithm algorithm = Algorithm.HMAC256(this.jwtScret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            String strBase64 = verifier.verify(jwt).getPayload();
            byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
            String decodedString = new String(decodedBytes);
            JwtPayload payload = objectMapper.readValue(decodedString, JwtPayload.class);

            return payload;
        } catch (JWTDecodeException e) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }

    }
}
