package com.huanletao.content.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.huanletao.content.service.ContentService;
import com.huanletao.mapper.TbContentMapper;
import com.huanletao.pojo.PageResult;
import com.huanletao.pojo.TbContent;
import com.huanletao.pojo.TbContentExample;
import com.huanletao.util.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbContent> findAll() {
		return contentMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbContent> page=   (Page<TbContent>) contentMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbContent content) {
		contentMapper.insert(content);
		template.boundHashOps(RedisConfig.CONTENT).delete(content.getCategoryId());
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbContent content){
		contentMapper.updateByPrimaryKey(content);
		template.boundHashOps(RedisConfig.CONTENT).delete(content.getCategoryId());
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbContent findOne(Long id){

		return contentMapper.selectByPrimaryKey(id);

	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			TbContent content = findOne(id);
			template.boundHashOps(RedisConfig.CONTENT).delete(content.getCategoryId());
			contentMapper.deleteByPrimaryKey(id);
		}		
	}


	
	@Override
	public PageResult findPage(TbContent content, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbContentExample example=new TbContentExample();
		TbContentExample.Criteria criteria = example.createCriteria();
		
		if(content!=null){			
						if(content.getTitle()!=null && content.getTitle().length()>0){
				criteria.andTitleLike("%"+content.getTitle()+"%");
			}
			if(content.getUrl()!=null && content.getUrl().length()>0){
				criteria.andUrlLike("%"+content.getUrl()+"%");
			}
			if(content.getPic()!=null && content.getPic().length()>0){
				criteria.andPicLike("%"+content.getPic()+"%");
			}
			if(content.getStatus()!=null && content.getStatus().length()>0){
				criteria.andStatusLike("%"+content.getStatus()+"%");
			}
		}

		Page<TbContent> page= (Page<TbContent>)contentMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Autowired
	private RedisTemplate template;

	@Override
	public List<TbContent> findByCategoryId(Long id) {
		List<TbContent> redisList = (List<TbContent>) template.boundHashOps(RedisConfig.CONTENT).get(id);
		if(redisList == null) {
			System.out.println("从数据库获取的");
			//看看redis里面有没有
			//如果有  直接返回
			//如果没有 查 存到redis里面 再返回
			TbContentExample example = new TbContentExample();
			TbContentExample.Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(id);
			criteria.andStatusEqualTo("1");
			example.setOrderByClause("sort_order ASC");
			List<TbContent> list = contentMapper.selectByExample(example);
			template.boundHashOps(RedisConfig.CONTENT).put(id, list);
			return list;
		}else{
			System.out.println("从缓存中获取的获取的");
			return redisList;
		}


	}

}
