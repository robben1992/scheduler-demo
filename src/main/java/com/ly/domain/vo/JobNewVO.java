package com.ly.domain.vo;

import com.ly.controller.function.EnumValue;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class JobNewVO {

    //任务名称
    @NotEmpty
    private String name;
    //cron表达式
    @NotEmpty
    private String cronExpression;
    //自定义定时任务bean name
    @NotEmpty
    private String beanName;
    //自定义定时任务method name
    @NotEmpty
    private String methodName;
    //自定义定时任务参数（如果是对象类型需要转化为json）
    private String params;
    //0-手动，1-自动
    @EnumValue(intValues = {0,1}, message = "Invalid jobType value")
    private Integer jobType;
    //重试次数
    @Max(5)
    @Min(0)
    private Integer retry;
    //描述
    private String description;

    private String createBy;
}
