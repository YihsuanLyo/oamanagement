package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Meet;
import com.yj.oa.project.po.Sample;

import java.util.List;

public interface SampleMapper {

    /**
     * 主键查询
     * @param id
     * @return
     */
    Sample selectByPrimaryKey(Integer id);

    /**
     * 批量删除
     * @param id
     * @return
     */
    int deleteByPrimaryKeys(Integer[] id);

    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Sample record);

    /**
     * 修改啊
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Sample record);

    /**
     * 列表
     * @param sample
     * @return
     */
    List<Sample> selectBySampleList(Sample sample);
    /**
     *
     * @描述 列表
     *
     * @date 2018/9/19 12:07
     */
    List<Sample> selectByProgramKey(Integer programId);


    List<Sample> selectSampleList(Sample sample);

}