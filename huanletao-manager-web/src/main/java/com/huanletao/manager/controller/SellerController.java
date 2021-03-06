package com.huanletao.manager.controller;
import java.util.List;

import com.huanletao.pojo.JsonResult;
import com.huanletao.pojo.PageResult;
import com.huanletao.pojo.TbSeller;
import com.huanletao.sellergoods.service.SellerService;
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
@RequestMapping("/seller")
public class SellerController {

	@Reference
	private SellerService sellerService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll(){
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return sellerService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	@RequestMapping("/add")
	public JsonResult add(@RequestBody TbSeller seller){
		try {
			sellerService.add(seller);
			return new JsonResult(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@RequestMapping("/update")
	public JsonResult update(@RequestBody TbSeller seller){
		try {
			sellerService.update(seller);
			return new JsonResult(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, "修改失败");
		}
	}
	@RequestMapping("/updateStatus")
	public JsonResult updateStatus(String sellerId ,String status){
		try {
			sellerService.updateStatus(sellerId,status);
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
	public TbSeller findOne(String id){
		return sellerService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public JsonResult delete(String [] ids){
		try {
			sellerService.delete(ids);
			return new JsonResult(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSeller seller, int page, int rows  ){
		return sellerService.findPage(seller, page, rows);		
	}
	
}
