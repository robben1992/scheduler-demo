package com.ly.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component("testJob1")
public class TestJob1 {

    public void testExec(String params){
        log.info("----testJob1任务 开始执行----");
        log.info("接收参数为：{}", params);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("----testJob1任务 执行结束----");
    }
}
