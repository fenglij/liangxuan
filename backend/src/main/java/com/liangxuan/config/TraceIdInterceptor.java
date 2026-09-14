package com.liangxuan.config;

import com.liangxuan.common.SnowflakeIdGenerator;
import com.liangxuan.common.TraceIdContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TraceIdInterceptor implements HandlerInterceptor {
    private final SnowflakeIdGenerator idGenerator;

    public TraceIdInterceptor(SnowflakeIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Long traceId = parseTraceId(request.getHeader(TraceIdContext.HEADER_NAME));
        if (traceId == null) traceId = idGenerator.nextId();
        TraceIdContext.setTraceId(traceId);
        response.setHeader(TraceIdContext.HEADER_NAME, traceId.toString());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) {
        TraceIdContext.clear();
    }

    private Long parseTraceId(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}