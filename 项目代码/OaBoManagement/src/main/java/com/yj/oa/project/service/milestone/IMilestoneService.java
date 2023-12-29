package com.yj.oa.project.service.milestone;

import com.yj.oa.project.po.Milestone;
import com.yj.oa.project.po.Program;

import java.util.List;

/**
 *
 */
public interface IMilestoneService {
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Milestone record, String[] userIds);

    Milestone selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Milestone record, String[] userIds);

    List<Milestone> selectMilestoneList(Milestone milestone);


    List<Milestone> selectMyMilestoneList(String uid);
    List<Milestone> selectMyMilestoneList_all(String uid);

    int updateComplete(Milestone program);
}
