package com.yj.oa.project.service.certificate;

import com.yj.oa.project.po.Certificates;
import com.yj.oa.project.po.Files;

import java.security.cert.Certificate;
import java.util.List;

/**
 *
 */
public interface ICertificateService {
    /**
     *
     * @描述 添加
     *
     * @date 2018/9/19 11:57
     */
    public int insertSelective(Certificates certificate);

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
    List<Certificates> selectByProgramKey(Integer programId);

    List<Certificates> selectCertificateList(Certificates certificates);

    void downloadCountAddOne(Certificates certificates);
}
