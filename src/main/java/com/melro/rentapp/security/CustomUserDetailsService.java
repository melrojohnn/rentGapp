package com.melro.rentapp.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.melro.rentapp.model.InternalUserModel;
import com.melro.rentapp.repository.InternalUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final InternalUserRepository internalUserRepository;

    public CustomUserDetailsService(InternalUserRepository internalUserRepository) {
        this.internalUserRepository = internalUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to find user by email first (most common case)
        InternalUserModel internalUser = internalUserRepository.findByEmail(username)
                .orElse(null);

        // If not found by email, try to find by name
        if (internalUser == null) {
            // For simplicity, we'll use the first admin user if username is "admin"
            if ("admin".equals(username)) {
                internalUser = internalUserRepository.findAll().stream()
                        .filter(user -> "ADMIN".equals(user.getRole().name()))
                        .findFirst()
                        .orElse(null);
            }
        }

        if (internalUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return User.builder()
                .username(internalUser.getEmail())
                .password(internalUser.getPassword())
                .authorities(
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + internalUser.getRole().name())))
                .build();
    }
}
