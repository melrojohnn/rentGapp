package com.melro.rentapp.repository;

import com.melro.rentapp.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

    Optional<CustomerModel> findByEmail(String email);
}
