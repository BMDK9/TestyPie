package com.example.testypie.domain.user.service;

import com.example.testypie.domain.feedback.entity.Feedback;
import com.example.testypie.domain.feedback.service.FeedbackService;
import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.product.service.ProductService;
import com.example.testypie.domain.user.dto.request.RatingStarRequestDTO;
import com.example.testypie.domain.user.dto.request.UpdateProfileRequestDTO;
import com.example.testypie.domain.user.dto.response.ParticipatedProductResponseDTO;
import com.example.testypie.domain.user.dto.response.RegisteredProductResponseDTO;
import com.example.testypie.domain.user.dto.response.UpdateProfileResponseDTO;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.domain.user.repository.UserRepository;
import com.example.testypie.domain.util.S3Util;
import com.example.testypie.domain.util.S3Util.FilePath;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRepository userRepository;
    private final FeedbackService feedbackService;
    private final ProductService productService;
    private final PasswordEncoder passwordEncoder;
    private final S3Util s3Util;
    private final Random random = new Random();

    @Value("${default.image.address}")
    private String defaultProfileImageUrl;

    @Transactional
    public UpdateProfileResponseDTO updateProfile(
            String account, UpdateProfileRequestDTO req, MultipartFile multipartfile, User user) {

        User profileUser =
                userRepository
                        .findByAccount(account)
                        .orElseThrow(
                                () -> new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_USER_NOT_FOUND));

        getUserValid(profileUser, user);

        if (req.password() != null && !req.password().isBlank()) {
            String password = passwordEncoder.encode(req.password());
            profileUser.updatePassword(password);
        }

        String fileUrl = profileUser.getFileUrl();

        if (multipartfile != null && !multipartfile.isEmpty()) {
            if (!fileUrl.equals(defaultProfileImageUrl)) {
                s3Util.deleteFile(fileUrl, FilePath.PROFILE);
            }
            fileUrl = s3Util.uploadFile(multipartfile, FilePath.PROFILE);
        } else {
            fileUrl = defaultProfileImageUrl;
        }

        profileUser.update(req, fileUrl);
        return new UpdateProfileResponseDTO(
                profileUser.getAccount(),
                profileUser.getNickname(),
                profileUser.getEmail(),
                profileUser.getDescription(),
                profileUser.getFileUrl());
    }

    public User findProfile(String account) {
        return userRepository
                .findByAccount(account)
                .orElseThrow(
                        () ->
                                new GlobalExceptionHandler.CustomException(
                                        ErrorCode.SELECT_PROFILE_USER_NOT_FOUND));
    }

    public void checkSameUser(String profileAccount, String userAccount) {
        User profileUser = findProfile(profileAccount);
        User user = findProfile(userAccount);
        if (!profileUser.equals(user)) {
            throw new GlobalExceptionHandler.CustomException(
                    ErrorCode.PROFILE_USER_INVALID_AUTHORIZATION);
        }
    }

    public List<RegisteredProductResponseDTO> getUserProducts(String account) {
        List<Product> productList = userRepository.getUserProductsOrderByCreatedAtDesc(account);
        return productList.stream().map(RegisteredProductResponseDTO::of).collect(Collectors.toList());
    }

    public List<ParticipatedProductResponseDTO> getUserParticipatedProducts(String account) {
        return userRepository.getUserFeedbacksDtoIncludingProductInfo(account);
    }

    public Feedback checkFeedback(Long productId, Long feedbackId) {

        Feedback feedback = checkAndGetFeedback(productId, feedbackId);
        checkFeedbackLocation(feedback, productId);

        return feedback;
    }

    private Feedback checkAndGetFeedback(Long productId, Long feedbackId) {
        return feedbackService.checkFeedback(productId, feedbackId);
    }

    private void checkFeedbackLocation(Feedback feedback, Long productId) {

        if (!feedback.getProduct().getId().equals(productId)) {
            throw new GlobalExceptionHandler.CustomException(
                    ErrorCode.PROFILE_PRODUCT_FEEDBACK_ID_NOT_FOUND);
        }
    }

    public void assignRatingStarAtFeedback(Feedback feedback, RatingStarRequestDTO req) {

        feedbackService.assignFeedbackRatingStar(feedback, req);
    }

    public double getAverageRating(String account) {

        User user = findProfile(account);

        return feedbackService.checkAverageRating(user);
    }

    // 랜덤로직
    //  public List<User> drawUsers(Long productId) {
    //
    //    Product product = productService.checkProduct(productId);
    //    List<Reward> rewardList = product.getRewardList();
    //
    //    if (rewardList == null || rewardList.isEmpty()) {
    //      return List.of();
    //    }
    //
    //    return getRandomUserList(productId, rewardList);
    //  }
    //  private List<User> getRandomUserList(Long productId, List<Reward> rewardList) {
    //
    //    List<User> userList = userRepository.findAllFeedbackUsersByProductId(productId);
    //
    //    for (Reward reward : rewardList) {
    //      Integer rewardCnt = Math.toIntExact(reward.getItemSize());
    //    }
    //
    //    int totalRewardSize =
    //        rewardList.stream().mapToInt(reward -> Math.toIntExact(reward.getItemSize())).sum();
    //    List<Integer> randomIndexes = getRandomIndexes(totalRewardSize, userList.size());
    //
    //    return randomIndexes.stream().distinct().map(userList::get).collect(Collectors.toList());
    //  }
    //
    //  private List<Integer> getRandomIndexes(int totalSize, int maxSize) {
    //    return random.ints(totalSize, 0, maxSize).boxed().toList();
    //  }

    private void getUserValid(User profileUser, User user) {
        if (!profileUser.getId().equals(user.getId())) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.UPDATE_USER_INVALID_AUTHORIZATION);
        }
    }
}
