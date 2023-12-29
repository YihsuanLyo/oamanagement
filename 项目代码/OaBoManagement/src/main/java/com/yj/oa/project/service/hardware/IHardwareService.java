package com.yj.oa.project.service.hardware;

import com.yj.oa.project.po.Hardware;

import java.util.List;

/**
 *
 */
public interface IHardwareService {
    /**
     *
     * @描述 添加
     *
     * @date 2018/9/19 11:57
     */
    public int insertSelective(Hardware certificate);

    /**
     *
     * @描述 删除
     *
     * @date 2018/9/19 11:57
     */
    public int deleteByPrimaryKeys(String[] ids);

    /**
     *
     * @描述 列表
     *
     * @date 2018/9/19 12:07
     */
    List<Hardware> selectByProgramKey(Integer programId);

    List<Hardware> selectHardwareList(Hardware hardware);

    void downloadCountAddOne(Hardware hardware);
}
