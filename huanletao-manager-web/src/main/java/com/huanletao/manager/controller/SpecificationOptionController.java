package com.huanletao.manager.controller;
import java.util.List;

import com.huanletao.pojo.JsonResult;
import com.huanletao.pojo.PageResult;
import com.huanletao.pojo.TbSpecificationOption;
import com.huanletao.sellergoods.service.SpecificationOptionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;


/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/specificationOption")
public class SpecificationOptionController {

	@Reference
	private SpecificationOptionService specificationOptionService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSpecificationOption> findAll(){
		return specificationOptionService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return specificationOptionService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param specificationOption
	 * @return
	 */
	@RequestMapping("/add")
	public JsonResult add(@RequestBody TbSpecificationOption specificationOption){
		try {
			specificationOptionService.add(specificationOption);
			return new JsonResult(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param specificationOption
	 * @return
	 */
	@RequestMapping("/update")
	public JsonResult update(@RequestBody TbSpecificationOption specificationOption){
		try {
			specificationOptionService.update(specificationOption);
			return new JsonResult(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbSpecificationOption findOne(Long id){
		return specificationOptionService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public JsonResult delete(Long [] ids){
		try {
			specificationOptionService.delete(ids);
			return new JsonResult(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSpecificationOption specificationOption, int page, int rows  ){
		return specificationOptionService.findPage(specificationOption, page, rows);		
	}
	
}
