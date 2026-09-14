package com.liangxuan.common;

public record Result<T>(int code, String message, T data, Long traceId) {
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data, TraceIdContext.getTraceId());
    }

    public static <T> Result<T> failure(int code, String message) {
        return new Result<>(code, message, null, TraceIdContext.getTraceId());
    }
}
