package com.yj.oa.project.service.chain;

import com.yj.oa.project.mapper.ChainMapper;
import com.yj.oa.project.po.Chain;
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
public class IChainServiceIml implements IChainService {

    private Logger log= LoggerFactory.getLogger(IChainServiceIml.class);

    @Autowired
    private ChainMapper chainMapper;

    /**
     *
     * @描述 添加
     *
     * @date 2018/9/19 12:00
     */
    @Override
    public int insertSelective(Chain certificate)
    {
        return chainMapper.insertSelective(certificate);
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
            return chainMapper.deleteByPrimaryKeys(ids);
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
    public List<Chain> selectByProgramKey(Integer programId)
    {
        return chainMapper.selectByProgramKey(programId);
    }

    public List<Chain> selectChainList(Chain chain){
        return chainMapper.selectChainList(chain);
    }

    /**
     * 下载数量加一
     * @param chainId
     */
    @Override
    public void downloadCountAddOne(Chain certificates)
    {
        chainMapper.updateByPrimaryKeySelective(certificates);
    }
}
