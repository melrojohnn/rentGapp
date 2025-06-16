package com.melro.rentapp.service;

import com.melro.rentapp.dto.CreatePhoneDto;
import com.melro.rentapp.model.SmartphoneModel;
import com.melro.rentapp.repository.SmartphoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {

    private final SmartphoneRepository PhoneRepository;

    public PhoneService(SmartphoneRepository PhoneRepository) {
        this.PhoneRepository = PhoneRepository;
    }

    public SmartphoneModel createPhone(CreatePhoneDto dto) {
        SmartphoneModel Phone = new SmartphoneModel();
        Phone.setBrand(dto.brand());
        Phone.setModel(dto.model());
        Phone.setItemId(dto.itemId());
        Phone.setCamera(dto.camera());
        Phone.setHd(dto.hd());
        Phone.setScreen(dto.screen());
        // A data created_at e updated_at podem ser setadas no entity listener, ou aqui
        return PhoneRepository.save(Phone);
    }

    public List<SmartphoneModel> findAll() {
        return PhoneRepository.findAll();
    }
}
