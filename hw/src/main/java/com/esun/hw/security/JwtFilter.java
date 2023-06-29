package com.esun.hw.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import com.esun.hw.dao.UserRepository;
import com.esun.hw.dto.request.JwtPayload;
import com.esun.hw.exception.AppException;
import com.esun.hw.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtProvider jwtProvider;

    private UserRepository userRepository;

    ObjectMapper objectMapper;

    private String jwt;

    private final Set<String> excludedUrls = new HashSet<>();

    public JwtFilter(JwtProvider jwtProvider, ObjectMapper objectMapper, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        excludedUrls.add("/api/auth/login");
        excludedUrls.add("/api/auth/register");

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, AppException {

        try {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                throw new AppException(HttpStatus.UNAUTHORIZED, "Invalid token");
            }

            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    this.jwt = cookie.getValue();
                    break;
                }
            }
            User reqUser = getRequestUser(jwt);

            if (reqUser == null) {
                throw new AppException(HttpStatus.UNAUTHORIZED, "User Not found");
            }

            request.setAttribute("reqUserId", reqUser.getUserId());

            filterChain.doFilter(request, response);
        } catch (AppException e) {
            ErrorResponse errorResponse = ErrorResponse.builder(e, e.getStatus(), e.getErrorMessage()).build();
            response.setContentType("application/json");
            response.setStatus(e.getStatus().value());
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }

    }

    private User getRequestUser(String token) throws JsonMappingException, JsonProcessingException {

        JwtPayload payload = jwtProvider.decodedJwt(token);

        if (payload == null) {
            return null;
        }

        User reqUser = userRepository.findById(payload.getUserId()).get();

        return reqUser;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // TODO Auto-generated method stub
        String requestURI = request.getRequestURI();
        return excludedUrls.contains(requestURI);
    }

}
