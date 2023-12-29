package com.yj.oa.project.service.design;

import com.yj.oa.project.po.Design;

import java.util.List;

/**
 *
 */
public interface IDesignService {
    /**
     *
     * @描述 添加
     *
     * @date 2018/9/19 11:57
     */
    public int insertSelective(Design design);

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
    List<Design> selectByProgramKey(Integer programId);

    List<Design> selectDesignList(Design design);

    void downloadCountAddOne(Design design);
}
