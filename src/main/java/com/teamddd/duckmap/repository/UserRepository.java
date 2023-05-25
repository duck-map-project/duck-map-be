package com.teamddd.duckmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamddd.duckmap.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
