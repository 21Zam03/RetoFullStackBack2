package com.zam.prueba.repository;

import com.zam.prueba.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
