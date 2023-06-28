package com.esun.hw.service;

import org.springframework.http.ResponseEntity;

import com.esun.hw.dto.request.SignupDto;

public interface AuthService {
    public ResponseEntity<?> registerUser(SignupDto signupDto);
}
