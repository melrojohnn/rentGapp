package com.melro.rentapp.service;

import com.melro.rentapp.dto.CreatePlanDto;
import com.melro.rentapp.model.PlansModel;
import com.melro.rentapp.repository.PlansRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlansService {

    private final PlansRepository plansRepository;

    public PlansService(PlansRepository plansRepository) {
        this.plansRepository = plansRepository;
    }

    public Long createPlan(CreatePlanDto dto) {
        if (plansRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Plan with this name already exists");
        }

        PlansModel plan = new PlansModel();
        plan.setName(dto.name());
        plan.setPrice(dto.price());
        plan.setDescription(dto.description());

        return plansRepository.save(plan).getId();
    }

    public Optional<PlansModel> findById(Long id) {
        return plansRepository.findById(id);
    }

    public List<PlansModel> findAll() {
        return plansRepository.findAll();
    }

    public void deleteById(Long id) {
        plansRepository.deleteById(id);
    }
}
