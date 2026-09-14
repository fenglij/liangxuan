package com.liangxuan.auth.dto;

public record AuthResponse(String token, UserView user) {
    public record UserView(Long id, String account) {
    }
}
