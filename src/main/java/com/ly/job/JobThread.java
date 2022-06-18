package com.ly.job;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ly.constant.JobStatusEnum;
import com.ly.domain.entity.Job;
import com.ly.mapper.JobMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Slf4j
@Builder
public class JobThread implements Runnable {

    private String jobId;
    private String beanName;
    private String methodName;
    private String params;
    private int retry;

    public JobThread(String jobId, String beanName, String methodName, String params, int retry) {
        this.jobId = jobId;
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
        this.retry = retry;
    }

    @Override
    public void run() {
        int retryTime = retry;
        while (retryTime-- >= 0){
            boolean exec = this.exec();
            if (exec)
                break;
        }
    }

    private boolean exec(){
        Object bean = SpringUtil.getBean(beanName);
        if (bean == null){
            log.info("id为：{} 的定时任务不存在", jobId);
            return false;
        }
        try {
            Method method;
            if (StrUtil.isBlankIfStr(params)){
                method = bean.getClass().getDeclaredMethod(methodName);
                method.invoke(bean);
            }else {
                method = bean.getClass().getDeclaredMethod(methodName, String.class);
                method.invoke(bean, params);
            }
            //更新DB 任务状态为执行中, 最后一次执行时间为当前时间
            SpringUtil.getBean(JobMapper.class)
                    .update(Job.builder()
                            .id(jobId)
                            .lastExecTime(LocalDateTime.now())
                            .status(JobStatusEnum.EXECUTING.getStatus())
                            .build());
            log.info("id为：{} 的定时任务执行成功", jobId);
            return true;
        } catch (NoSuchMethodException e) {
            log.info("id为：{} 的定时任务方法 {} 不存在", jobId, methodName);
            return false;
        } catch (Exception e) {
            log.info("id为：{} 的定时任务执行失败", jobId);
            return false;
        }
    }
}
