package com.melro.rentapp.controller;

import com.melro.rentapp.dto.CreatePlanDto;
import com.melro.rentapp.model.PlansModel;
import com.melro.rentapp.service.PlansService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/plans")
public class PlansController {

    private final PlansService plansService;

    public PlansController(PlansService plansService) {
        this.plansService = plansService;
    }

    @PostMapping
    public ResponseEntity<Void> createPlan(@RequestBody CreatePlanDto createPlanDto) {
        Long id = plansService.createPlan(createPlanDto);
        return ResponseEntity.created(URI.create("/v1/plans/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlansModel> getPlanById(@PathVariable Long id) {
        return plansService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PlansModel>> getAllPlans() {
        return ResponseEntity.ok(plansService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        plansService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
