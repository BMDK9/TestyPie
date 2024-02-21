package com.example.testypie.domain.reward.repository;

import com.example.testypie.domain.reward.entity.Reward;
import com.example.testypie.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {
    List<Reward> findAllByUser(User user);
}
