package com.example.testypie.domain.user.controller;


import com.example.testypie.domain.feedback.entity.Feedback;
import com.example.testypie.domain.user.dto.*;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.domain.user.service.ProfileService;
import com.example.testypie.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ProfileController {

    private final ProfileService profileService;

    //프로필 조회
    @GetMapping("/{account}")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable String account) {
        User user = profileService.findProfile(account);
        return ResponseEntity.ok().body(ProfileResponseDTO.of(user));
    }

    //프로필 수정
    @PatchMapping("/{account}")
    public ResponseEntity<?> updateProfile(@PathVariable String account,
                                           @RequestBody ProfileRequestDTO req) {

        try {
            ProfileResponseDTO res = profileService.updateProfile(account, req);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new MessageDTO("업데이트에 실패했습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 2024-01-12
    // product 등록 이력 조회
    // user가 자신의 프로필에서 product 등록이력을 조회하는 서비스입니다.
    // 로직은 아래와 같습니다.
    // 1. 유효한 사용자인지 체크합니다.
    // 2. 유효한 사용자일 경우 등록한 product를 전부 가져옵니다.
    // 3. RegisteredProductResponstDTO객체는 productId, product title, createdAt, closedAt를 가집니다.
    @GetMapping("/{account}/registeredProducts")
    public ResponseEntity<List<RegisteredProductResponseDTO>> getRegisteredProducts(@PathVariable String account, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 1.
        profileService.checkSameUser(account, userDetails.getUsername());

        // 2, 3.
        List<RegisteredProductResponseDTO> res = profileService.getUserProducts(account);

        return ResponseEntity.ok().body(res);
    }

    // 2024-01-15
    // product 참여 이력 조회
    // user가 자신의 프로필에서 product 등록 이력을 조회하는 서비스입니다.
    // 로직은 아래와 같습니다.
    // 1. 유효한 사용자인지 체크합니다.
    // 2. 해당 유저가 작성한 feedback을 모두 조회합니다.
    // 3. 조회한 feedback들에서 product이름, **feedback별점(미구현), feedback 작성일시를 가져옵니다.
    @GetMapping("{account}/participatedProducts")
    public ResponseEntity<List<ParticipatedProductResponseDTO>> getParticipatedProducts(@PathVariable String account, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 1.
        profileService.checkSameUser(account, userDetails.getUsername());

        // 2. 3.
        List<ParticipatedProductResponseDTO> res = profileService.getUserFeedbacks(account);

        return ResponseEntity.ok().body(res);
    }


    // 2024-01-17
    // 테스트 유저가 지금 까지 받은 점수를 평균내서 보여주는 기능입니다.
    // 1. 유효한 사용자인지 확인합니다.
    // 2. 자신이 작성한 모든 feedback을 조회한후 rating의 평균점수를 계산해옵니다.
    // 3. 결과는 AverageRatingResponseDTO에 담겨 보내집니다.
    @GetMapping("{account}/averageStarRating")
    public ResponseEntity<AverageRatingResponseDTO> getAverageStarRating(@PathVariable String account,
                                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 1.
        profileService.checkSameUser(account, userDetails.getUsername());

        // 2.
        double averageRating = profileService.getAverageRating(account);

        // 3.
        AverageRatingResponseDTO res = new AverageRatingResponseDTO(averageRating);
        return ResponseEntity.ok().body(res);
    }

    // 2024-01-16
    // product 등록자가 tester의 feedback에 별점을 매기는 메서드입니다.
    // 로직은 아래와 같습니다.
    // 1. product 등록자와 userDetails의 유저가 같은지 확인합니다.
    // 2. productId와 FeedbackId가 유효한지 확인합니다.
    // 3. assignRatingStarAfFeedback(Feedback, ratingstarrequestDTO)로 별점(rating)을 매깁니다. 예) 별점 3점 :: assignRating(RatingStar.THREE) -> rating = 3.0;
    // 4. 별점을 double rating column에 넣습니다.
    @PostMapping("/{account}/ratingStar/{productId}/{feedbackId}")
    public ResponseEntity<MessageDTO> assignRatingStarToFeedback(@PathVariable String account, @PathVariable Long productId,
                                                                 @PathVariable Long feedbackId,
                                                                 @Valid @RequestBody RatingStarRequestDTO req,
                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 1.
        profileService.checkSameUser(account, userDetails.getUsername());

        // 2.
        Feedback feedback = profileService.getValidFeedback(productId, feedbackId);

        // 3
        profileService.assignRatingStarAtFeedback(feedback, req);

        //4.
        String message = String.format("별점이 %.1f점 매겨졌습니다.", req.rating());
        return ResponseEntity.ok().body(new MessageDTO(message, HttpStatus.OK.value()));
    }

    // 2024-01-17
    // tester가 지금까지 받은 reward 이력을 확인하는 메서드입니다.
    // 로직은 아래와 같습니다.
    // 1. pathVariable accountId의 유저와 userDetails의 유저가 같은지 확인합니다.
    // 2. User id를 통해 user가 받은 reward를 list로 받습니다.
    // 3. list를 userRewardResponseDTO에 담습니다.

    // 2024-01-17
    // product 등록자가 tester중에서 추첨합니다.
    // 로직은 아래와 같습니다.
    // 1. product 등록자와 userdetails유저가 같은지 확인합니다.
    // 2. product의 feedback을 만든 유저들 중에서 5점을 받은 유저들중에서 추첨을 한번 돌립니다.
    // 3. 추첨된 유저를 제외한 feedback을 만든 전체 유저들중에서 추첨을 돌립니다.
    // 4. 0점을 받은 유저들은 제외합니다.
    // 5. reward에 user를 넣습니다.
    @GetMapping("/{account}/lotto/{productId}")
    public ResponseEntity<LottoResponseDTO> chooseRewardUser(@PathVariable String account,
                                                             @PathVariable Long productId,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 1.
        profileService.checkSameUser(account, userDetails.getUsername());

        // 2. 3. 4. 5. drawUsers에는 5점을 받은 사람들 중에서 한명 뽑고 나머지에서 추첨을 돌립니다.
        List<User> userList = profileService.drawUsers(productId);

        return ResponseEntity.ok().body(new LottoResponseDTO(userList));
    }
}
