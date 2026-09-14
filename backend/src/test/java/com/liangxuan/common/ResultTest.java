package com.liangxuan.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

class ResultTest {
    @Test
    void reusesTheRequestTraceIdForSuccessAndFailureResponses() {
        MDC.put(TraceIdContext.MDC_KEY, "2098433494032613377");
        try {
            Result<String> success = Result.success("ok");
            Result<Void> failure = Result.failure(400, "bad request");

            assertThat(success.traceId()).isEqualTo(2098433494032613377L);
            assertThat(failure.traceId()).isEqualTo(success.traceId());
        } finally {
            MDC.remove(TraceIdContext.MDC_KEY);
        }
    }
}