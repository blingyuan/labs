package com.smallyuan.labs.elasticsearch.repository;

import com.smallyuan.labs.elasticsearch.dataobject.ESProductDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * ProductRepository
 * 继承 ElasticsearchRepository, Spring Data Jest会自动生成CRUD (跟JPA很像)
 */
public interface ProductRepository extends ElasticsearchRepository<ESProductDO,Integer> {

}
