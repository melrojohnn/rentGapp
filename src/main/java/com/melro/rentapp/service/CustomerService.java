package com.melro.rentapp.service;

import com.melro.rentapp.dto.CreateUserDto;
import com.melro.rentapp.model.CustomerModel;
import com.melro.rentapp.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long createCustomer(CreateUserDto dto) {
        var customer = new CustomerModel(dto.name(), dto.email(), passwordEncoder.encode(dto.password()));
        var saved = customerRepository.save(customer);
        return saved.getUserId();
    }

    public Optional<CustomerModel> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<CustomerModel> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<CustomerModel> findAll() {
        return customerRepository.findAll();
    }
}
