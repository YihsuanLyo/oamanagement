package com.yj.oa.project.service.subtask;

import com.yj.oa.project.po.Milestone;
import com.yj.oa.project.po.Subtask;

import java.util.List;

/**
 *
 */
public interface ISubtaskService {
    int deleteByPrimaryKeys(Integer[] id);



    int insertSelective(Subtask record, String[] userIds);

    Subtask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Subtask record, String[] userIds);

    List<Subtask> selectSubtaskList(Subtask subtask);


    List<Subtask> selectMySubtaskList(String uid);
    List<Subtask> selectMySubtaskList_all(String uid);

    int updateComplete(Subtask subtask);

    int deleteByMilestoneid(Integer[] id);
}
