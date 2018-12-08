package cn.itcast.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SolrJTest {

    private HttpSolrServer httpSolrServer;

    @Before
    public void setup(){
        //solr服务器地址
        String baseURL = "http://localhost:8080/solr";
        //创建httpSolrServer
        httpSolrServer = new HttpSolrServer(baseURL);
    }

    /**
     * 根据条件查询
     */
    @Test
    public void queryInHighLight() throws Exception {
        //1. 创建查询对象；
        SolrQuery solrQuery = new SolrQuery();

        //设置查询条件
        solrQuery.setQuery("item_title:小米");

        //设置分页；起始索引号
        solrQuery.setStart(0);
        //页大小
        solrQuery.setRows(10);

        //设置高亮
        solrQuery.setHighlight(true);
        //高亮域名称
        solrQuery.addHighlightField("item_title");
        //高亮起始标签
        solrQuery.setHighlightSimplePre("<font style='color:red'>");
        //高亮结束标签
        solrQuery.setHighlightSimplePost("</font>");


        //2. 利用HttpSolrServer执行查询；
        QueryResponse queryResponse = httpSolrServer.query(solrQuery);
        //3. 处理返回结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("符合本次查询的总记录数为："+ solrDocumentList.getNumFound());

        /**
         * 获取高亮的返回结果；其数据结构如下：
         * "highlighting": {
         *     "7437788": {
         *       "item_title": [
         *         "222 小米8 全面屏游戏智能<font style='color:red'>手机</font> 6GB+64GB 黑色 全网通4G 双卡双待"
         *       ]
         *     },
         *
         */
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println("id = " + solrDocument.getFieldValue("id"));
            System.out.println("item_title = " + solrDocument.getFieldValue("item_title"));

            //获取高亮标题
            String title = highlighting.get(solrDocument.get("id").toString()).get("item_title").get(0);

            System.out.println("高亮的标题为：" + title);

            System.out.println("item_catalog_name = " + solrDocument.getFieldValue("item_catalog_name"));
            System.out.println("item_price = " + solrDocument.getFieldValue("item_price"));
            System.out.println("item_image = " + solrDocument.getFieldValue("item_image"));
        }

    }

    /**
     * 根据条件查询
     */
    @Test
    public void query() throws Exception {
        //1. 创建查询对象；
        SolrQuery solrQuery = new SolrQuery();

        //设置查询条件
        solrQuery.setQuery("item_title:小米");

        //设置分页；起始索引号
        solrQuery.setStart(0);
        //页大小
        solrQuery.setRows(10);


        //2. 利用HttpSolrServer执行查询；
        QueryResponse queryResponse = httpSolrServer.query(solrQuery);
        //3. 处理返回结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("符合本次查询的总记录数为："+ solrDocumentList.getNumFound());

        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println("id = " + solrDocument.getFieldValue("id"));
            System.out.println("item_title = " + solrDocument.getFieldValue("item_title"));
            System.out.println("item_catalog_name = " + solrDocument.getFieldValue("item_catalog_name"));
            System.out.println("item_price = " + solrDocument.getFieldValue("item_price"));
            System.out.println("item_image = " + solrDocument.getFieldValue("item_image"));
        }

    }

    /**
     * 根据条件查询
     */
    @Test
    public void queryInSolrCore() throws Exception {
        String baseURL = "http://localhost:8080/solr/collection2";
        //创建httpSolrServer
        httpSolrServer = new HttpSolrServer(baseURL);

        //1. 创建查询对象；
        SolrQuery solrQuery = new SolrQuery();

        //设置查询条件
        solrQuery.setQuery("item_title:小米");

        //设置分页；起始索引号
        solrQuery.setStart(0);
        //页大小
        solrQuery.setRows(10);


        //2. 利用HttpSolrServer执行查询；
        QueryResponse queryResponse = httpSolrServer.query(solrQuery);
        //3. 处理返回结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("符合本次查询的总记录数为："+ solrDocumentList.getNumFound());

        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println("id = " + solrDocument.getFieldValue("id"));
            System.out.println("item_title = " + solrDocument.getFieldValue("item_title"));
            System.out.println("item_catalog_name = " + solrDocument.getFieldValue("item_catalog_name"));
            System.out.println("item_price = " + solrDocument.getFieldValue("item_price"));
            System.out.println("item_image = " + solrDocument.getFieldValue("item_image"));
        }

    }

    /**
     * 根据条件删除solr数据
     */
    @Test
    public void deleteByQuery() throws Exception {

        //根据条件删除solr数据；该表达式与lucene一致
        httpSolrServer.deleteByQuery("*:*");

        //提交
        httpSolrServer.commit();
    }

    /**
     * 根据id删除solr数据
     */
    @Test
    public void deleteById() throws Exception {

        //根据id删除solr数据
        httpSolrServer.deleteById("7437788");

        //提交
        httpSolrServer.commit();
    }

    /**
     * 新增或者更新solr数据
     * 如果文档存在则更新，不存在则新增
     */
    @Test
    public void addOrUpdate() throws Exception {

        //创建文档
        SolrInputDocument doc = new SolrInputDocument();
        //参数1：域名；一定要在schema.xml文件中配置的域名才可以使用
        //参数2：域值
        doc.setField("id", "7437789");
        doc.setField("item_title", "小米8 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待");
        doc.setField("item_catalog_name", "手机");
        doc.setField("item_price", 2499);
        doc.setField("item_image", "https://item.jd.com/7437788.html");

        //保存
        httpSolrServer.add(doc);

        //提交
        httpSolrServer.commit();
    }
}
