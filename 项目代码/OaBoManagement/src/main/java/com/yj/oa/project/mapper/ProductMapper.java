package com.yj.oa.project.mapper;


import com.yj.oa.project.po.Product;

import java.util.List;

public interface ProductMapper {
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
    int insertSelective(Product record);

    /**
     * 主键查询
     * @param fileId
     * @return
     */
    Product selectByPrimaryKey(Integer fileId);

    /**
     * 修改下载数量
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Product record);



    /**
     *
     * @描述 列表
     *
     * @date 2018/9/19 12:07
     */
    List<Product> selectByProgramKey(Integer proramId);

    List<Product> selectProductList(Product product);
}