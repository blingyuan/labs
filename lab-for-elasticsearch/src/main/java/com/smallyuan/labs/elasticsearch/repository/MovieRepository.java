package com.smallyuan.labs.elasticsearch.repository;

import com.smallyuan.labs.elasticsearch.dataobject.ESMovieDO;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MovieRepository extends ElasticsearchRepository<ESMovieDO,Integer> {

    List<ESMovieDO> findByTitleLike(String title);

    @Query("{\"query_string\": {\"query\": \"?0\"}}")
    List<ESMovieDO> findByQuery(String anything);

    @Query("{\"query_string\": {\"default_field\": \"?0\",\"query\": \"?1\"}}")
    List<ESMovieDO> findByQueryAndField(String filed,String anything);
}
