package com.melro.rentapp.controller;

import com.melro.rentapp.dto.CreatePhoneDto;
import com.melro.rentapp.model.SmartphoneModel;
import com.melro.rentapp.service.PhoneService;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/Phones")
public class PhoneController {

    private final PhoneService PhoneService;

    public PhoneController(PhoneService PhoneService) {
        this.PhoneService = PhoneService;
    }

    @PostMapping
    public ResponseEntity<SmartphoneModel> createPhone(@RequestBody CreatePhoneDto dto) {
        SmartphoneModel Phone = PhoneService.createPhone(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Phone);
    }

    @GetMapping
    public List<SmartphoneModel> getAllPhones() {
        return PhoneService.findAll();
    }
}
