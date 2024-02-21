package com.example.testypie.domain.user.dto.response;

import com.example.testypie.domain.user.entity.User;

public record ReadProfileResponseDTO(
        String account, String nickname, String email, String description, String fileUrl) {
    public static ReadProfileResponseDTO of(User user) {
        return new ReadProfileResponseDTO(
                user.getAccount(),
                user.getNickname(),
                user.getEmail(),
                user.getDescription(),
                user.getFileUrl());
    }
}
