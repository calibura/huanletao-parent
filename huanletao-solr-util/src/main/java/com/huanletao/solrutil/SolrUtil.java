package com.huanletao.solrutil;

import com.alibaba.fastjson.JSON;
import com.huanletao.mapper.TbItemMapper;
import com.huanletao.pojo.TbItem;
import com.huanletao.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private SolrTemplate template;

    /**
     * 完成solr数据的初始导入
     */
    public void importData(){
        //查询所有的数据
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");
        List<TbItem> list = itemMapper.selectByExample(example);
        //导入数据
        for (TbItem tbItem : list) {
            String spec = tbItem.getSpec();
            Map<String,String> specMap = (Map) JSON.parse(spec);
            tbItem.setSpecMap(specMap);
            System.out.println(tbItem);
            template.saveBean(tbItem);
        }
        template.commit();
    }

    public void deleteAll(){
        SolrDataQuery query = new SimpleQuery("*:*");
        template.delete(query);
        template.commit();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        SolrUtil solrUtil = (SolrUtil) context.getBean("solrUtil");
        solrUtil.importData();
//        solrUtil.deleteAll();
    }
}
