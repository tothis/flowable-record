package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@ComponentScan("com.example")
@SpringBootApplication
public class FlowableApplication {

    public static void main(String[] args) {
        log.info("开始启动服务 ");
        SpringApplication.run(FlowableApplication.class, args);
        log.info("完成启动服务 ");
    }

}