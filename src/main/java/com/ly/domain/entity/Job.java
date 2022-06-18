package com.ly.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Job {

    private String id;
    //任务名称
    private String name;
    //cron表达式
    private String cronExpression;
    //自定义定时任务bean name
    private String beanName;
    //自定义定时任务method name
    private String methodName;
    //自定义定时任务参数（如果是对象类型需要转化为json）
    private String params;
    //0-手动，1-自动
    private Integer jobType;
    //任务执行状态 0-待执行，1-执行中，2-暂停，3-已删除
    private Integer status;
    //执行次数
    private Integer num;
    //重试次数
    private Integer retry;
    //描述
    private String description;
    //任务最后一次执行时间
    private LocalDateTime lastExecTime;

    private String createBy;
    private LocalDateTime createTime;
    private String updateBy;
    private LocalDateTime updateTime;
}
