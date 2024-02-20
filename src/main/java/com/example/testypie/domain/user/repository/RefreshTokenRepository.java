package com.example.testypie.domain.user.repository;

import com.example.testypie.domain.user.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByTokenValue(String tokenValue);
}
