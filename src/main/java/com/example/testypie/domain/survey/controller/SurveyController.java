package com.example.testypie.domain.survey.controller;

import com.example.testypie.domain.survey.dto.request.CreateSurveyRequestDTO;
import com.example.testypie.domain.survey.dto.response.CreateSurveyResponseDTO;
import com.example.testypie.domain.survey.dto.response.ReadSurveyResponseDTO;
import com.example.testypie.domain.survey.service.SurveyService;
import com.example.testypie.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/category/{parentCategoryName}/{childCategoryId}/products/{productId}/survey")
    public ResponseEntity<ReadSurveyResponseDTO> getSurvey(
            @PathVariable Long productId,
            @PathVariable Long childCategoryId,
            @PathVariable String parentCategoryName) {

        ReadSurveyResponseDTO res =
                surveyService.getSurvey(productId, childCategoryId, parentCategoryName);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/category/{parentCategoryName}/{childCategoryId}/products/{productId}/surveys")
    public ResponseEntity<CreateSurveyResponseDTO> addSurvey(
            @RequestBody CreateSurveyRequestDTO req,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productId,
            @PathVariable Long childCategoryId,
            @PathVariable String parentCategoryName) {

        CreateSurveyResponseDTO res =
                surveyService.addSurvey(
                        req, productId, userDetails.getUser(), childCategoryId, parentCategoryName);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
