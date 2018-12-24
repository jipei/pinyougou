package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //创建查询对象
        //SimpleQuery query = new SimpleQuery();
        //创建高亮的查询对象
        SimpleHighlightQuery query = new SimpleHighlightQuery();

        //设置查询条件 is 会对搜索关键字进行分词
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        //设置高亮的域名和起始、结束标签
        HighlightOptions highlightOptions = new HighlightOptions();
        //高亮域名
        highlightOptions.addField("item_title");
        //设置高亮的起始标签
        highlightOptions.setSimplePrefix("<font style='color:red'>");
        //设置高亮的结束标签
        highlightOptions.setSimplePostfix("</font>");

        query.setHighlightOptions(highlightOptions);

        //1. 根据商品分类进行商品分类过滤条件的设置；
        if(!StringUtils.isEmpty(searchMap.get("category"))){
            //创建过滤查询条件对象
            Criteria categoryCriteria = new Criteria("item_category").is(searchMap.get("category"));
            SimpleFilterQuery categoryFilterQuery = new SimpleFilterQuery(categoryCriteria);
            query.addFilterQuery(categoryFilterQuery);
        }
        //2. 根据品牌进行品牌过滤条件的设置；
        if(!StringUtils.isEmpty(searchMap.get("brand"))){
            //创建过滤查询条件对象
            Criteria brandCriteria = new Criteria("item_brand").is(searchMap.get("brand"));
            SimpleFilterQuery brandFilterQuery = new SimpleFilterQuery(brandCriteria);
            query.addFilterQuery(brandFilterQuery);
        }

        //3. 根据规格进行规格过滤条件的设置；
        if(searchMap.get("spec") != null){

            //创建过滤查询条件对象
            Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
            Set<Map.Entry<String, String>> entries = specMap.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                Criteria specCriteria = new Criteria("item_spec_" + entry.getKey()).is(entry.getValue());
                SimpleFilterQuery specFilterQuery = new SimpleFilterQuery(specCriteria);
                query.addFilterQuery(specFilterQuery);
            }

        }


        //查询
        //ScoredPage<TbItem> scoredPage = solrTemplate.queryForPage(query, TbItem.class);
        HighlightPage<TbItem> highlightPage = solrTemplate.queryForHighlightPage(query, TbItem.class);

        //处理高亮的返回结果
        List<HighlightEntry<TbItem>> highlighted = highlightPage.getHighlighted();
        if (highlighted != null && highlighted.size() > 0) {
            for (HighlightEntry<TbItem> entry : highlighted) {
                List<HighlightEntry.Highlight> highlights = entry.getHighlights();
                if (highlights != null && highlights.size() > 0
                        && highlights.get(0).getSnipplets() != null && highlights.get(0).getSnipplets().size() > 0) {
                    //第一个get(0)为获取第一个域，第二个get(0)获取该域第一个高亮的字符串
                    String title = highlights.get(0).getSnipplets().get(0).toString();
                    entry.getEntity().setTitle(title);
                }
            }
        }


        resultMap.put("rows", highlightPage.getContent());
        return resultMap;
    }
}
