package com.example.starter.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author youngbear
 * @email youngbear@aliyun.com
 * @date 2019-07-29 22:50
 * @blog https://blog.csdn.net/next_second
 * @github https://github.com/YoungBear
 * @description
 */
public class MyApplicationListener implements ApplicationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyApplicationListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        String className = applicationEvent.getClass().getName();
        LOGGER.info("onApplicationEvent, className: {}", className);
    }
}
