package com.yj.oa.project.mapper;

import com.yj.oa.project.po.MilestoneUser;
import com.yj.oa.project.po.SubtaskUser;

import java.util.List;

public interface SubtaskUserMapper {
    int deleteByPrimaryKeys(Integer[] id);


    int insertSelective(List<SubtaskUser> userList);

}