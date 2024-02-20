package com.example.testypie.domain.reward.dto.response;

import com.example.testypie.domain.reward.entity.Reward;
import com.example.testypie.domain.user.entity.User;

public record ReadRewardResponseDTO(
        Long id, String rewardItem, Long itemSize, User user, Long productId) {

    public ReadRewardResponseDTO(Reward reward) {
        this(
                reward.getId(),
                reward.getRewardItem(),
                reward.getItemSize(),
                reward.getUser(),
                reward.getProduct().getId());
    }
}
