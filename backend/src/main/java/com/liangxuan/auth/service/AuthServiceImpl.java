package com.liangxuan.auth.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.liangxuan.auth.dto.AuthRequest;
import com.liangxuan.auth.dto.AuthResponse;
import com.liangxuan.common.BizException;
import com.liangxuan.user.entity.User;
import com.liangxuan.user.mapper.UserMapper;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Pattern ACCOUNT_PATTERN = Pattern.compile("^(?:[^\\s@]+@[^\\s@]+\\.[^\\s@]+|1[3-9]\\d{9})$");

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public AuthResponse register(AuthRequest request) {
        String account = normalizeAccount(request.account());
        validateAccount(account);
        if (userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account)) != null) {
            throw new BizException(409, "账号已注册");
        }
        User user = new User();
        user.setAccount(account);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.insert(user);
        return toResponse(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        String account = normalizeAccount(request.account());
        validateAccount(account);
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
        if (user == null || !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BizException(401, "账号或密码错误");
        }
        return toResponse(user);
    }

    private AuthResponse toResponse(User user) {
        return new AuthResponse(jwtService.createToken(user.getId()), new AuthResponse.UserView(user.getId(), user.getAccount()));
    }

    private String normalizeAccount(String account) {
        return account.trim().toLowerCase(Locale.ROOT);
    }

    private void validateAccount(String account) {
        if (!ACCOUNT_PATTERN.matcher(account).matches()) {
            throw new BizException(400, "账号必须是有效的邮箱或手机号");
        }
    }
}
