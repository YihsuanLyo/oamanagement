package com.yj.oa.project.service.product;

import com.yj.oa.project.po.Product;

import java.util.List;

/**
 *
 */
public interface IProductService {
    /**
     *
     * @描述 添加
     *
     * @date 2018/9/19 11:57
     */
    public int insertSelective(Product product);

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
    List<Product> selectByProgramKey(Integer programId);
    List<Product> selectProductList(Product product);

    void downloadCountAddOne(Product product);
}
