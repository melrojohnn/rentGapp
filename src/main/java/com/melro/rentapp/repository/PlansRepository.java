package com.melro.rentapp.repository;

import com.melro.rentapp.model.PlansModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlansRepository extends JpaRepository<PlansModel, Long> {
    boolean existsByName(String name);
}
