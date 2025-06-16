package com.melro.rentapp.controller;

import com.melro.rentapp.dto.CreateLaptopDTO;
import com.melro.rentapp.model.LaptopModel;
import com.melro.rentapp.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/laptops")
public class LaptopController {

    private final LaptopService laptopService;

    @Autowired
    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @PostMapping
    public ResponseEntity<LaptopModel> createLaptop(@RequestBody CreateLaptopDTO createLaptopDTO) {
        var id = laptopService.createLaptop(createLaptopDTO);
        var laptop = laptopService.findById(id).orElseThrow();
        return ResponseEntity.created(URI.create("/v1/laptops/" + id)).body(laptop);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaptopModel> getLaptopById(@PathVariable Long id) {
        return laptopService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
