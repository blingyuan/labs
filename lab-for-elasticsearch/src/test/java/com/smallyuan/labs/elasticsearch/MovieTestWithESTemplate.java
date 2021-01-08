package com.smallyuan.labs.elasticsearch;

import com.smallyuan.labs.elasticsearch.dataobject.ESMovieDO;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class MovieTestWithESTemplate {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void  searchRelatedInfo() {
        String keyword = "Kill";
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        // 搜索条件
        if (StringUtils.isBlank(keyword)) {
            builder.withQuery(QueryBuilders.matchAllQuery());
        } else {
            builder.withQuery(QueryBuilders.multiMatchQuery(keyword,"title","director"));
        }
        NativeSearchQuery searchQuery = builder.build();
        SearchHits<ESMovieDO> searchHit = elasticsearchRestTemplate.search(searchQuery,ESMovieDO.class);
        if (searchHit.getTotalHits() <= 0) {
            return ;
        }
        List<ESMovieDO> result = searchHit.stream().map(SearchHit::getContent).collect(Collectors.toList());
        result.forEach(System.out::println);
        System.out.println("------------------------------");
    }
}
