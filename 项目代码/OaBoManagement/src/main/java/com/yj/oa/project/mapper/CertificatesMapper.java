package com.yj.oa.project.mapper;


import com.yj.oa.project.po.Certificates;
import com.yj.oa.project.po.Files;

import java.security.cert.Certificate;
import java.util.List;

public interface CertificatesMapper {
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
    int insertSelective(Certificates record);

    /**
     * 主键查询
     * @param fileId
     * @return
     */
    Certificates selectByPrimaryKey(Integer fileId);

    /**
     * 修改下载数量
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Certificates record);



    /**
     *
     * @描述 列表
     *
     * @date 2018/9/19 12:07
     */
    List<Certificates> selectByProgramKey(Integer proramId);

    List<Certificates> selectCertificateList(Certificates certificates);
}