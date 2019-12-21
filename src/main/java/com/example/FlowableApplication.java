package com.example;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.example", "org.flowable.ui.modeler", "org.flowable.ui.common"})
@MapperScan("com.example.mapper")
@SpringBootApplication(
        exclude = {
                SecurityAutoConfiguration.class
                , UserDetailsServiceAutoConfiguration.class
                , LiquibaseAutoConfiguration.class
        }
)
public class FlowableApplication {

    public static void main(String[] args) {
        log.info("开始启动服务 ");
        SpringApplication.run(FlowableApplication.class, args);
        log.info("完成启动服务 ");
    }

}