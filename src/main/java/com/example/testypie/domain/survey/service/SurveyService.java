package com.example.testypie.domain.survey.service;

import com.example.testypie.domain.category.entity.Category;
import com.example.testypie.domain.category.service.CategoryService;
import com.example.testypie.domain.product.entity.Product;
import com.example.testypie.domain.product.service.ProductService;
import com.example.testypie.domain.survey.dto.request.CreateOptionRequestDTO;
import com.example.testypie.domain.survey.dto.request.CreateQuestionRequestDTO;
import com.example.testypie.domain.survey.dto.request.CreateSurveyRequestDTO;
import com.example.testypie.domain.survey.dto.response.CreateSurveyResponseDTO;
import com.example.testypie.domain.survey.dto.response.ReadSurveyResponseDTO;
import com.example.testypie.domain.survey.entity.Option;
import com.example.testypie.domain.survey.entity.Question;
import com.example.testypie.domain.survey.entity.QuestionType;
import com.example.testypie.domain.survey.entity.Survey;
import com.example.testypie.domain.survey.repository.SurveyRepository;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Transactional
    public CreateSurveyResponseDTO addSurvey(
            CreateSurveyRequestDTO req,
            Long productId,
            User user,
            Long childCategoryId,
            String parentCategoryName) {

        Category category = categoryService.checkCategory(childCategoryId, parentCategoryName);
        Product product = productService.getUserProduct(productId, user);

        if (!category.getId().equals(product.getCategory().getId())) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_PRODUCT_NOT_FOUND);
        }

        Survey survey =
                Survey.builder()
                        .user(user)
                        .title(req.title().trim()) // 공백 제거 후 설정
                        .product(product)
                        .build();

        List<Question> questions = new ArrayList<>();
        for (CreateQuestionRequestDTO questionDTO : req.questionList()) {

            Question question =
                    Question.builder()
                            .text(questionDTO.text().trim()) // 공백 제거 후 설정
                            .questionType(questionDTO.questionType())
                            .survey(survey)
                            .build();

            if (question.getQuestionType() == QuestionType.MULTI_CHOICE) {
                for (CreateOptionRequestDTO optionDTO : questionDTO.optionList()) {

                    Option option =
                            Option.builder()
                                    .text(optionDTO.text().trim())
                                    .question(question)
                                    .build(); // 공백 제거 후 설정
                    question.getOptionList().add(option);
                }
            }
            questions.add(question);
        }

        survey.setQuestionList(questions);
        Survey savedSurvey = surveyRepository.save(survey);

        product.setSurvey(savedSurvey);

        return new CreateSurveyResponseDTO(savedSurvey);
    }

    @Transactional(readOnly = true)
    public ReadSurveyResponseDTO getSurvey(
            Long productId, Long childCategoryId, String parentCategoryName) {

        Category category = categoryService.checkCategory(childCategoryId, parentCategoryName);
        Product product = productService.checkProduct(productId);

        if (!category.getId().equals(product.getCategory().getId())) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_PRODUCT_CATEGORY_NOT_FOUND);
        }

        checkSurveyUser(product.getSurvey(), productId);

        return ReadSurveyResponseDTO.of(product.getSurvey());
    }

    @Transactional(readOnly = true)
    public Survey checkSurveyById(Long id) {
        return surveyRepository
                .findById(id)
                .orElseThrow(
                        () -> new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_SURVEY_NOT_FOUND));
    }

    public void checkSurveyUser(Survey survey, Long productId) {
        if (!survey.getProduct().getId().equals(productId)) { // product 생성자와 유저가 일치하지 않으면
            throw new GlobalExceptionHandler.CustomException(
                    ErrorCode.SELECT_SURVEY_INVALID_AUTHORIZATION);
        }
    }
}
