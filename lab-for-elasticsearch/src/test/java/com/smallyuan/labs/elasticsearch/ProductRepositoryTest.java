package com.smallyuan.labs.elasticsearch;

import com.smallyuan.labs.elasticsearch.dataobject.ESProductDO;
import com.smallyuan.labs.elasticsearch.repository.ProductRepository;
import org.elasticsearch.action.bulk.BulkRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    // 插入数据
    @Test
    public void testInsert() {
        ESProductDO product = new ESProductDO();
        product.setId(1); // 一般 ES 的 ID 编号，使用 DB 数据对应的编号。这里，先写死
        product.setName("smallyuan");
        product.setSellPoint("愿你平安喜乐");
        product.setCid(1);
        product.setCategoryName("技术");
        productRepository.save(product);
    }

    // 更新，save方法进行更新需要全量字段，会覆盖
    @Test
    public void testUpdate() {
        ESProductDO product = new ESProductDO();
        product.setId(1);
        product.setCid(2);
        product.setCategoryName("技术-Java");
        productRepository.save(product);
    }

    // 根据 ID 编号，删除一条记录
    @Test
    public void testDelete() {
        productRepository.deleteById(1);
    }

    // 根据Id编号，查询一条记录
    @Test
    public void testSelectById() {
        Optional<ESProductDO> userDO = productRepository.findById(1);
        System.out.println(userDO.isPresent());
        System.out.println(userDO.get().toString());
    }

    @Test
    public void testSelectByIds() {
        Iterable<ESProductDO>  users = productRepository.findAllById(Arrays.asList(1,4));
        users.forEach(System.out::println);
    }

    public void testBulk() {
        BulkRequest request = new BulkRequest();
    }
}
