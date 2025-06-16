package com.melro.rentapp.dto;

public record CreateTabletDto(
        String brand,
        String model,
        String itemId,
        String camera,
        String hd,
        String screen
) {}
