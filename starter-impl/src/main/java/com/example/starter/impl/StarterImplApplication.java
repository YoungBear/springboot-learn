package com.example.starter.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author youngbear
 * @email youngbear@aliyun.com
 * @date 2019-07-29 22:44
 * @blog https://blog.csdn.net/next_second
 * @github https://github.com/YoungBear
 * @description
 */
@SpringBootApplication
public class StarterImplApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StarterImplApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StarterImplApplication.class);
    }
}
