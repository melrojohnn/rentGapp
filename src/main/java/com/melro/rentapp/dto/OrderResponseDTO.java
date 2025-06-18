package com.melro.rentapp.dto;

import java.time.Instant;
import java.util.List;

public record OrderResponseDTO(
        Long orderId,
        String customerName,
        String planName,
        List<String> equipmentNames,
        Instant orderDate,
        Instant endDate
) {}

