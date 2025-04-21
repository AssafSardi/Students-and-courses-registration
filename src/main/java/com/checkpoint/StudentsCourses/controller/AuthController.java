package com.checkpoint.StudentsCourses.controller;

import com.checkpoint.StudentsCourses.dto.AuthRequestDTO;
import com.checkpoint.StudentsCourses.dto.AuthResponseDTO;
import com.checkpoint.StudentsCourses.service.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final SessionManager sessionManager;

    public AuthController(AuthenticationConfiguration authenticationConfiguration, SessionManager sessionManager) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.sessionManager = sessionManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String sessionKey = sessionManager.createSession(request.getUsername());
            return ResponseEntity.ok(Collections.singletonMap("sessionKey", sessionKey));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
