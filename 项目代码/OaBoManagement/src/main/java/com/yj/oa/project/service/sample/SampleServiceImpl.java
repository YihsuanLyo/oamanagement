package com.yj.oa.project.service.sample;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.MeetMapper;
import com.yj.oa.project.mapper.SampleMapper;
import com.yj.oa.project.po.Sample;
import com.yj.oa.project.po.UserMeet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@Transactional
public class SampleServiceImpl implements ISampleService {

    @Autowired
    SampleMapper sampleMapper;


    /**
     *
     * @描述: 根据主键查询
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:06
     */
    @Override
    public Sample selectByPrimaryKey(Integer id)
    {
        return sampleMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述: 批量删除
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:08
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] ids)
    {


        return sampleMapper.deleteByPrimaryKeys(ids);
    }


    /**
     *
     * @描述: 添加
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:09
     */
    @Override
    public int insertSelective(Sample record)
    {
        int i = sampleMapper.insertSelective(record);


        return i;
    }


    /**
     *
     * @描述: 修改
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:09
     */
    @Override
    public int updateByPrimaryKeySelective(Sample record)
    {



        return sampleMapper.updateByPrimaryKeySelective(record);
    }

    /**
     *
     * @描述  列表
     *
     * @date 2018/9/19 12:08
     */
    @Override
    public List<Sample> selectByProgramKey(Integer programId)
    {
        return sampleMapper.selectByProgramKey(programId);

    }


    public List<Sample> selectSampleList(Sample sample)
    {
        return sampleMapper.selectSampleList(sample);

    }}
