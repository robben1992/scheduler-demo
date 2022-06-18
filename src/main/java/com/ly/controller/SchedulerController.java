package com.ly.controller;

import cn.hutool.core.util.StrUtil;
import com.ly.domain.entity.Job;
import com.ly.domain.vo.JobNewVO;
import com.ly.domain.vo.JobUpdateVO;
import com.ly.execption.BusinessException;
import com.ly.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService schedulerService;

    @PostMapping("/add")
    public Integer add(@RequestBody @Validated JobNewVO jobNewVO){
        checkCronExpression(jobNewVO.getCronExpression());
        return schedulerService.add(jobNewVO);
    }

    @PostMapping("/update")
    public Integer update(@RequestBody @Validated JobUpdateVO jobUpdateVO){
        if (StrUtil.isNotBlank(jobUpdateVO.getCronExpression())){
            checkCronExpression(jobUpdateVO.getCronExpression());
        }
        return schedulerService.update(jobUpdateVO);
    }

    @GetMapping("/disable")
    public Integer disable(@RequestParam("id") @NotEmpty String id){
        return schedulerService.disable(id);
    }

    @GetMapping("/all")
    public List<Job> list(){
        return schedulerService.all();
    }

    @GetMapping("/find")
    public Job findOne(@RequestParam("id") @NotEmpty String id){
        return schedulerService.findOne(id);
    }

    private void checkCronExpression(String cronExpression){
        try{
            CronExpression.parse(cronExpression);
        } catch (Exception e){
            throw new BusinessException("不正确的cron表达式");
        }
    }
}
