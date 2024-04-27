package com.example.mentalHealthApp.service;

import com.example.mentalHealthApp.entity.Users;
import com.example.mentalHealthApp.models.UserDto;

import java.util.List;

public interface UserService {

    void  saveUser(UserDto userDto);

    Users findByEmail(String email);

    List<UserDto> findAllUsers();
}
