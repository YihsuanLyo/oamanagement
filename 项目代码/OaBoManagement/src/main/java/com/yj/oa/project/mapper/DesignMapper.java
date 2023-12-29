package com.yj.oa.project.mapper;


import com.yj.oa.project.po.Design;

import java.util.List;

public interface DesignMapper {
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
    int insertSelective(Design record);

    /**
     * 主键查询
     * @param fileId
     * @return
     */
    Design selectByPrimaryKey(Integer fileId);

    /**
     * 修改下载数量
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Design record);



    /**
     *
     * @描述 列表
     *
     * @date 2018/9/19 12:07
     */
    List<Design> selectByProgramKey(Integer proramId);

    List<Design> selectDesignList(Design design);
}