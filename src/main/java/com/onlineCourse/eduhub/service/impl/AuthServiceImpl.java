package com.onlineCourse.eduhub.service.impl;

import com.onlineCourse.eduhub.dto.auth.*;
import com.onlineCourse.eduhub.entity.User;
import com.onlineCourse.eduhub.repository.UserRepository;
import com.onlineCourse.eduhub.security.JwtService;
import com.onlineCourse.eduhub.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void signup(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists with this email");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email/password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email/password");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        return new LoginResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}