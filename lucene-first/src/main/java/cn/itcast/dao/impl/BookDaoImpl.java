package cn.itcast.dao.impl;

import cn.itcast.dao.BookDao;
import cn.itcast.pojo.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    public List<Book> queryBookList() {
        List<Book> bookList = new ArrayList<Book>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            //1. 加载驱动
            Class.forName("com.mysql.jdbc.Driver");

            //2. 创建连接
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lucene_95", "root", "root");
            //3. 创建执行语句对象
            statement = connection.createStatement();
            //4. 执行查询
            rs = statement.executeQuery("select * from book");
            //5. 处理返回结果
            Book book = null;
            while (rs.next()) {
                book = new Book();
                book.setId(rs.getInt("id"));
                book.setBookname(rs.getString("bookname"));
                book.setPic(rs.getString("pic"));
                book.setPrice(rs.getFloat("price"));
                book.setBookdesc(rs.getString("bookdesc"));

                bookList.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6. 关闭资源
            try {
                if(rs != null){
                    rs.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return bookList;
    }

    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        List<Book> bookList = bookDao.queryBookList();
        for (Book book : bookList) {
            System.out.println(book);
        }
    }

}
