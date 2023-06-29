package com.esun.hw.service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.esun.hw.dao.UserRepository;
import com.esun.hw.dto.request.LoginDto;
import com.esun.hw.dto.request.SignupDto;
import com.esun.hw.exception.AppException;
import com.esun.hw.model.User;
import com.esun.hw.security.JwtProvider;
import com.esun.hw.service.AuthService;

@Service
public class AuthServiceIpml implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public AuthServiceIpml(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> registerUser(SignupDto signupDto) {

        if (userRepository.existsByPhone(signupDto.getPhone())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "This phone is already taken !!");
        }
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "This email is already taken !!");
        }
        User user = User.builder().email(signupDto.getEmail()).phone(signupDto.getPhone())
                .userName(signupDto.getUserName()).password(passwordEncoder.encode(signupDto.getPassword()))
                .biography(signupDto.getBiography())
                .build();

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully !!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> login(LoginDto loginDto) {

        User targetUser = userRepository.findByPhone(loginDto.getPhone());

        if (targetUser == null) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), targetUser.getPassword())) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        String jwt = jwtProvider.genJWT(targetUser);

        ResponseCookie cookie = ResponseCookie.from("jwt", jwt).httpOnly(true).secure(true).domain("localhost")
                .path("/").sameSite("Lax").build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Login success!! Welcome~~");

    }

}
