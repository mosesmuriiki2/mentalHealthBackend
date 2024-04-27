package com.example.mentalHealthApp.repository;

import com.example.mentalHealthApp.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {

    Roles findByName(String name);
}
