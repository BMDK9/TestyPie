package com.example.testypie.domain.reward.dto.response;

import com.example.testypie.domain.reward.entity.Reward;

public record CreateRewardResponseDTO(Long id, String rewardItem, Long itemSize) {
    public static CreateRewardResponseDTO of(Reward reward) {
        return new CreateRewardResponseDTO(
                reward.getId(), reward.getRewardItem(), reward.getItemSize());
    }
}
