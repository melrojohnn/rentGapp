package com.melro.rentapp.repository;


import com.melro.rentapp.model.SmartphoneModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmartphoneRepository extends JpaRepository<SmartphoneModel, Long> {
}