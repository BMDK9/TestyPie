package com.example.testypie.domain.reward.dto;

import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.reward.dto.request.CreateRewardRequestDTO;
import com.example.testypie.domain.reward.dto.response.ReadRewardResponseDTO;
import com.example.testypie.domain.reward.entity.Reward;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import java.util.List;
import java.util.stream.Collectors;

public class RewardMapper {
    public static List<Reward> mapToEntityList(
            List<CreateRewardRequestDTO> dtoList, Product product) {
        return dtoList.stream().map(dto -> mapToEntity(dto, product)).collect(Collectors.toList());
    }

    public static Reward mapToEntity(CreateRewardRequestDTO dto, Product product) {
        if (dto.rewardItem().isEmpty() || dto.itemSize() < 1) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.PRODUCT_REWARD_IS_NOT_NULL);
        }

        return Reward.builder()
                .reward_item(dto.rewardItem())
                .item_size(dto.itemSize())
                .product(product)
                .build();
    }

    public static List<ReadRewardResponseDTO> mapToDTOList(List<Reward> rewardList) {
        return rewardList.stream().map(RewardMapper::mapToDTO).collect(Collectors.toList());
    }

    public static ReadRewardResponseDTO mapToDTO(Reward reward) {
        return new ReadRewardResponseDTO(reward);
    }
}
