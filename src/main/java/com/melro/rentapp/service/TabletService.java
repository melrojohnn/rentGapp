package com.melro.rentapp.service;

import com.melro.rentapp.dto.CreateTabletDto;
import com.melro.rentapp.model.TabletModel;
import com.melro.rentapp.repository.TabletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabletService {

    private final TabletRepository tabletRepository;

    public TabletService(TabletRepository tabletRepository) {
        this.tabletRepository = tabletRepository;
    }

    public TabletModel createTablet(CreateTabletDto dto) {
        TabletModel tablet = new TabletModel();
        tablet.setBrand(dto.brand());
        tablet.setModel(dto.model());
        tablet.setItemId(dto.itemId());
        tablet.setCamera(dto.camera());
        tablet.setHd(dto.hd());
        tablet.setScreen(dto.screen());
        // A data created_at e updated_at podem ser setadas no entity listener, ou aqui
        return tabletRepository.save(tablet);
    }

    public List<TabletModel> findAll() {
        return tabletRepository.findAll();
    }
}
