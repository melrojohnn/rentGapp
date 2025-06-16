package com.melro.rentapp.dto;

public record CreatePhoneDto(
        String brand,
        String model,
        String itemId,
        String camera,
        String hd,
        String screen
) {}
