package com.yj.oa.project.service.design;

import com.yj.oa.project.mapper.DesignMapper;
import com.yj.oa.project.po.Design;
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
public class IDesignServiceIml implements IDesignService {

    private Logger log= LoggerFactory.getLogger(IDesignServiceIml.class);

    @Autowired
    private DesignMapper designMapper;

    /**
     *
     * @描述 添加
     *
     * @date 2018/9/19 12:00
     */
    @Override
    public int insertSelective(Design design)
    {
        return designMapper.insertSelective(design);
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
            return designMapper.deleteByPrimaryKeys(ids);
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
    public List<Design> selectByProgramKey(Integer programId)
    {
        return designMapper.selectByProgramKey(programId);
    }

    public List<Design> selectDesignList(Design design){return designMapper.selectDesignList(design);}

    /**
     * 下载数量加一
     * @param designId
     */
    @Override
    public void downloadCountAddOne(Design design)
    {
        designMapper.updateByPrimaryKeySelective(design);
    }
}
