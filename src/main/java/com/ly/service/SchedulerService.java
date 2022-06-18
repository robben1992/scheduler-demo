package com.ly.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ly.constant.JobStatusEnum;
import com.ly.domain.entity.Job;
import com.ly.domain.vo.JobNewVO;
import com.ly.domain.vo.JobUpdateVO;
import com.ly.execption.BusinessException;
import com.ly.job.JobUtil;
import com.ly.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final JobMapper jobMapper;
    private final JobUtil jobUtil;

    public Integer add(JobNewVO jobNewVO){
        Job job = BeanUtil.copyProperties(jobNewVO, Job.class);
        job.setId(IdUtil.simpleUUID());
        job.setStatus(JobStatusEnum.PENDING.getStatus());
        job.setCreateTime(LocalDateTime.now());

        int num = jobMapper.add(job);

        //执行任务
        jobUtil.start(job);
        return num;
    }

    public Integer update(JobUpdateVO jobUpdateVOO){
        Job job = checkExist(jobUpdateVOO.getId());
        BeanUtil.copyProperties(jobUpdateVOO, job);
        job.setUpdateTime(LocalDateTime.now());
        int num = jobMapper.update(job);

        //执行任务
        jobUtil.start(job);
        return num;
    }

    public Integer disable(String id){
        Job job = checkExist(id);
        if (JobStatusEnum.DELETED.getStatus().equals(job.getStatus())){
            throw new BusinessException("任务已删除，请勿重复操作");
        }
        int num = jobMapper.update(Job.builder().id(id).status(JobStatusEnum.DELETED.getStatus()).build());

        //删除任务
        jobUtil.cancel(id);
        return num;
    }

    public List<Job> all(){
        return jobMapper.all();
    }

    public Job findOne(String id){
        return checkExist(id);
    }

    private Job checkExist(String id){
        Job job = jobMapper.findById(id);
        if (ObjectUtil.isNull(job)){
            throw new BusinessException("任务不存在，请检查");
        }
        return job;
    }
}
