package com.dongxiayong.springbatchstart;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing//表示开启Spring Batch批处理功能
public class SpringBatchStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchStartApplication.class, args);
    }

}
