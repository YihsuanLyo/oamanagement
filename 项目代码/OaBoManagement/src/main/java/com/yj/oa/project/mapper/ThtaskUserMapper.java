package com.yj.oa.project.mapper;

import com.yj.oa.project.po.SubtaskUser;
import com.yj.oa.project.po.ThtaskUser;

import java.util.List;

public interface ThtaskUserMapper {
    int deleteByPrimaryKeys(Integer[] id);


    int insertSelective(ThtaskUser thtaskUser);

}