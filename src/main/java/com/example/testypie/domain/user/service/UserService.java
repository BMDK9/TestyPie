package com.example.testypie.domain.user.service;

import com.example.testypie.domain.user.dto.request.LoginRequestDTO;
import com.example.testypie.domain.user.dto.request.SignUpRequestDTO;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.domain.user.repository.UserRepository;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Value("${default.image.address}")
    private String defaultProfileImageUrl;

    public void signup(SignUpRequestDTO signUpRequestDTO) {

        String account = signUpRequestDTO.account();
        String password = passwordEncoder.encode(signUpRequestDTO.password());
        String email = signUpRequestDTO.email();
        String nickname = signUpRequestDTO.nickname();
        String description = signUpRequestDTO.description();

        if (userRepository.findByAccount(account).isPresent()) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SIGNUP_DUPLICATED_USER_ACCOUNT);
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SIGNUP_DUPLICATED_USER_EMAIL);
        }

        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SIGNUP_DUPLICATED_USER_NICKNAME);
        }

        User user =
                User.builder()
                        .account(account)
                        .password(password)
                        .email(email)
                        .nickname(nickname)
                        .description(description)
                        .fileUrl(defaultProfileImageUrl)
                        .build();

        userRepository.save(user);
    }

    public void login(LoginRequestDTO req) {
        String account = req.account();
        String password = req.password();

        User user =
                userRepository
                        .findByAccount(account)
                        .orElseThrow(
                                () -> new GlobalExceptionHandler.CustomException(ErrorCode.LOGIN_INVALID_ACCOUNT));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.LOGIN_INVALID_PASSWORD);
        }
    }

    public User findUser(String account) {
        return userRepository
                .findByAccount(account)
                .orElseThrow(
                        () -> new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_USER_NOT_FOUND));
    }

    public void signOut(User user) {
        Long userId = user.getId();
        deleteUser(userId);
    }

    private void deleteUser(Long userId) {
        userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new GlobalExceptionHandler.CustomException(ErrorCode.DELETE_USER_NOT_FOUND));
        userRepository.deleteById(userId);
    }
}
