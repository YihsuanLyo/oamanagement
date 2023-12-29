package com.yj.oa.project.mapper;

import com.yj.oa.project.po.ApplySample;
import com.yj.oa.project.po.LeaveForm;

import java.util.List;

public interface ApplySampleMapper {
    /**
     *
     * 批量删除
     * @mbggenerated
     */
    int deleteByprocInstIds(String[] ids);

    /**
     *添加
     * @mbggenerated
     */
    int insertSelective(ApplySample record);

    /**
     *主键查询
     *
     * @mbggenerated
     */
    ApplySample selectByPrimaryKey(Integer id);

    /**
     * 修改状态
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ApplySample record);


    /**
     * 列表
     * @mbggenerated
     */
    List<ApplySample> selectApplySampleList(ApplySample a);

    /**
     * 统计请假天数
     */
    List<ApplySample> selectByUserIdAndDate(ApplySample applySample);
}