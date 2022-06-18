-- 任务表
CREATE TABLE `job`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务ID',
    `name`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      DEFAULT '' COMMENT '任务名称',
    `cron_expression` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      DEFAULT NULL COMMENT 'cron执行表达式',
    `bean_name`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      DEFAULT NULL COMMENT '自定义定时任务bean name',
    `method_name`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      DEFAULT NULL COMMENT '自定义定时任务method name',
    `params`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     DEFAULT NULL COMMENT '自定义定时任务参数',
    `job_type`        int                                                               DEFAULT '0' COMMENT '任务类型 0-手动，1-自动',
    `status`          int                                                               DEFAULT '0' COMMENT '任务执行状态 0-待执行，1-执行中，2-暂停，3-已删除',
    `num`             int                                                               DEFAULT '0' COMMENT '执行次数',
    `retry`           int                                                               DEFAULT '0' COMMENT '失败重试次数',
    `description`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci     DEFAULT '' COMMENT '描述',
    `last_exec_time`  datetime                                                          DEFAULT NULL COMMENT '任务最后一次执行时间',
    `create_by`       varchar(64)                                                       DEFAULT '' COMMENT '创建者',
    `create_time`     datetime                                                          DEFAULT NULL COMMENT '创建时间',
    `update_by`       varchar(64)                                                       DEFAULT '' COMMENT '更新者',
    `update_time`     datetime                                                          DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='任务表';

-- INSERT INTO test.job
-- (id, name, cron_expression, bean_name, method_name, params, job_type, status, num, retry, description, last_exec_time, create_by, create_time, update_by, update_time)
-- VALUES('d3820a9f6cc44cdeb7562271009f9a9f', 'testJob1', '0/5 * * * * ?', 'testJob1', 'testExec', 'aaaaaaa', 1, 0, NULL, 3, '测试任务', NULL, 'robben', '2022-06-17 17:39:51', '', NULL);


