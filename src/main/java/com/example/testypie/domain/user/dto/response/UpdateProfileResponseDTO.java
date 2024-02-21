package com.example.testypie.domain.user.dto.response;

import com.example.testypie.domain.user.entity.User;

public record UpdateProfileResponseDTO(
        String account, String nickname, String email, String description, String fileUrl) {
    public static UpdateProfileResponseDTO of(User user) {
        return new UpdateProfileResponseDTO(
                user.getAccount(),
                user.getNickname(),
                user.getEmail(),
                user.getDescription(),
                user.getFileUrl());
    }
}
