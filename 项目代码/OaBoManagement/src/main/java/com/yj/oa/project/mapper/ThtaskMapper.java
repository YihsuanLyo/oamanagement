package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Subtask;
import com.yj.oa.project.po.Thtask;

import java.util.List;

public interface ThtaskMapper {

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKeys(Integer[] id);

    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Thtask record);

    /**
     * 主键查询
     * @param id
     * @return
     */
    Thtask selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Thtask record);

    /**
     * 列表
     * @param program
     * @return
     */
    List<Thtask> selectThtaskList(Thtask program);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteBySubtaskid(Integer id);



    /**
     * 根据用户id获取个人会议
     * @param uId
     * @return
     */
    List<Thtask> selectMyThtaskList(String uId);

    /**
     * 根据用户id获取个人会议
     * @param uId
     * @return
     */
    List<Thtask> selectMyThtaskList_all(String uId);


}
