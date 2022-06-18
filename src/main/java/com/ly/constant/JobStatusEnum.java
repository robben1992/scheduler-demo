package com.ly.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobStatusEnum {

    //任务执行状态
    PENDING(0, "待执行"),
    EXECUTING(1, "执行中"),
    PAUSED(2, "暂停"),
    DELETED(3, "已删除");

    private final Integer status;

    private final String desc;

}
