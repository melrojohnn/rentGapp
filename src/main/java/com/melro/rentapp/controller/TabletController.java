package com.melro.rentapp.controller;

import com.melro.rentapp.dto.CreateTabletDto;
import com.melro.rentapp.model.TabletModel;
import com.melro.rentapp.service.TabletService;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tablets")
public class TabletController {

    private final TabletService tabletService;

    public TabletController(TabletService tabletService) {
        this.tabletService = tabletService;
    }

    @PostMapping
    public ResponseEntity<TabletModel> createTablet(@RequestBody CreateTabletDto dto) {
        TabletModel tablet = tabletService.createTablet(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tablet);
    }

    @GetMapping
    public List<TabletModel> getAllTablets() {
        return tabletService.findAll();
    }
}
