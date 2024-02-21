package com.example.testypie.domain.product.dto.request;

import com.example.testypie.domain.reward.dto.request.CreateRewardRequestDTO;
import java.util.List;

public record CreateProductTestRequestDTO(
        List<CreateRewardRequestDTO> rewardList, String startAt, String closedAt) {}
