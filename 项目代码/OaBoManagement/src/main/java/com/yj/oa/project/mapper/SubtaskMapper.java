package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Milestone;
import com.yj.oa.project.po.Subtask;

import java.util.List;

public interface SubtaskMapper {

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
    int insertSelective(Subtask record);

    /**
     * 主键查询
     * @param id
     * @return
     */
    Subtask selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Subtask record);

    /**
     * 列表
     * @param program
     * @return
     */
    List<Subtask> selectSubtaskList(Subtask program);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByMilestoneid(Integer[] id);

    /**
     * 根据用户id获取个人会议
     * @param uId
     * @return
     */
    List<Subtask> selectMySubtaskList(String uId);
    /**
     * 根据用户id获取个人会议
     * @param Mid
     * @return
     */
    List<Subtask> selectByMilestoneid(Integer Mid);
    /**
     * 根据用户id获取个人会议
     * @param uId
     * @return
     */
    List<Subtask> selectMySubtaskList_all(String uId);


}
