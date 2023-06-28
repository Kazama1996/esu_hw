package com.esun.hw.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esun.hw.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    public Boolean existsByPhone(String phone);

    public Boolean existsByEmail(String email);
}
