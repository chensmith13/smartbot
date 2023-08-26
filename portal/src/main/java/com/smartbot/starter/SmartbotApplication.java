package com.smartbot.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.smartbot"} )
@MapperScan(basePackages = {"com.smartbot.dao"})
@Import(MybatisPlusConfiguration.class)
public class SmartbotApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartbotApplication.class,args);
    }
}
