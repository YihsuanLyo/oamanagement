package com.yj.oa.project.service.product;

import com.yj.oa.project.mapper.ProductMapper;
import com.yj.oa.project.po.Product;
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
public class IProductServiceIml implements IProductService {

    private Logger log= LoggerFactory.getLogger(IProductServiceIml.class);

    @Autowired
    private ProductMapper productMapper;

    /**
     *
     * @描述 添加
     *
     * @date 2018/9/19 12:00
     */
    @Override
    public int insertSelective(Product product)
    {
        return productMapper.insertSelective(product);
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
            return productMapper.deleteByPrimaryKeys(ids);
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
    public List<Product> selectByProgramKey(Integer programId)
    {
        return productMapper.selectByProgramKey(programId);
    }

    /**
     *
     * @描述  列表
     *
     * @date 2018/9/19 12:08
     */
    @Override
    public List<Product> selectProductList(Product product)
    {
        return productMapper.selectProductList(product);
    }

    /**
     * 下载数量加一
     * @param productId
     */
    @Override
    public void downloadCountAddOne(Product product)
    {
        productMapper.updateByPrimaryKeySelective(product);
    }
}
