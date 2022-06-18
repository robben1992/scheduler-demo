package com.ly.job;

import com.ly.constant.JobStatusEnum;
import com.ly.constant.JobTypeEnum;
import com.ly.domain.entity.Job;
import com.ly.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobUtil {

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final JobMapper jobMapper;

    private static final Map<String, ScheduledFuture<?>> jobMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        //启动初始化加载DB 待执行、执行中的任务
        List<Job> jobList = jobMapper.listByType(JobTypeEnum.AUTO.getStatus());
        log.info("初始化任务数量：{}", jobList.size());
        jobList.forEach(job -> this.start(job));
    }

    @PreDestroy
    public void destory(){
        jobMap.forEach((jobId,job) -> this.cancel(jobId));
        log.info("任务结束");
    }

    public void start(Job job){
        log.info("开始任务：{}", job);
        if (jobMap.get(job.getId()) != null){
            log.warn("任务已存在");
            //已存在的任务关闭后重新开启
            this.cancel(job.getId());
        }
        JobThread jobThread = JobThread.builder()
                .jobId(job.getId())
                .beanName(job.getBeanName())
                .methodName(job.getMethodName())
                .params(job.getParams())
                .retry(job.getRetry()).build();
        //手动执行的直接调用Thread执行，自动的放入线程池
        if (JobTypeEnum.MANUAL.getStatus() == job.getJobType()){
            jobThread.run();
        }else{
            ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(jobThread, new CronTrigger(job.getCronExpression()));
            jobMap.put(job.getId(), schedule);
        }
    }

    public void pause(String jobId){
        log.info("暂停任务：{}", jobId);
        if (jobMap.get(jobId) != null){
            jobMap.get(jobId).cancel(true);
            jobMap.remove(jobId);

            //更新DB状态
            jobMapper.update(Job.builder().id(jobId).status(JobStatusEnum.PAUSED.getStatus()).build());
        }else {
            log.warn("id为：{} 的任务不存在，无法暂停", jobId);
        }
    }

    public void cancel(String jobId){
        log.info("取消任务：{}", jobId);
        if (jobMap.get(jobId) != null){
            jobMap.get(jobId).cancel(true);
            jobMap.remove(jobId);
        }else{
            log.warn("id为：{} 的任务不存在，无法取消", jobId);
        }
    }
}
