package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Meet;
import com.yj.oa.project.po.Minutes;

import java.util.List;

public interface MinutesMapper {

    /**
     * 主键查询
     * @param id
     * @return
     */
    Minutes selectByPrimaryKey(Integer id);

    /**
     * 批量删除
     * @param id
     * @return
     */
    int deleteByPrimaryKeys(Integer[] id);
    /**
     * 批量删除
     * @param id
     * @return
     */
    int deleteByMeetid(Integer[] id);

    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Minutes record);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Minutes record);

    /**
     * 列表
     * @param minutes
     * @return
     */
    List<Minutes> selectByMinutesList(Minutes minutes);

    /**
     * 根据用户id获取个人会议
     * @param uId
     * @return
     */
    List<Minutes> selectMyMinutesList(String uId);




}