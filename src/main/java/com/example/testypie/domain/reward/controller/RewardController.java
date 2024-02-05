package com.example.testypie.domain.reward.controller;

import com.example.testypie.domain.reward.dto.response.DeleteRewardResponseDTO;
import com.example.testypie.domain.reward.dto.response.ReadRewardResponseDTO;
import com.example.testypie.domain.reward.service.RewardService;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.domain.user.service.UserInfoService;
import com.example.testypie.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RewardController {

  private final RewardService rewardService;
  private final UserInfoService userInfoService;

  @GetMapping("/reward")
  public ResponseEntity<List<ReadRewardResponseDTO>> getRewardList(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    List<ReadRewardResponseDTO> resList = rewardService.getRewardList(userDetails.getUser());
    return ResponseEntity.ok(resList);
  }

  @GetMapping("/reward/{account}")
  public ResponseEntity<List<ReadRewardResponseDTO>> getReward(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String account) {

    userInfoService.checkSameUser(account, userDetails.getUsername());
    List<ReadRewardResponseDTO> resList = rewardService.getReward(account);
    return ResponseEntity.ok(resList);
  }

  @DeleteMapping("/reward/{account}/{reward_id}")
  public ResponseEntity<DeleteRewardResponseDTO> deleteReward(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long reward_id,
      @PathVariable String account) {
    userInfoService.checkSameUser(account, userDetails.getUsername());
    User user = userDetails.getUser();
    DeleteRewardResponseDTO res = rewardService.deleteReward(user, reward_id);
    return ResponseEntity.ok().body(res);
  }
}
