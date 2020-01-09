package com.huanletao.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huanletao.pojo.JsonResult;
import com.huanletao.pojo.PageResult;
import com.huanletao.pojo.TbBrand;
import com.huanletao.sellergoods.service.BrandService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public PageResult findAll(String keywords, int pageNum, int pageSize){
        return brandService.findAll(keywords,pageNum,pageSize);
    }
    @RequestMapping("/add")
    public JsonResult add(@RequestBody TbBrand brand){
        try{
            brandService.add(brand);
            return new JsonResult(true,"添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(false,"添加失败");
        }

    }
    @RequestMapping("/findOne")
    public TbBrand findOne(Long id){
       return brandService.findOne(id);
    }

    @RequestMapping("/update")
    public JsonResult update(@RequestBody TbBrand brand){
        try{
            brandService.update(brand);
            return new JsonResult(true,"修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(false,"修改失败");
        }

    }

    @RequestMapping("/dele")
    public JsonResult dele( Long[] ids){
        try{
            brandService.dele(ids);
            return new JsonResult(true,"删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(false,"删除失败");
        }

    }

    @RequestMapping("/selectBrandList")
    public List<Map> selectBrandList(){
        return brandService.selectBrandList();
    }


}
