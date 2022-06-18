package com.ly.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class JobUpdateVO {

    @NotEmpty
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
    //重试次数
    private Integer retry;
    //描述
    private String description;

    private String updateBy;
}
