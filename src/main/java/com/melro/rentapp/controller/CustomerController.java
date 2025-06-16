package com.melro.rentapp.controller;

import com.melro.rentapp.dto.CreateUserDto;
import com.melro.rentapp.model.CustomerModel;
import com.melro.rentapp.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CreateUserDto createUserDto) {
        Long id = customerService.createCustomer(createUserDto);
        return ResponseEntity.created(URI.create("/v1/customers/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable Long id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> listAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }
}
