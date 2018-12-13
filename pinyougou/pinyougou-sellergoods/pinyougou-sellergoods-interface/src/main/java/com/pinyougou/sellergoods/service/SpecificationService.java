package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Specification;

import java.util.List;
import java.util.Map;

public interface SpecificationService extends BaseService<TbSpecification> {

    PageResult search(Integer page, Integer rows, TbSpecification specification);

    /**
     * 保存规格及其选项
     * @param specification 规格及其选项
     */
    void add(Specification specification);

    /**
     * 根据规格id查询规格及其对应的选项列表
     * @param id 规格id
     * @return 规格及其对应的选项列表
     */
    Specification findOne(Long id);

    /**
     * 更新保存规格及其规格选项列表
     * @param specification 规格及其对应的选项列表
     */
    void update(Specification specification);

    /**
     * 根据规格id数组批量删除这些规格id对应的选项
     * @param ids 规格id数组
     */
    void deleteSpecificationByIds(Long[] ids);

    /**
     * 加载select2的规格数据列表；格式：[{id:'1',text:'内存'},{id:'2',text:'尺寸'}]
     * @return 规格数据列表
     */
    List<Map<String, String>> selectOptionList();
}