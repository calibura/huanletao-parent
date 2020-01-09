package com.huanletao.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huanletao.mapper.TbBrandMapper;
import com.huanletao.pojo.PageResult;
import com.huanletao.pojo.TbBrand;
import com.huanletao.pojo.TbBrandExample;
import com.huanletao.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class BrandServcieImpl implements BrandService {
    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Override
    public PageResult findAll(String keywords, int pageNum, int pageSize) {
        // 限定查询范围 改变了原有的查询结构
        PageHelper.startPage(pageNum,pageSize);

        TbBrandExample example = new TbBrandExample();
        Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.findByKeywords(keywords);
        List<TbBrand> rows = page.getResult();
        long total = page.getTotal();
        return new PageResult(total,rows);
    }

    @Override
    public void add(TbBrand brand) throws Exception {
        tbBrandMapper.insert(brand);
    }

    @Override
    public TbBrand findOne(Long id) {
        return tbBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(TbBrand brand) {
        tbBrandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public void dele(Long[] ids) {
        for (Long id : ids) {
            tbBrandMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public List<Map> selectBrandList() {
        return tbBrandMapper.selectBrandList();
    }
}
