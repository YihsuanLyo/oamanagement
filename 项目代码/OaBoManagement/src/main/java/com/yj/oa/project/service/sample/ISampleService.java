package com.yj.oa.project.service.sample;

import com.yj.oa.project.po.Sample;

import java.util.List;

/**
 *
 */
public interface ISampleService {

    Sample selectByPrimaryKey(Integer id);

    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Sample record);

    int updateByPrimaryKeySelective(Sample record);



    List<Sample> selectByProgramKey(Integer programId);
    List<Sample> selectSampleList(Sample sample);


}
