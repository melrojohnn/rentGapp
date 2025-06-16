package com.melro.rentapp.dto;

import java.math.BigDecimal;

public record CreatePlanDto(String name, BigDecimal price, String description) {
}
