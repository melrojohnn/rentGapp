package com.melro.rentapp.repository;


import com.melro.rentapp.model.TabletModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabletRepository extends JpaRepository<TabletModel, Long> {
}
