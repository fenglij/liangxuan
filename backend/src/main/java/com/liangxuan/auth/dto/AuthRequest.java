package com.liangxuan.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank(message = "账号不能为空") String account,
        @NotBlank(message = "密码不能为空") @Size(min = 8, max = 72, message = "密码长度必须为 8 至 72 位") String password) {
}
