package com.melro.rentapp.model;

import com.melro.rentapp.enums.UserRole;
import com.melro.rentapp.model.common.UsersData;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_internal_user")
public class InternalUserModel extends UsersData {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UserRole role;

    public InternalUserModel() {
        super();
    }

    public InternalUserModel(String name, String email, String password, UserRole role) {
        super(name, email, password);
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
