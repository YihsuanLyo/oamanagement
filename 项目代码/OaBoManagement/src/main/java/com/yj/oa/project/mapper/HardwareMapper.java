package com.yj.oa.project.mapper;


import com.yj.oa.project.po.Hardware;

import java.util.List;

public interface HardwareMapper {
    /**
     * 批量删除
     * @param fileId
     * @return
     */
    int deleteByPrimaryKeys(String[] fileId);


    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Hardware record);

    /**
     * 主键查询
     * @param fileId
     * @return
     */
    Hardware selectByPrimaryKey(Integer fileId);

    /**
     * 修改下载数量
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Hardware record);



    /**
     *
     * @描述 列表
     *
     * @date 2018/9/19 12:07
     */
    List<Hardware> selectByProgramKey(Integer proramId);

    List<Hardware> selectHardwareList(Hardware hardware);
}