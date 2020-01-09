package com.huanletao.sellergoods.service;

import com.huanletao.pojo.PageResult;
import com.huanletao.pojo.TbBrand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    PageResult findAll(String keywords, int pageNum, int pageSize);

    void add(TbBrand brand)throws Exception;

    TbBrand findOne(Long id);

    void update(TbBrand brand);

    void dele(Long[] ids);

    List<Map> selectBrandList();
}
