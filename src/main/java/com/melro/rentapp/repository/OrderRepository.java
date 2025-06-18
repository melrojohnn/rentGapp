package com.melro.rentapp.repository;

import com.melro.rentapp.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {


}
