package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service(interfaceClass = SpecificationService.class)
public class SpecificationServiceImpl extends BaseServiceImpl<TbSpecification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public PageResult search(Integer page, Integer rows, TbSpecification specification) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbSpecification.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(specification.getSpecName())){
            criteria.andLike("specName", "%" + specification.getSpecName() + "%");
        }

        List<TbSpecification> list = specificationMapper.selectByExample(example);
        PageInfo<TbSpecification> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void add(Specification specification) {
        //1、保存规格；通用mapper默认设置了主键回填，也就是执行完新增方法之后，对象的主键有值
        specificationMapper.insertSelective(specification.getSpecification());

        //2、保存规格选项列表
        if (specification.getSpecificationOptionList() != null && specification.getSpecificationOptionList().size() > 0) {
            for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
                specificationOption.setSpecId(specification.getSpecification().getId());
                //保存规格选项
                specificationOptionMapper.insertSelective(specificationOption);
            }
        }
    }

    @Override
    public Specification findOne(Long id) {
        Specification specification = new Specification();

        //1、根据规格id查询规格
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        specification.setSpecification(tbSpecification);

        //2、根据规格id查询规格选项列表
        TbSpecificationOption param = new TbSpecificationOption();
        param.setSpecId(id);
        List<TbSpecificationOption> specificationOptionList = specificationOptionMapper.select(param);

        specification.setSpecificationOptionList(specificationOptionList);

        return specification;
    }

    @Override
    public void update(Specification specification) {
        //1、根据规格id更新规格
        specificationMapper.updateByPrimaryKeySelective(specification.getSpecification());

        //2、根据规格id删除规格选项
        TbSpecificationOption param = new TbSpecificationOption();
        param.setSpecId(specification.getSpecification().getId());

        specificationOptionMapper.delete(param);

        //3、保存规格选项列表
        if (specification.getSpecificationOptionList() != null && specification.getSpecificationOptionList().size() > 0) {
            for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
                specificationOption.setSpecId(specification.getSpecification().getId());
                //保存规格选项
                specificationOptionMapper.insertSelective(specificationOption);
            }
        }
    }
}
