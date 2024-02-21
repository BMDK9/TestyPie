package com.example.testypie.domain.user.dto.response;

import com.example.testypie.domain.user.entity.User;

public record ProfileResponseDTO(
        String account, String nickname, String email, String description, String fileUrl) {
    public static ProfileResponseDTO of(User user) {
        return new ProfileResponseDTO(
                user.getAccount(),
                user.getNickname(),
                user.getEmail(),
                user.getDescription(),
                user.getFileUrl());
    }
}
