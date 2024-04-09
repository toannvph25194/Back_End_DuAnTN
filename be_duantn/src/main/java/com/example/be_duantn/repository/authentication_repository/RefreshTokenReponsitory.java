package com.example.be_duantn.repository.authentication_repository;

import com.example.be_duantn.entity.RefeshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenReponsitory extends JpaRepository<RefeshToken, UUID>{

    Optional<RefeshToken> findByToken(String token);

}