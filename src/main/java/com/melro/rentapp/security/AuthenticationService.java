package com.melro.rentapp.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.melro.rentapp.dto.LoginRequestDTO;
import com.melro.rentapp.dto.LoginResponseDTO;
import com.melro.rentapp.model.InternalUserModel;
import com.melro.rentapp.repository.InternalUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final InternalUserRepository internalUserRepository;

    public LoginResponseDTO authenticate(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()));

        // Find user to get role information
        InternalUserModel user = internalUserRepository.findByEmail(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Create UserDetails for token generation
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .build();

        String jwtToken = jwtService.generateToken(userDetails);

        return new LoginResponseDTO(
                jwtToken,
                user.getEmail(),
                user.getRole().name());
    }
}
