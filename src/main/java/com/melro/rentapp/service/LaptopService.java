package com.melro.rentapp.service;

import com.melro.rentapp.dto.CreateLaptopDTO;
import com.melro.rentapp.model.LaptopModel;
import com.melro.rentapp.repository.LaptopRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaptopService {

    private final LaptopRepository laptopRepository;

    public LaptopService(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    public Long createLaptop(CreateLaptopDTO dto) {
        LaptopModel entity;
        entity = new LaptopModel(
                dto.model(),
                dto.brand(),
                dto.gpu(),
                dto.cpu(),
                dto.ram(),
                dto.hd()
        );

        var saved = laptopRepository.save(entity);
        return saved.getLaptopId();
    }

    public Optional<LaptopModel> findById(Long laptopId) {
        return laptopRepository.findById(laptopId);
    }
}
