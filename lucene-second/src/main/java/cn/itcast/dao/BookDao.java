package cn.itcast.dao;

import cn.itcast.pojo.Book;

import java.util.List;

public interface BookDao {

    //查询全部
    List<Book> queryBookList();
}
