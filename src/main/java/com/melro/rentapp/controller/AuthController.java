package com.melro.rentapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melro.rentapp.dto.LoginRequestDTO;
import com.melro.rentapp.dto.LoginResponseDTO;
import com.melro.rentapp.security.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
