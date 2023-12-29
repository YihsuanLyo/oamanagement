package com.yj.oa.project.mapper;


import com.yj.oa.project.po.Chain;

import java.util.List;

public interface ChainMapper {
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
    int insertSelective(Chain record);

    /**
     * 主键查询
     * @param fileId
     * @return
     */
    Chain selectByPrimaryKey(Integer fileId);

    /**
     * 修改下载数量
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Chain record);



    /**
     *
     * @描述 列表
     *
     * @date 2018/9/19 12:07
     */
    List<Chain> selectByProgramKey(Integer proramId);

    List<Chain> selectChainList(Chain chain);
}