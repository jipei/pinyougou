package cn.itcast.lucene;

import cn.itcast.dao.BookDao;
import cn.itcast.dao.impl.BookDaoImpl;
import cn.itcast.pojo.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IndexSearchTest {

    /**
     * 根据查询表达式查询
     * @throws Exception
     */
    @Test
    public void queryParser() throws Exception {
        //1. 创建QueryParser
        //参数1：默认的域名称，参数2：分词器
        QueryParser queryParser = new QueryParser("bookname", new IKAnalyzer());

        //2. 编写查询表达式
        //3. 获取查询对象Query
        Query query = queryParser.parse("bookname:java AND bookname:lucene");
        //4. 查询并输出
        doSearch(query);
    }

    /**
     * 组合查询
     * @throws Exception
     */
    @Test
    public void booleanQuery() throws Exception {
        //创建组合查询
        BooleanQuery query = new BooleanQuery();
        /**
         * 按照数值范围查询
         * 参数1：要查询的域名
         * 参数2：查询域值下限
         * 参数3：查询域值上限
         * 参数4：是否包含下限顶端
         * 参数5：是否包含上限顶端
         * 查询价格大于80小于等于100
         */
        Query query1 = NumericRangeQuery.newDoubleRange("price", 80.0, 100.0, false, true);
        query.add(query1, BooleanClause.Occur.MUST);

        Query query2 = new TermQuery(new Term("bookname", "action"));
        query.add(query2, BooleanClause.Occur.MUST);

        doSearch(query);
    }

    /**
     * 根据数值范围查询
     * @throws Exception
     */
    @Test
    public void numericRangeQuery() throws Exception {
        /**
         * 按照数值范围查询
         * 参数1：要查询的域名
         * 参数2：查询域值下限
         * 参数3：查询域值上限
         * 参数4：是否包含下限顶端
         * 参数5：是否包含上限顶端
         * 查询价格大于80小于等于100
         */
        Query query = NumericRangeQuery.newDoubleRange("price", 80.0, 100.0, false, true);

        doSearch(query);
    }

    /**
     * 根据词条查询；是最小的查询单位
     * @throws Exception
     */
    @Test
    public void termQuery() throws Exception {
        Query query = new TermQuery(new Term("bookname", "lucene"));

        doSearch(query);
    }

    /**
     * 从索引目录检索并获取文档
     */
    public void doSearch(Query query) throws Exception {
        //1.	创建分析器对象（Analyzer），用于分词
        //Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        //2.	创建查询对象（Query）

        //3.	创建索引库目录对象（Directory），指定索引库的位置
        Directory directory = FSDirectory.open(new File("D:\\itcast\\test\\lucene"));
        //4.	创建索引数据读取对象（IndexReader），把索引数据读取到内存中
        IndexReader indexReader = DirectoryReader.open(directory);
        //5.	创建索引搜索对象（IndexSearcher），执行搜索，返回搜索的结果集TopDocs
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        /**
         * 参数1：查询条件对象
         * 参数2：本次返回的前n条记录数
         */
        TopDocs topDocs = indexSearcher.search(query, 10);

        System.out.println("符合本次查询条件的文档数有：" + topDocs.totalHits);


        //6.	处理搜索结果
        //符合条件的那些文档的id
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("---------------------------------------------------------");
            System.out.println("文档在lucene中的id为：" + scoreDoc.doc);
            System.out.println("文档在lucene中的分值：" + scoreDoc.score);
            //根据文档id获取文档
            Document doc = indexSearcher.doc(scoreDoc.doc);

            System.out.println("id = " + doc.get("id"));
            System.out.println("bookname = " + doc.get("bookname"));
            System.out.println("pic = " + doc.get("pic"));
            System.out.println("price = " + doc.get("price"));
            System.out.println("bookdesc = " + doc.get("bookdesc"));
        }

        //7.	释放资源
        indexReader.close();
    }

}
