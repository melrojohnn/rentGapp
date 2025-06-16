package com.melro.rentapp.dto;
import com.melro.rentapp.model.common.UsersData;


public record CreateUserDto(String name, String email, String password) {
}
