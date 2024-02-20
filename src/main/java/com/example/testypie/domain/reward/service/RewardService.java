package com.example.testypie.domain.reward.service;

import com.example.testypie.domain.reward.dto.response.DeleteRewardResponseDTO;
import com.example.testypie.domain.reward.entity.Reward;
import com.example.testypie.domain.reward.repository.RewardRepository;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.domain.user.service.UserInfoService;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;
    private final UserInfoService userInfoService;

    //  사용 예정 (랜덤 로직)
    //  public List<ReadRewardResponseDTO> getRewardList(User user) {
    //
    //    userInfoService.findProfile(user.getAccount());
    //
    //    return rewardRepository.findAll().stream().map(ReadRewardResponseDTO::new).toList();
    //  }

    public DeleteRewardResponseDTO deleteReward(User user, Long reward_Id) {

        Reward reward = getUserReward(user, reward_Id);
        rewardRepository.delete(reward);

        return DeleteRewardResponseDTO.of(reward);
    }

    private Reward getUserReward(User user, Long rewardId) {

        Reward reward = findReward(rewardId);

        if (!user.getId().equals(reward.getUser().getId())) {
            throw new GlobalExceptionHandler.CustomException(
                    ErrorCode.SELECT_USER_REWARD_INVALID_AUTHORIZATION);
        }
        return reward;
    }

    public Reward findReward(Long reward_Id) {

        return rewardRepository
                .findById(reward_Id)
                .orElseThrow(
                        () -> new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_REWARD_NOT_FOUND));
    }
}
