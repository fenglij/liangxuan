package com.liangxuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LiangxuanApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiangxuanApplication.class, args);
    }
}
