package com.xjtu.repository;

import com.xjtu.bean.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book,Integer> {
    List<Book> findByBookNameAndAuthor(String bookName, String author);

}

