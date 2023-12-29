package com.yj.oa.project.service.thtask;

import com.yj.oa.project.po.Subtask;
import com.yj.oa.project.po.Thtask;

import java.util.List;

/**
 *
 */
public interface IThtaskService {
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Thtask record);

    Thtask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Thtask record);

    List<Thtask> selectThtaskList(Thtask subtask);


    List<Thtask> selectMyThtaskList(String uid);
    List<Thtask> selectMyThtaskList_all(String uid);

    int updateComplete(Thtask subtask);


}
