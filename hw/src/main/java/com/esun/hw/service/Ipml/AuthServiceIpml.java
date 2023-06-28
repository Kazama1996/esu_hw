package com.esun.hw.service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.esun.hw.dao.UserRepository;
import com.esun.hw.dto.request.SignupDto;
import com.esun.hw.exception.AppException;
import com.esun.hw.model.User;
import com.esun.hw.service.AuthService;

@Service
public class AuthServiceIpml implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthServiceIpml(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> registerUser(SignupDto signupDto) {
        // TODO Auto-generated method stub

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

        // create JWT and assign into HttpOnlyCookie

        return new ResponseEntity<>("User registered successfully !!", HttpStatus.CREATED);
    }

}
