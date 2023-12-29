package com.yj.oa.project.service.chain;

import com.yj.oa.project.po.Chain;

import java.util.List;

/**
 *
 */
public interface IChainService {
    /**
     *
     * @描述 添加
     *
     * @date 2018/9/19 11:57
     */
    public int insertSelective(Chain chain);

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
    List<Chain> selectByProgramKey(Integer programId);

    List<Chain> selectChainList(Chain chain);

    void downloadCountAddOne(Chain chain);
}
