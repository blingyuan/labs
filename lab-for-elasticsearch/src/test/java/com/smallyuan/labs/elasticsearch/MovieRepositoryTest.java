package com.smallyuan.labs.elasticsearch;

import com.smallyuan.labs.elasticsearch.dataobject.ESMovieDO;
import com.smallyuan.labs.elasticsearch.repository.MovieRepository;
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


}
