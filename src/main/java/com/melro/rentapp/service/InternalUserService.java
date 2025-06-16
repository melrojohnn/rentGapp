package com.melro.rentapp.service;

import com.melro.rentapp.dto.CreateUserDto;
import com.melro.rentapp.enums.UserRole;
import com.melro.rentapp.model.InternalUserModel;
import com.melro.rentapp.repository.InternalUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternalUserService {

    private final InternalUserRepository internalUserRepository;
    private final PasswordEncoder passwordEncoder;

    public InternalUserService(InternalUserRepository internalUserRepository, PasswordEncoder passwordEncoder) {
        this.internalUserRepository = internalUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long createInternalUser(CreateUserDto dto) {
        // Passa o role corretamente
        var internal = new InternalUserModel(
                dto.name(),
                dto.email(),
                passwordEncoder.encode(dto.password()),
                UserRole.INTERNAL_USER
        );
        var saved = internalUserRepository.save(internal);
        return saved.getUserId();
    }

    public Optional<InternalUserModel> findById(Long id) {
        return internalUserRepository.findById(id);
    }

    public Optional<InternalUserModel> findByEmail(String email) {
        return internalUserRepository.findByEmail(email);
    }

    public List<InternalUserModel> findAll() {
        return internalUserRepository.findAll();
    }
}
