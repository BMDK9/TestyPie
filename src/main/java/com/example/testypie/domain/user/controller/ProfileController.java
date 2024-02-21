package com.example.testypie.domain.user.controller;

import com.example.testypie.domain.feedback.entity.Feedback;
import com.example.testypie.domain.user.dto.request.RatingStarRequestDTO;
import com.example.testypie.domain.user.dto.request.UpdateProfileRequestDTO;
import com.example.testypie.domain.user.dto.response.AverageRatingResponseDTO;
import com.example.testypie.domain.user.dto.response.ParticipatedProductResponseDTO;
import com.example.testypie.domain.user.dto.response.ReadProfileResponseDTO;
import com.example.testypie.domain.user.dto.response.RegisteredProductResponseDTO;
import com.example.testypie.domain.user.dto.response.ResponseMessageDTO;
import com.example.testypie.domain.user.dto.response.UpdateProfileResponseDTO;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.domain.user.service.UserInfoService;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import com.example.testypie.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ProfileController {

    private final UserInfoService userInfoService;

    @GetMapping("/{account}")
    public ModelAndView getProfile(@PathVariable String account, Model model) {

        User user = userInfoService.findProfile(account);
        ReadProfileResponseDTO res = ReadProfileResponseDTO.of(user);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        model.addAttribute("profile", res);

        return modelAndView;
    }

    @PatchMapping(
            value = "/{account}/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProfile(
            @PathVariable String account,
            @Valid @RequestPart(value = "req", required = false) UpdateProfileRequestDTO req,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            User user = userDetails.getUser();
            UpdateProfileResponseDTO res =
                    userInfoService.updateProfile(account, req, multipartFile, user);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException e) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_PROFILE_USER_NOT_FOUND);
        } catch (Exception e) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.UPDATE_PROFILE_BAD_REQUEST);
        }
    }

    @GetMapping("/{account}/registeredProducts")
    public ResponseEntity<List<RegisteredProductResponseDTO>> getRegisteredProducts(
            @PathVariable String account, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        userInfoService.checkSameUser(account, user.getAccount());
        List<RegisteredProductResponseDTO> res = userInfoService.getUserProducts(account);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("{account}/participatedProducts")
    public ResponseEntity<List<ParticipatedProductResponseDTO>> getParticipatedProducts(
            @PathVariable String account, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userInfoService.checkSameUser(account, userDetails.getUsername());

        List<ParticipatedProductResponseDTO> res = userInfoService.getUserParticipatedProducts(account);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("{account}/averageStarRating")
    public ResponseEntity<AverageRatingResponseDTO> getAverageStarRating(
            @PathVariable String account, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userInfoService.checkSameUser(account, userDetails.getUsername());

        double averageRating = userInfoService.getAverageRating(account);

        AverageRatingResponseDTO res = new AverageRatingResponseDTO(averageRating);
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/{account}/ratingStar/{productId}/{feedbackId}")
    public ResponseEntity<ResponseMessageDTO> evaluateFeedback(
            @PathVariable String account,
            @PathVariable Long productId,
            @PathVariable Long feedbackId,
            @ModelAttribute RatingStarRequestDTO req
            /*@AuthenticationPrincipal UserDetailsImpl userDetails*/ ) {

        //    userInfoService.checkSameUser(account, userDetails.getUsername());
        Feedback feedback = userInfoService.checkFeedback(productId, feedbackId);
        userInfoService.assignRatingStarAtFeedback(feedback, req);
        String message = String.format("별점이 %.1f점 매겨졌습니다.", req.rating());

        return ResponseEntity.ok().body(new ResponseMessageDTO(message, HttpStatus.OK.value()));
    }

    //  랜덤로직
    //  @GetMapping("/{account}/lotto/{productId}")
    //  public ResponseEntity<LottoResponseDTO> chooseRewardUser(
    //      @PathVariable String account,
    //      @PathVariable Long productId,
    //      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    //
    //    userInfoService.checkSameUser(account, userDetails.getUsername());
    //    List<User> userList = userInfoService.drawUsers(productId);
    //
    //    return ResponseEntity.ok()
    //        .body(
    //            new LottoResponseDTO(
    //                userList.stream().map(User::getAccount).collect(Collectors.toList())));
    //  }
}
