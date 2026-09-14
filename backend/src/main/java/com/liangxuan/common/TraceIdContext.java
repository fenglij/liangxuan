package com.liangxuan.common;

import org.slf4j.MDC;

public final class TraceIdContext {
    public static final String MDC_KEY = "traceId";
    public static final String HEADER_NAME = "X-Trace-Id";

    private TraceIdContext() {
    }

    public static Long getTraceId() {
        String value = MDC.get(MDC_KEY);
        return value == null ? null : Long.valueOf(value);
    }

    public static void setTraceId(Long traceId) {
        MDC.put(MDC_KEY, traceId.toString());
    }

    public static void clear() {
        MDC.remove(MDC_KEY);
    }
}