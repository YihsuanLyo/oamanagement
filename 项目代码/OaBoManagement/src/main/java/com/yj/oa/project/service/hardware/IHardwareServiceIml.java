package com.yj.oa.project.service.hardware;

import com.yj.oa.project.mapper.HardwareMapper;
import com.yj.oa.project.po.Hardware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 *
 */
@Service
@Transactional
public class IHardwareServiceIml implements IHardwareService {

    private Logger log= LoggerFactory.getLogger(IHardwareServiceIml.class);

    @Autowired
    private HardwareMapper hardwareMapper;

    /**
     *
     * @描述 添加
     *
     * @date 2018/9/19 12:00
     */
    @Override
    public int insertSelective(Hardware hardware)
    {
        return hardwareMapper.insertSelective(hardware);
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
            return hardwareMapper.deleteByPrimaryKeys(ids);
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
    public List<Hardware> selectByProgramKey(Integer programId)
    {
        return hardwareMapper.selectByProgramKey(programId);
    }

    /**
     * 下载数量加一
     * @param hardwareId
     */
    @Override
    public void downloadCountAddOne(Hardware certificates)
    {
        hardwareMapper.updateByPrimaryKeySelective(certificates);
    }

    public List<Hardware> selectHardwareList(Hardware hardware){return hardwareMapper.selectHardwareList(hardware);}
}
