package com.ly.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobTypeEnum {

    //任务类型 0-手动，1-自动
    MANUAL(0, "手动"),
    AUTO(1, "自动");

    private final Integer status;

    private final String desc;

}
