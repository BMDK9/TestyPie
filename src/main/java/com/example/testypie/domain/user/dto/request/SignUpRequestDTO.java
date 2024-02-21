package com.example.testypie.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequestDTO(
        @NotBlank @Pattern(regexp = "^[a-z0-9]{6,12}$", message = "6~12자 사이의 소문자, 숫자") String account,
        @NotBlank @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$", message = "8~16자 사이의 소문자, 대문자, 숫자")
                String password,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^[a-zA-Z0-9가-힣 ]{1,20}$", message = "1~20자 사이의 소문자, 대문자, 숫자")
                String nickname,
        @Pattern(regexp = "^[a-zA-Z0-9가-힣 ]{0,20}$", message = "20자 미만의 소문자, 대문자, 숫자")
                String description) {}
