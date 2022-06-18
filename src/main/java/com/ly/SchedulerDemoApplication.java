package com.ly;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.ly","cn.hutool.extra.spring"})
@MapperScan(basePackages = "com.ly.mapper")
public class SchedulerDemoApplication {

    public static void main( String[] args ) {
        SpringApplication.run(SchedulerDemoApplication.class, args);
    }
}
