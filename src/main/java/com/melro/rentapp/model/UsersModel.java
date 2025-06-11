package com.melro.rentapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_member")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usr", nullable = false, length = 100)
    private String name;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "login", unique = true, nullable = false, updatable = false, length = 36)
    private String login = UUID.randomUUID().toString();

    @JsonIgnore
    @Column(name = "passkey", nullable = false, length = 100)
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createTimestamp;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updateTimestamp;

}
