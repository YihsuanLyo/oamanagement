package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Milestone;
import com.yj.oa.project.po.Program;

import java.util.List;

public interface MilestoneMapper {

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
    int insertSelective(Milestone record);

    /**
     * 主键查询
     * @param id
     * @return
     */
    Milestone selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Milestone record);

    /**
     * 列表
     * @param program
     * @return
     */
    List<Milestone> selectMilestoneList(Milestone program);

    /**
     * 根据用户id获取个人会议
     * @param uId
     * @return
     */
    List<Milestone> selectMyMilestoneList(String uId);

    /**
     * 根据用户id获取个人会议
     * @param uId
     * @return
     */
    List<Milestone> selectMyMilestoneList_all(String uId);
}
