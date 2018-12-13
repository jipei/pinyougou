package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService extends BaseService<TbBrand> {
    /**
     * 查询品牌列表
     * @return 品牌列表
     */
    List<TbBrand> queryAll();

    /**
     * 分页查询品牌的第1页每页5条数据
     * @param page 页号
     * @param rows 页大小
     * @return 品牌列表
     */
    List<TbBrand> testPage(Integer page, Integer rows);

    /**
     * 根据条件分页模糊查询品牌数据
     * @param page 页号
     * @param rows 页大小
     * @param brand 查询条件
     * @return 分页对象
     */
    PageResult search(Integer page, Integer rows, TbBrand brand);

    /**
     * 加载select2的品牌数据列表；格式：[{id:'1',text:'联想'},{id:'2',text:'华为'}]
     * @return 品牌数据列表
     */
    List<Map<String, String>> selectOptionList();
}
