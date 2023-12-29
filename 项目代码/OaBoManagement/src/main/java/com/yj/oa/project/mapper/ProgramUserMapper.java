package com.yj.oa.project.mapper;

import com.yj.oa.project.po.ProgramUser;
import com.yj.oa.project.po.ScheduleUser;

import java.util.List;

public interface ProgramUserMapper {
    int deleteByPrimaryKeys(Integer[] id);


    int insertSelective(List<ProgramUser> userList);

}