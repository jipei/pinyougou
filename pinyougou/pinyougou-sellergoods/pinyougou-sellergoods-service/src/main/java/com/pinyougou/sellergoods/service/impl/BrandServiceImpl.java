package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

//将服务暴露注册到dubbo注册中心
@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl extends BaseServiceImpl<TbBrand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<TbBrand> queryAll() {
        return brandMapper.queryAll();
    }

    @Override
    public List<TbBrand> testPage(Integer page, Integer rows) {
        //设置分页
        PageHelper.startPage(page, rows);

        return brandMapper.selectAll();
    }

    @Override
    public PageResult search(Integer page, Integer rows, TbBrand brand) {

        //设置分页
        PageHelper.startPage(page, rows);

        //创建查询对象,相当于from子句
        Example example = new Example(TbBrand.class);

        //创建查询条件对象,相当于where子句
        Example.Criteria criteria = example.createCriteria();

        //首字母
        /*if (brand.getFirstChar() != null && !"".equals(brand.getFirstChar())) {
            criteria.andEqualTo("firstChar", brand.getFirstChar());
        }*/
        if (!StringUtils.isEmpty(brand.getFirstChar())){
            criteria.andEqualTo("firstChar", brand.getFirstChar());
        }

        //名称 模糊查询
        if(!StringUtils.isEmpty(brand.getName())){
            criteria.andLike("name", "%"+brand.getName()+"%");
        }


        //查询
        List<TbBrand> list = brandMapper.selectByExample(example);
        //转换分页信息对象
        PageInfo<TbBrand> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public List<Map<String, String>> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}
