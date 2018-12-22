package cn.itcast.solr;

import com.pinyougou.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-solr.xml")
public class SolrTest {

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 新增或者更新
     * 如果数据存在则更新，不存在则新增
     */
    @Test
    public void addOrUpdate(){
        TbItem item = new TbItem();
        item.setId(100001172674L);
        item.setTitle("222 一加手机6T 8GB+128GB 墨岩黑 光感屏幕指纹 全面屏双摄游戏手机 全网通4G 双卡双待");
        item.setPrice(new BigDecimal(3599));
        item.setImage("https://item.jd.com/100001172674.html");
        item.setBrand("一加");
        item.setCategory("手机");

        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }

    /**
     * 根据主键删除
     */
    @Test
    public void deleteById(){
        solrTemplate.deleteById("100001172674");
        solrTemplate.commit();
    }

    /**
     * 根据条件删除
     */
    @Test
    public void deleteByQuery(){
        //域名，contains不会分词
        Criteria criteria = new Criteria("item_title").contains("游戏");
        SimpleQuery query = new SimpleQuery(criteria);
        solrTemplate.delete(query);
        solrTemplate.commit();
    }

    /**
     * 分页查询数据
     */
    @Test
    public void queryForPage(){

        //创建查询条件
        SimpleQuery query = new SimpleQuery();

        //设置查询条件
        Criteria criteria = new Criteria("item_title").contains("游戏");
        query.addCriteria(criteria);

        //设置分页信息；起始索引号；默认为0
        query.setOffset(0);
        //页大小；默认为10
        query.setRows(10);

        //根据条件查询
        ScoredPage<TbItem> scoredPage = solrTemplate.queryForPage(query, TbItem.class);

        showPage(scoredPage);
    }

    /**
     * 显示查询结果
     * @param scoredPage 查询结果
     */
    private void showPage(ScoredPage<TbItem> scoredPage) {
        System.out.println("总记录数为：" + scoredPage.getTotalElements());
        System.out.println("总页数为：" + scoredPage.getTotalPages());
        for (TbItem tbItem : scoredPage.getContent()) {
            System.out.println("id = " + tbItem.getId());
            System.out.println("title = " + tbItem.getTitle());
            System.out.println("price = " + tbItem.getPrice());
            System.out.println("image = " + tbItem.getImage());
            System.out.println("brand = " + tbItem.getBrand());
            System.out.println("category = " + tbItem.getCategory());
        }
    }

    /**
     * 多条件查询数据
     */
    @Test
    public void multiQuery(){
        //创建查询条件
        SimpleQuery query = new SimpleQuery();

        //设置查询条件
        Criteria criteria = new Criteria("item_title").contains("游戏");
        query.addCriteria(criteria);

        Criteria criteria2 = new Criteria("item_category").contains("手机");
        query.addCriteria(criteria2);

        //设置分页信息；起始索引号；默认为0
        //query.setOffset(0);
        //页大小；默认为10
        //query.setRows(10);

        //根据条件查询
        ScoredPage<TbItem> scoredPage = solrTemplate.queryForPage(query, TbItem.class);

        showPage(scoredPage);
    }
}
