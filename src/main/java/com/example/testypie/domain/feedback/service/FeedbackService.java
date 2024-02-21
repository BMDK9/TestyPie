package com.example.testypie.domain.feedback.service;

import com.example.testypie.domain.category.entity.Category;
import com.example.testypie.domain.category.service.CategoryService;
import com.example.testypie.domain.feedback.dto.request.CreateFeedbackDetailsRequestDTO;
import com.example.testypie.domain.feedback.dto.request.CreateFeedbackRequestDTO;
import com.example.testypie.domain.feedback.dto.response.CreateFeedbackResponseDTO;
import com.example.testypie.domain.feedback.entity.Feedback;
import com.example.testypie.domain.feedback.entity.FeedbackDetails;
import com.example.testypie.domain.feedback.repository.FeedbackRepository;
import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.product.service.ProductService;
import com.example.testypie.domain.survey.entity.Survey;
import com.example.testypie.domain.survey.service.SurveyService;
import com.example.testypie.domain.user.dto.request.RatingStarRequestDTO;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SurveyService surveyService;

    public CreateFeedbackResponseDTO createFeedback(
            CreateFeedbackRequestDTO req,
            Long productId,
            User user,
            Long childCategoryId,
            String parentCategoryName) {

        Category category = categoryService.checkCategory(childCategoryId, parentCategoryName);
        Product product = productService.checkProduct(productId);
        Survey survey = surveyService.checkSurveyById(product.getSurvey().getId());

        if (product.getUser().getId().equals(user.getId())) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.CREATE_FEEDBACK_NOT_ALLOWED);
        }

        if (LocalDateTime.now().isAfter(product.getClosedAt())) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.CREATE_FEEDBACK_PASSED_DUE_DATE);
        }

        if (!category.getId().equals(product.getCategory().getId())) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_PRODUCT_CATEGORY_NOT_FOUND);
        }

        boolean hasSubmitted = feedbackRepository.existsByUserAndSurvey(user, survey);
        if (hasSubmitted) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.CREATE_FEEDBACK_ALREADY_SUBMITTED);
        }

        Feedback feedback = Feedback.builder().user(user).product(product).survey(survey).build();

        List<FeedbackDetails> detailslist = new ArrayList<>();
        for (CreateFeedbackDetailsRequestDTO feedbackDetailsDTO : req.feedbackDetailsList()) {
            FeedbackDetails feedbackDetails =
                    FeedbackDetails.builder()
                            .feedback(feedback)
                            .response(feedbackDetailsDTO.response())
                            .build();

            detailslist.add(feedbackDetails);
        }

        feedback.setFeedbackDetailsList(detailslist);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return new CreateFeedbackResponseDTO(savedFeedback);
    }

    public double checkAverageRating(User user) {

        return feedbackRepository
                .findAverageScoreByUserId(user.getId())
                .orElseThrow(
                        () ->
                                new GlobalExceptionHandler.CustomException(
                                        ErrorCode.PROFILE_AVERAGE_FEEDBACK_RATING_NULL));
    }

    public Feedback checkFeedback(Long productId, Long feedbackId) {

        return feedbackRepository
                .findByProductIdAndId(productId, feedbackId)
                .orElseThrow(
                        () -> new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_FEEDBACK_NOT_FOUND));
    }

    public void assignFeedbackRatingStar(Feedback feedback, RatingStarRequestDTO req) {

        feedback.assignRating(req);
        feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacksByProductId(Long productId) {
        return feedbackRepository
                .findAllFeedbacksByProductId(productId)
                .orElseThrow(
                        () -> new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_FEEDBACK_NOT_FOUND));
    }
}
