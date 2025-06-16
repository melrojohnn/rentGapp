package com.melro.rentapp.model;

import com.melro.rentapp.model.common.UsersData;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_customer")
public class CustomerModel extends UsersData {

    public CustomerModel() {
        super();
    }

    public CustomerModel(String name, String email, String password) {
        super(name, email, password);
    }


}
