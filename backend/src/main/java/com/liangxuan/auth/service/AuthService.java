package com.liangxuan.auth.service;

import com.liangxuan.auth.dto.AuthRequest;
import com.liangxuan.auth.dto.AuthResponse;
public interface AuthService {
    AuthResponse register(AuthRequest request);

    AuthResponse login(AuthRequest request);
}
