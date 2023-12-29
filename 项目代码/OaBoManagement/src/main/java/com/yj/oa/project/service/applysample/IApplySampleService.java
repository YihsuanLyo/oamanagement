package com.yj.oa.project.service.applysample;

import com.yj.oa.project.po.ApplySample;
import com.yj.oa.project.po.LeaveForm;

import java.util.List;

/**
 *
 */
public interface IApplySampleService {
    /**
     *
     * 批量删除
     * @mbggenerated
     */
    int deleteByPrimaryKeys(String[] ids) throws Exception;

    /**
     *添加
     * @mbggenerated
     */
    void insertSelective(ApplySample record);

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
    List<ApplySample> selectApplySampleList(ApplySample applySample);


    /**
     * 填写表单
     * @mbggenerated
     */
    void fillForm(ApplySample applySample);

    /**
     * 提交表单
     * @mbggenerated
     */
    void submit(String proceId);

    /**
     * 放弃提交表单
     * @mbggenerated
     */
    void giveUp(String proceId);
}
