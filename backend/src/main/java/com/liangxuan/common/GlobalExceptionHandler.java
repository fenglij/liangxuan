package com.liangxuan.common;

import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Result<Void> handleBizException(BizException exception) {
        Result<Void> result = Result.failure(exception.getCode(), exception.getMessage());
        log.warn("Business request failed, traceId={}, code={}, message={}", result.traceId(), exception.getCode(), exception.getMessage());
        return result;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("；"));
        return Result.failure(400, message);
    }

    @ExceptionHandler({ConstraintViolationException.class, HttpMessageNotReadableException.class, IllegalArgumentException.class})
    public Result<Void> handleBadRequest(Exception exception) {
        return Result.failure(400, exception.getMessage() == null ? "请求参数不正确" : exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleUnknown(Exception exception) {
        Result<Void> result = Result.failure(500, "服务暂时不可用，请稍后重试");
        log.error("Unhandled request failure, traceId={}", result.traceId(), exception);
        return result;
    }
}
