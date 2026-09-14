package com.liangxuan.auth.controller;

import com.liangxuan.auth.dto.AuthRequest;
import com.liangxuan.auth.dto.AuthResponse;
import com.liangxuan.auth.service.AuthService;
import com.liangxuan.common.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Result<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        return Result.success(authService.register(request));
    }

    @PostMapping("/login")
    public Result<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return Result.success(authService.login(request));
    }
}
