package com.yj.oa.project.service.certificate;

import com.yj.oa.project.mapper.CertificatesMapper;
import com.yj.oa.project.mapper.FilesMapper;
import com.yj.oa.project.po.Certificates;
import com.yj.oa.project.po.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.cert.Certificate;
import java.util.List;

/**
 *
 *
 */
@Service
@Transactional
public class ICertificateServiceIml implements ICertificateService {

    private Logger log= LoggerFactory.getLogger(ICertificateServiceIml.class);

    @Autowired
    private CertificatesMapper certificateMapper;

    /**
     *
     * @描述 添加
     *
     * @date 2018/9/19 12:00
     */
    @Override
    public int insertSelective(Certificates certificate)
    {
        return certificateMapper.insertSelective(certificate);
    }

    /**
     *
     * @描述 删除
     *
     * @date 2018/9/19 12:00
     */
    @Override
    public int deleteByPrimaryKeys(String[] ids)
    {
        try
        {
            return certificateMapper.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            log.error("$$$$$ 删除文件失败=[{}]",e);
            throw new RuntimeException("操作失败！");
        }
    }

    /**
     *
     * @描述  列表
     *
     * @date 2018/9/19 12:08
     */
    @Override
    public List<Certificates> selectByProgramKey(Integer programId)
    {
        return certificateMapper.selectByProgramKey(programId);
    }

    public List<Certificates> selectCertificateList(Certificates certificates){
        return certificateMapper.selectCertificateList(certificates);
    }

    /**
     * 下载数量加一
     * @param certificateId
     */
    @Override
    public void downloadCountAddOne(Certificates certificates)
    {
        certificateMapper.updateByPrimaryKeySelective(certificates);
    }
}
