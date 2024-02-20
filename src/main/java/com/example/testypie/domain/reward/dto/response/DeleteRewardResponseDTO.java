package com.example.testypie.domain.reward.dto.response;

import com.example.testypie.domain.reward.entity.Reward;

public record DeleteRewardResponseDTO(Long id) {
    public static DeleteRewardResponseDTO of(Reward reward) {
        return new DeleteRewardResponseDTO(reward.getId());
    }
}
