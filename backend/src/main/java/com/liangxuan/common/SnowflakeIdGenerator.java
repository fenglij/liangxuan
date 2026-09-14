package com.liangxuan.common;

import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {
    private static final long EPOCH = 1767225600000L;
    private static final long WORKER_ID_BITS = 5L;
    private static final long SEQUENCE_BITS = 12L;
    private static final long MAX_WORKER_ID = (1L << WORKER_ID_BITS) - 1;
    private static final long SEQUENCE_MASK = (1L << SEQUENCE_BITS) - 1;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    private final long workerId;
    private long lastTimestamp = -1L;
    private long sequence;

    public SnowflakeIdGenerator() {
        this(1L);
    }

    SnowflakeIdGenerator(long workerId) {
        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException("workerId must be between 0 and " + MAX_WORKER_ID);
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long now = System.currentTimeMillis();
        if (now < lastTimestamp) {
            now = waitUntil(lastTimestamp);
        }
        if (now == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) now = waitUntil(lastTimestamp + 1);
        } else {
            sequence = 0;
        }
        lastTimestamp = now;
        this.sequence = sequence;
        return ((now - EPOCH) << TIMESTAMP_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    private long waitUntil(long timestamp) {
        long now = System.currentTimeMillis();
        while (now < timestamp) now = System.currentTimeMillis();
        return now;
    }
}