package com.yj.oa.project.mapper;

import com.yj.oa.project.po.MilestoneUser;
import com.yj.oa.project.po.ProgramUser;

import java.util.List;

public interface MilestoneUserMapper {
    int deleteByPrimaryKeys(Integer[] id);


    int insertSelective(List<MilestoneUser> userList);

}