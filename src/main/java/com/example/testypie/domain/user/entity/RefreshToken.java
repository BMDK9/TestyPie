package com.example.testypie.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class RefreshToken {
    @Id String account;
    String tokenValue;

    @Builder
    private RefreshToken(String account, String tokenValue) {
        this.account = account;
        this.tokenValue = tokenValue;
    }
}
