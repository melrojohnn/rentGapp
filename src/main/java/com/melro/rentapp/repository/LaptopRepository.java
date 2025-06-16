package com.melro.rentapp.repository;

import com.melro.rentapp.model.LaptopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopRepository extends JpaRepository<LaptopModel, Long> {
}
