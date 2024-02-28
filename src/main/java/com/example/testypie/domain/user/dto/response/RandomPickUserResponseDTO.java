package com.example.testypie.domain.user.dto.response;

import java.util.Map;

public record RandomPickUserResponseDTO(Map<Long, String> randomPickedUserMap) {}
