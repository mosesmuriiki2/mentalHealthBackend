package com.example.mentalHealthApp.repository;

import com.example.mentalHealthApp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
}
