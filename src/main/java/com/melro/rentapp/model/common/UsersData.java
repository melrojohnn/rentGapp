package com.melro.rentapp.model.common;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public abstract class UsersData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userId;

    @Column(name = "usr", nullable = false, length = 100)
    protected String name;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    protected String email;

    @Column(name = "account_id", unique = true, nullable = false, updatable = false, length = 36)
    protected String accountID = UUID.randomUUID().toString();

    @JsonIgnore
    @Column(name = "passkey", nullable = false, length = 100)
    protected String password;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createTimestamp;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    protected Instant updateTimestamp;

    public UsersData(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UsersData() {

    }


    // Getters & Setters

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreateTimestamp() {
        return createTimestamp;
    }

    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }
}
