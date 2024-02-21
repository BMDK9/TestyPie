package com.example.testypie.domain.user.controller;

import static com.example.testypie.global.jwt.JwtUtil.*;

import com.example.testypie.domain.user.dto.request.LoginRequestDTO;
import com.example.testypie.domain.user.dto.request.SignUpRequestDTO;
import com.example.testypie.domain.user.dto.response.ResponseMessageDTO;
import com.example.testypie.domain.user.entity.RefreshToken;
import com.example.testypie.domain.user.entity.User;
import com.example.testypie.domain.user.service.RefreshTokenService;
import com.example.testypie.domain.user.service.UserService;
import com.example.testypie.global.exception.ErrorCode;
import com.example.testypie.global.exception.GlobalExceptionHandler;
import com.example.testypie.global.jwt.JwtUtil;
import com.example.testypie.global.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final RefreshTokenService refreshTokenService;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/api/users/signup")
    public ResponseEntity<ResponseMessageDTO> signup(
            @RequestBody @Valid SignUpRequestDTO req, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }

        userService.signup(req);

        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new ResponseMessageDTO("회원가입 성공", HttpStatus.CREATED.value()));
    }

    // 로그인
    @PostMapping("/api/users/login")
    public ResponseEntity<ResponseMessageDTO> login(
            @RequestBody @Valid LoginRequestDTO req,
            BindingResult bindingResult,
            HttpServletResponse res) {

        if (bindingResult.hasErrors()) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.SELECT_USER_NOT_FOUND);
        }

        userService.login(req);

        res.setHeader(AUTHORIZATION_HEADER, jwtUtil.createAccessToken(req.account()));
        String refreshTokenValue = jwtUtil.createRefreshToken(req.account());
        RefreshToken refreshToken =
                RefreshToken.builder().account(req.account()).tokenValue(refreshTokenValue).build();

        Cookie cookie = new Cookie(REFRESH_AUTHORIZATION_HEADER, refreshTokenValue);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);

        refreshTokenService.addRefreshToken(refreshToken);

        return ResponseEntity.ok().body(new ResponseMessageDTO("로그인 성공", HttpStatus.OK.value()));
    }

    @GetMapping("/api/user/profile")
    public Map<String, String> getUserProfile(
            @RequestHeader("Authorization") String authorizationHeader) {

        Map<String, String> response = new HashMap<>();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            if (jwtUtil.validateToken(token)) {
                Claims info = jwtUtil.getUserInfoFromToken(token);
                String account = info.getSubject();
                response.put("account", account);

                return response;
            }
        }

        response.put("error", "유효하지 않은 토큰입니다.");

        return response;
    }

    @DeleteMapping("/api/users/signout")
    public ResponseEntity<ResponseMessageDTO> signOut(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        userService.signOut(user);

        return ResponseEntity.ok(new ResponseMessageDTO("유저가 탈퇴했습니다.", 200));
    }

    @PostMapping("/api/users/refresh")
    public ResponseEntity<?> refresh(
            @CookieValue(REFRESH_AUTHORIZATION_HEADER) String token, HttpServletResponse res) {

        RefreshToken refreshToken = refreshTokenService.findToken(token);

        if (refreshToken == null) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        Claims claims = jwtUtil.getUserInfoFromToken(refreshToken.getTokenValue());

        if (claims == null) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        String account = claims.getSubject();
        User user = userService.findUser(account);
        res.setHeader(AUTHORIZATION_HEADER, jwtUtil.createAccessToken(user.getAccount()));

        return ResponseEntity.ok().body(new ResponseMessageDTO("토큰이 성공적으로 생성됐습니다.", 200));
    }

    @DeleteMapping("/api/users/logout")
    public ResponseEntity<?> logout(
            @CookieValue(value = "Refresh-Authorization", required = false) String token,
            HttpServletResponse res) {
        logger.info("리프레시 토큰: " + token);

        if (token == null || token.isEmpty()) {
            Cookie cookie = new Cookie("Refresh-Authorization", null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            res.addCookie(cookie);

            return ResponseEntity.ok().body(new ResponseMessageDTO("로그아웃 되었습니다.", 200));
        }

        RefreshToken refreshToken = refreshTokenService.findToken(token);

        if (refreshToken == null) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        Claims claims = jwtUtil.getUserInfoFromToken(refreshToken.getTokenValue());

        if (claims == null) {
            throw new GlobalExceptionHandler.CustomException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        userService.findUser(claims.getSubject());
        refreshTokenService.deleteRefreshToken(refreshToken);

        Cookie cookie = new Cookie("Refresh-Authorization", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        res.addCookie(cookie);

        return ResponseEntity.ok().body(new ResponseMessageDTO("토큰이 성공적으로 삭제됐습니다.", 200));
    }

    @GetMapping("/api/user/token")
    public ResponseEntity<?> getUserAuth(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.findUser(userDetails.getUser().getAccount());

        return ResponseEntity.ok().body(new ResponseMessageDTO("존재하는 유저입니다.", 200));
    }
}
