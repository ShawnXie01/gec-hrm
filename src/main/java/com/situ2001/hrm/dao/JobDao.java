package com.situ2001.hrm.dao;

import com.situ2001.hrm.pojo.Job;

import java.util.List;

public interface JobDao {
    // methods: add, delete, deleteMany, count, pagination, edit
    int add(Job job);
    int delete(int id);
    int deleteMany(String idSetString);
    int count();
    int countByJobName(String jobName);
    List<Job> listJob(int page, int limit);
    List<Job> findJobByName(String jobName, int page, int limit);
    int update(Job job);
    boolean checkName(String jobName);
}
