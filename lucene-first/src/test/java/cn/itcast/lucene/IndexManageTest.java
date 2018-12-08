package cn.itcast.lucene;

import cn.itcast.dao.BookDao;
import cn.itcast.dao.impl.BookDaoImpl;
import cn.itcast.pojo.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
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

public class IndexManageTest {

    /**
     * 从索引目录检索并获取文档
     */
    @Test
    public void indexSearch() throws Exception {
        //1.	创建分析器对象（Analyzer），用于分词
        //Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        //2.	创建查询对象（Query）
        //参数1：域名， 参数2：分词器（要与索引的时候分词器一致）
        QueryParser queryParser = new QueryParser("bookname", analyzer);

        Query query = queryParser.parse("lucene");

        //3.	创建索引库目录对象（Directory），指定索引库的位置
        Directory directory = FSDirectory.open(new File("D:\\itcast\\test\\lucene"));
        //4.	创建索引数据读取对象（IndexReader），把索引数据读取到内存中
        IndexReader indexReader = DirectoryReader.open(directory);
        //5.	创建索引搜索对象（IndexSearcher），执行搜索，返回搜索的结果集TopDocs
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        /**
         * 分页查询：
         * 查询第2页数据，每页2条
         */
        int page = 1;
        //页大小
        int pageSize = 2;
        //起始索引号 =（页号-1）*页大小
        int start = (page-1)*pageSize;
        //本次要才前n条
        int end = start + pageSize;

        /**
         * 参数1：查询条件对象
         * 参数2：本次返回的前n条记录数
         */
        //TopDocs topDocs = indexSearcher.search(query, 10);
        TopDocs topDocs = indexSearcher.search(query, end);

        System.out.println("符合本次查询条件的文档数有：" + topDocs.totalHits);

        if(end > topDocs.totalHits){
            //最后的一条记录的索引号不可以大于本次的总记录数
            end = topDocs.totalHits;
        }

        //6.	处理搜索结果
        //符合条件的那些文档的id
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        /*for (ScoreDoc scoreDoc : scoreDocs) {
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
        }*/

        for (int i = start; i < end; i++) {
            ScoreDoc scoreDoc = scoreDocs[i];
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

    /**
     * 写索引到某个一个索引目录
     */
    @Test
    public void indexWrite() throws Exception{
        //1.	采集数据
        BookDao bookDao = new BookDaoImpl();
        List<Book> bookList = bookDao.queryBookList();

        List<Document> documentList = new ArrayList<Document>();
        Document document = null;
        //2.	创建文档对象（Document）
        for (Book book : bookList) {
            document = new Document();

            /**
             * 参数1：保存到lucene的域名称
             * 参数2：域值
             * 参数3：是否要存储
             */
            document.add(new TextField("id", book.getId()+"", Field.Store.YES));
            TextField textField = new TextField("bookname", book.getBookname(), Field.Store.YES);

            if (book.getId().equals(5)) {
                //设置权重，默认值为1.0
                textField.setBoost(2.0f);
            }

            document.add(textField);
            document.add(new TextField("price", book.getPrice()+"", Field.Store.YES));
            document.add(new TextField("pic", book.getPic(), Field.Store.YES));
            document.add(new TextField("bookdesc", book.getBookdesc(), Field.Store.YES));

            documentList.add(document);
        }

        //3.	创建分析器对象（Analyzer），用于分词
        //Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new IKAnalyzer();

        //4.	创建索引库的配置对象（IndexWriterConfig），配置索引库
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
        //5.	创建索引库的目录对象（Directory），指定索引库的存储位置
        File file = new File("D:\\itcast\\test\\lucene");
        Directory directory = FSDirectory.open(file);
        //6.	创建索引库操作对象（IndexWriter），操作索引库
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        //7.	使用IndexWriter对象，把文档对象写入索引库
        indexWriter.addDocuments(documentList);

        //8.	释放资源
        indexWriter.close();
    }
}
