package com.melro.rentapp.controller;

import com.melro.rentapp.dto.CreateUserDto;
import com.melro.rentapp.model.InternalUserModel;
import com.melro.rentapp.service.InternalUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/internal-users")
public class InternalUserController {

    private final InternalUserService internalUserService;

    public InternalUserController(InternalUserService internalUserService) {
        this.internalUserService = internalUserService;
    }

    @PostMapping
    public ResponseEntity<Void> createInternalUser(@RequestBody CreateUserDto createUserDto) {
        Long id = internalUserService.createInternalUser(createUserDto);
        return ResponseEntity.created(URI.create("/v1/internal-users/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternalUserModel> getInternalUserById(@PathVariable Long id) {
        return internalUserService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> listAllInternalUsers() {
        return ResponseEntity.ok(internalUserService.findAll());
    }
}
