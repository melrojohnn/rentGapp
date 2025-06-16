package com.melro.rentapp.repository;


import com.melro.rentapp.model.InternalUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternalUserRepository extends JpaRepository<InternalUserModel, Long> {

    Optional<InternalUserModel> findByEmail(String email);
}
