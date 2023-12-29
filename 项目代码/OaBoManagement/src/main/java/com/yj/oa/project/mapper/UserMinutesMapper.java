package com.yj.oa.project.mapper;

import com.yj.oa.project.po.UserMinutes;

import java.util.List;

public interface UserMinutesMapper {

    int deleteByMinutesIdKeys(Integer[] id);


    int insertSelective(List<UserMinutes> list);



}