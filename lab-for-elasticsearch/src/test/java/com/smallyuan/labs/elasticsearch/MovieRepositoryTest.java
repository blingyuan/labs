package com.smallyuan.labs.elasticsearch;

import com.smallyuan.labs.elasticsearch.dataobject.ESMovieDO;
import com.smallyuan.labs.elasticsearch.repository.MovieRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    // search
    @Test
    public void testFindAll() {
        Iterable<ESMovieDO> movies = movieRepository.findAll();
        movies.forEach(System.out::println);
    }

    @Test
    public void testFindByTitleLike(){
        List<ESMovieDO> movies = movieRepository.findByTitleLike("ford");
        movies.forEach(System.out::println);
    }

    @Test
    public void testFindByQuery(){
        List<ESMovieDO> movies = movieRepository.findByQuery("ford");
        movies.forEach(System.out::println);
    }

    @Test
    public void testFindByQueryAndField(){
        List<ESMovieDO> movies = movieRepository.findByQueryAndField("title","ford");
        movies.forEach(System.out::println);
    }

    @Test
    public void testFindByQueryAndFields(){
        QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("genres","drama")).filter(QueryBuilders.termQuery("year",1962));
        Iterable<ESMovieDO> movies = movieRepository.search(query);
        movies.forEach(System.out::println);
    }
}
