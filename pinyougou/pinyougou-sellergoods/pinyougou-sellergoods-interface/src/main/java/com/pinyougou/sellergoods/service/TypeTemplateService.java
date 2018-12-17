package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService extends BaseService<TbTypeTemplate> {

    PageResult search(Integer page, Integer rows, TbTypeTemplate typeTemplate);


    /**
     * 根据分类模版id查询对应的规格和规格选项。
     * @param id 分类模版id
     * @return 规格和规格选项列表数据
     */
    List<Map> findSpecList(Long id);
}