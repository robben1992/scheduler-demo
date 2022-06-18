package com.ly.mapper;

import com.ly.domain.entity.Job;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobMapper {

    Job findById(String id);

    int add(Job job);

    int update(Job job);

    List<Job> all();

    List<Job> listByType(Integer jobType);
}
