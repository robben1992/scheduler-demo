package com.ly.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component("testJob2")
public class TestJob2 {

    public void testExec(String params){
        log.info("----testJob2任务 开始执行----");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("----testJob2任务 执行结束----");
    }
}
